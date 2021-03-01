package com.bell_sic.entity.employees;

import com.bell_sic.entity.*;
import com.bell_sic.entity.permission.*;
import com.bell_sic.entity.wards.Ward;
import com.bell_sic.entity.wards.rooms.Bed;
import com.bell_sic.entity.wards.rooms.Room;
import com.bell_sic.state_machine.StateOperations;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleDelay;
import com.bell_sic.utility.ConsoleLineReader;
import javassist.bytecode.DuplicateMemberException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Doctor extends Employee {
    private Set<Appointment> appointments = new HashSet<>();
    private List<Rehabilitation> rehabilitations = new ArrayList<>();
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
        addPermission(DoctorPermission.get());
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

    public void setAppointments(Set<Appointment> appointments) throws NullPointerException {
        this.appointments = Objects.requireNonNull(appointments, "appointments cannot be null!");
    }

    public void clearAppointments() {
        appointments.clear();
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

    public void confirmSelectedAppointment(Appointment appointment) {
        appointment.setAppointmentState(AppointmentState.ACCEPTED);
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
                                == OperationType.SURGERY ? AppointmentState.HOSPITALIZATION_REQUIRED
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

    public void setRehabilitations(List<Rehabilitation> rehabilitations) {
        this.rehabilitations = rehabilitations;
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
        rehabDurationOperations.addOperation("Cancel", () -> {
        }, DoctorPermission.get());
        rehabDurationOperations.checkUserInputAndExecute();
    }

    private void consumeRehabilitation() {
        System.out.println("Select a rehabilitation you want to complete");
        StateOperations rehabConsOperations = new StateOperations();
        getRehabilitations().stream().filter(rehabilitation -> rehabilitation.getRehabilitationState() == RehabilitationState.ACCEPTED)
                .forEach(rehabilitation -> rehabConsOperations.addOperation(rehabilitation.toString(),
                        () -> rehabilitation.setRehabilitationState(RehabilitationState.PAST),
                        DoctorPermission.get()));
        rehabConsOperations.addOperation("Cancel", () -> {
        }, DoctorPermission.get());
        rehabConsOperations.checkUserInputAndExecute();
    }

    private void showRehabilitations(RehabilitationState rehabilitationState) {
        getRehabilitations().stream().filter(rehabilitation -> rehabilitation.getRehabilitationState() == rehabilitationState)
                .forEach(rehabilitation -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN,
                        rehabilitation.toString()));
    }

    private void selectHospitalization() {
        System.out.println("Select a patient to hospitalize!");
        StateOperations hospOperations = new StateOperations();
        Hospital.EmployeeView.getAllEmployeesOfType(Doctor.class).forEach(doctor -> doctor.getAppointments().stream()
                .filter(appointment -> appointment.getAppointmentState() == AppointmentState.HOSPITALIZATION_REQUIRED)
                .forEach(appointment -> hospOperations.addOperation(appointment.toString(), () -> selectBed(appointment), DoctorPermission.get())));
        hospOperations.addOperation("Cancel", () -> {
        }, DoctorPermission.get());
        hospOperations.checkUserInputAndExecute();
    }

    private void selectBed(Appointment appointment) {
        System.out.print("Insert a duration (pnYnMnD): ");
        Period duration;
        try {
            duration = Period.parse(ConsoleLineReader.getBufferedReader().readLine()).normalized();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (DateTimeParseException e) {
            ConsoleColoredPrinter.println("No buoino, you must enter a valid duration!");
            ConsoleDelay.addDelay(1000);
            return;
        }
        System.out.println("Select a free bed!");
        StateOperations freeBedOperations = new StateOperations();

        Hospital.WardView.getWards().stream().map(Ward::getRooms).collect(HashSet<Room>::new, HashSet::addAll, HashSet::addAll)
                .stream().map(Room::getBeds).collect(HashSet<Bed>::new, HashSet::addAll, HashSet::addAll).stream()
                .filter(bed -> bed.getPatientAppointment() == null).forEach(bed -> freeBedOperations.addOperation(bed.toString(),
                () -> {
                    bed.setPatientAppointment(appointment);
                    bed.setHospitalizationDuration(duration);
                    appointment.setAppointmentState(AppointmentState.ON_HOSPITALIZATION);
                }, DoctorPermission.get()));
        freeBedOperations.addOperation("Cancel", () -> {
        }, DoctorPermission.get());
        freeBedOperations.checkUserInputAndExecute();
    }

    private void consumeHospitalization() {
        System.out.println("Select a patient you want to dismiss!");
        StateOperations patientDismissOperations = new StateOperations();
        Hospital.WardView.getWards().stream().map(Ward::getRooms).collect(HashSet<Room>::new, HashSet::addAll, HashSet::addAll)
                .stream().map(Room::getBeds).collect(HashSet<Bed>::new, HashSet::addAll, HashSet::addAll)
                .stream().filter(bed -> bed.getPatientAppointment() != null).forEach(bed -> patientDismissOperations.addOperation(bed.toString(),
                () -> {
                    if (bed.getDateOut().isAfter(LocalDate.now())) {
                        Period remaining = LocalDate.now().until(bed.getDateOut());
                        char choice;
                        System.out.print("Do you really want to dismiss the patient? There are " +
                                remaining.getYears() + " years " + remaining.getMonths() + " months " + remaining.getDays() +
                                " days remaining! (y/N): ");
                        try {
                            choice = ConsoleLineReader.getBufferedReader().readLine().charAt(0);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return;
                        }
                        if (Character.toLowerCase(choice) != 'y') {
                            System.out.println("Operation cancelled!");
                            return;
                        }
                    }
                    bed.getPatientAppointment().setAppointmentState(AppointmentState.REHABILITATION_REQUIRED);
                    bed.removePatient();
                }, DoctorPermission.get()));
        patientDismissOperations.addOperation("Cancel", () -> {
        }, DoctorPermission.get());
        patientDismissOperations.checkUserInputAndExecute();
    }

    private void showBeds() {
        for (var ward :
                Hospital.WardView.getWards()) {
            for (var room :
                    ward.getRooms()) {
                for (var bed :
                        room.getBeds()) {
                    System.out.println(bed.toString());
                }
            }
        }

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
        ops.addOperation("Complete a rehabilitation", this::consumeRehabilitation, DoctorPermission.get());
        ops.addOperation("Show current rehabilitations", () -> showRehabilitations(RehabilitationState.ACCEPTED), DoctorPermission.get());
        ops.addOperation("Show past rehabilitations", () -> showRehabilitations(RehabilitationState.PAST), DoctorPermission.get());
        ops.addOperation("Select patients to hospitalize", this::selectHospitalization, DoctorPermission.get());
        ops.addOperation("Consume hospitalization", this::consumeHospitalization, DoctorPermission.get());
        ops.addOperation("Show beds", this::showBeds, DoctorPermission.get());
        ops.addOperation("Complete appointments", this::consumeAppointment, DoctorPermission.get());
        return ops;
    }

    public void clearRehabilitations() {
        rehabilitations.clear();
    }
}
