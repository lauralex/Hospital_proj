package com.bell_sic.entity.employees;

import com.bell_sic.entity.*;
import com.bell_sic.entity.permission.*;
import com.bell_sic.entity.wards.Ward;
import com.bell_sic.state_machine.StateOperations;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleLineReader;
import javassist.bytecode.DuplicateMemberException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Doctor extends Employee {
    private final Set<Appointment> appointments = new HashSet<>();
    private final List<Rehabilitation> rehabilitations = new ArrayList<>();
    private String qualification = "No buoino";

    public Doctor(String name, String lastName, String userName, String password, LocalDate dateOfBirth, String cityOfBirth) throws IllegalArgumentException, NullPointerException {
        super(name, lastName, userName, password, dateOfBirth, cityOfBirth);
    }

    public Doctor(PersonalInfo personalInfo, Credentials credentials) throws NullPointerException {
        super(personalInfo, credentials);
    }

    public static EmployeeBuilder builder(PersonalInfo personalInfo, Credentials credentials) {
        return new DoctorBuilderAdapter() {
            @Override
            public Employee build() {
                Doctor doc = (Doctor) builderHelper(new Doctor(personalInfo, credentials));
                doc.qualification = getQualification();
                return doc;
            }
        };
    }

    @Override
    protected void setDefaultPermissions() {
        addPermission(ExitPermission.get());
        addPermission(LogoutPermission.get());
        addPermission(ManagePatientInfoPermission.get());
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) throws IllegalArgumentException, NullPointerException {
        if (qualification.isBlank()) {
            throw new IllegalArgumentException("Qualification cannot be blank!");
        }
        this.qualification = qualification;
    }

    public Set<Appointment> getAppointments() {
        return Collections.unmodifiableSet(appointments);
    }

    public void addAppointment(Appointment appointment) throws NullPointerException, DuplicateMemberException {
        if (!appointments.add(Objects.requireNonNull(appointment, "Appointment cannot be null!"))) {
            throw new DuplicateMemberException("There is already this appointment");
        }
    }

    private void confirmAppointments() {
        System.out.println("Select an appointment to confirm");
        StateOperations confirmAppOperations = new StateOperations();
        appointments.stream().filter(appointment -> appointment.getAppointmentState() == AppointmentState.PENDING)
                .forEach(appointment -> confirmAppOperations.addOperation(appointment.toString(),
                        () -> appointment.setAppointmentState(AppointmentState.ACCEPTED), DoctorPermission.get()));
        confirmAppOperations.addOperation("Cancel", () -> {
        }, DoctorPermission.get());
        confirmAppOperations.checkUserInputAndExecute();
    }

    private void showAppointments(AppointmentState appointmentState) {
        appointments.stream().filter(appointment -> appointment.getAppointmentState() == Objects.requireNonNull(appointmentState,
                "Appointment state cannot be null!"))
                .forEach(appointment -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, appointment.toString()));
    }

    private void consumeAppointment() {
        System.out.println("Select an appointment to complete");
        StateOperations consApp = new StateOperations();
        appointments.stream().filter(appointment -> appointment.getAppointmentState() == AppointmentState.ACCEPTED)
                .forEach(appointment -> consApp.addOperation(appointment.toString(),
                        () -> appointment.setAppointmentState(appointment.getOperation().getOperationType()
                                == OperationType.SURGERY ? AppointmentState.REHABILITATION_REQUIRED
                                : AppointmentState.PAST), DoctorPermission.get()));
        consApp.addOperation("Cancel", () -> {
        }, DoctorPermission.get());
        consApp.checkUserInputAndExecute();
    }

    private void showAppointments() {
        appointments.forEach(appointment -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, appointment.toString()));
    }

    public boolean removeAppointment(Appointment appointment) throws NullPointerException {
        return appointments.remove(Objects.requireNonNull(appointment, "Appointment cannot be null!"));
    }

    public List<Rehabilitation> getRehabilitations() {
        return Collections.unmodifiableList(rehabilitations);
    }

    public void addRehabilitation(Rehabilitation rehabilitation) {
        rehabilitations.add(Objects.requireNonNull(rehabilitation, "Rehabilitation cannot be null!"));
    }

    public boolean removeRehabilitation(Rehabilitation rehabilitation) {
        return rehabilitations.remove(Objects.requireNonNull(rehabilitation, "Rehabilitation cannot be null!"));
    }

    private void selectRehabilitation() {
        System.out.println("Select a rehabilitation to take care of!");
        StateOperations rehabOperations = new StateOperations();
        Hospital.EmployeeView.getAllEmployeesOfType(Doctor.class).forEach(doctor -> doctor.appointments.stream().filter(
                appointment -> appointment.getAppointmentState() == AppointmentState.REHABILITATION_REQUIRED).forEach(appointment ->
                rehabOperations.addOperation(appointment.toString(), () -> registerRehabilitation(appointment), DoctorPermission.get())));
        rehabOperations.addOperation("Cancel", () -> {
        }, DoctorPermission.get());
        rehabOperations.checkUserInputAndExecute();
    }

    private void registerRehabilitation(Appointment appointment) {
        String rehabDesc;
        System.out.print("Insert rehabilitation description: ");
        try {
            rehabDesc = ConsoleLineReader.getBufferedReader().readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        StateOperations rehabDurationOperations = new StateOperations();
        appointment.getOperation().getPossibleRehabilitationDurations().forEach(
                period -> rehabDurationOperations.addOperation(period.getYears() + " years " + period.getMonths() + " months " + period.getDays() + " days",
                        () -> {
                            addRehabilitation(new Rehabilitation(appointment.getPatient(), rehabDesc, period));
                            appointment.setAppointmentState(AppointmentState.PAST);
                        }, DoctorPermission.get())
        );
        rehabDurationOperations.addOperation("Cancel", () -> {}, DoctorPermission.get());
        rehabDurationOperations.checkUserInputAndExecute();
    }

    public Ward getWard() {
        return Hospital.WardView.getWards().stream().filter(ward -> ward.getEmployees().contains(this)).findAny()
                .orElseThrow(() -> new NoSuchElementException("No ward found!"));
    }

    @Override
    public StateOperations getSpecificOperations() {
        StateOperations ops = new StateOperations();
        ops.addOperation("Confirm appointments", this::confirmAppointments, DoctorPermission.get());
        ops.addOperation("Show pending appointments", () -> showAppointments(AppointmentState.PENDING), DoctorPermission.get());
        ops.addOperation("Show accepted appointments", () -> showAppointments(AppointmentState.ACCEPTED), DoctorPermission.get());
        ops.addOperation("Show appointments/patients that need rehabilitation", () -> showAppointments(AppointmentState.REHABILITATION_REQUIRED), DoctorPermission.get());
        ops.addOperation("Select and register rehabilitation", this::selectRehabilitation, DoctorPermission.get());
        ops.addOperation("Complete appointments", this::consumeAppointment, DoctorPermission.get());
        return ops;
    }
}
