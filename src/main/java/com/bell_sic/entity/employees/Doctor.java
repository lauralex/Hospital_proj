package com.bell_sic.entity.employees;

import com.bell_sic.entity.Appointment;
import com.bell_sic.entity.Operation;
import com.bell_sic.entity.PersonalInfo;
import com.bell_sic.entity.Rehabilitation;
import com.bell_sic.entity.permission.*;
import com.bell_sic.entity.wards.Ward;
import com.bell_sic.state_machine.StateOperations;
import com.bell_sic.utility.Pair;

import javax.swing.plaf.nimbus.State;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Doctor extends Employee {
    private Operation operationOfCompetence; // TODO to delete
    private String qualification = "No buoino";
    private final List<Appointment> appointments = new ArrayList<>();
    private final List<Rehabilitation> rehabilitations = new ArrayList<>();

    public Doctor(String name, String lastName, String userName, String password, LocalDate dateOfBirth, String cityOfBirth) throws IllegalArgumentException, NullPointerException {
        super(name, lastName, userName, password, dateOfBirth, cityOfBirth);
    }

    @Override
    protected void setDefaultPermissions() {
        addPermission(ExitPermission.get());
        addPermission(LogoutPermission.get());
        addPermission(ManagePatientInfoPermission.get());
    }

    public Doctor(PersonalInfo personalInfo, Credentials credentials) throws NullPointerException {
        super(personalInfo, credentials);
    }

    public static EmployeeBuilder builder(PersonalInfo personalInfo, Credentials credentials) {
        return new DoctorBuilderAdapter() {
            @Override
            public Employee build() {
                Doctor doc = (Doctor) builderHelper(new Doctor(personalInfo, credentials));
                doc.setOperationOfCompetence(getCompetence());
                return doc;
            }
        };
    }

    public Operation getOperationOfCompetence() {
        // TODO to delete
        return operationOfCompetence;
    }
    public void setOperationOfCompetence(Operation operationOfCompetence) {
        // TODO to delete
        this.operationOfCompetence = operationOfCompetence;
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

    public List<Appointment> getAppointments() {
        return Collections.unmodifiableList(appointments);
    }
    public void addAppointment(Appointment appointment) throws NullPointerException {
        appointments.add(Objects.requireNonNull(appointment, "Appointment cannot be null!"));
    }
    public void confirmAppointments() {
        // TODO to implement
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

    public Ward getWard() {
        throw new UnsupportedOperationException();
        // TODO to implement
    }

    @Override
    public StateOperations getSpecificOperations() {
        StateOperations ops = new StateOperations();
        ops.addOperation("Confirm appointments", this::confirmAppointments, DoctorPermission.get());
        return ops;
    }
}
