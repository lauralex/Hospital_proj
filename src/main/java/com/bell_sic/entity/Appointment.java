package com.bell_sic.entity;

import java.util.Date;
import java.util.Objects;

public class Appointment {
    private Date dateOfAppointment;
    private Operation operation;
    private Patient patient;
    private AppointmentState appointmentState;

    /**
     * Construct a new {@link Appointment}.
     * @param dateOfAppointment The {@linkplain Date} of the {@code appointment}.
     * @param operation The {@linkplain Operation} of the {@code appointment}.
     * @param patient The {@linkplain Patient} associated with the {@code appointment}.
     * @throws NullPointerException If either {@code dateOfAppointment} or {@code operation} are {@code null}.
     */
    public Appointment(Date dateOfAppointment, Operation operation, Patient patient) throws NullPointerException {
        this.dateOfAppointment = Objects.requireNonNull(dateOfAppointment, "Date of appointment cannot be null!");
        this.operation = Objects.requireNonNull(operation, "Operation cannot be nuLL!");
        this.patient = Objects.requireNonNull(patient, "Patient cannot be null!");
        this.appointmentState = AppointmentState.PENDING;
    }

    /**
     * Get the date of the appointment.
     * @return The {@linkplain Date} of the appointment.
     */
    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    /**
     * Set the date of the appointment.
     * @param dateOfAppointment The {@linkplain Date} you want to set.
     * @throws NullPointerException
     */
    public void setDateOfAppointment(Date dateOfAppointment) throws NullPointerException {
        this.dateOfAppointment = Objects.requireNonNull(dateOfAppointment, "Date of appointment cannot be null!");
    }

    /**
     * Get the operation associated with the appointment.
     * @return The {@linkplain Operation} associated with the appointment.
     */
    public Operation getOperation() {
        return operation;
    }

    /**
     * @param operation The {@linkplain Operation} you want to set for the appointment.
     * @throws NullPointerException If {@code operation} is {@code null}.
     */
    public void setOperation(Operation operation) throws NullPointerException {
        this.operation = Objects.requireNonNull(operation, "Operation cannot be nulL!");
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) throws NullPointerException {
        this.patient = Objects.requireNonNull(patient, "Patient cannot be null!");
    }

    public AppointmentState getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(AppointmentState appointmentState) throws NullPointerException {
        this.appointmentState = Objects.requireNonNull(appointmentState, "AppointmentState cannot be null!");
    }
}
