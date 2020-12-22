package com.bell_sic.entity;

import java.util.Date;
import java.util.Objects;

public class Appointment {
    private Date dateOfAppointment;
    private Operation operation;

    public Appointment(Date dateOfAppointment, Operation operation) throws NullPointerException {
        this.dateOfAppointment = Objects.requireNonNull(dateOfAppointment, "Date of appointment cannot be null!");
        this.operation = Objects.requireNonNull(operation, "Operation cannot be nuLL!");
    }

    /**
     * @return The {@linkplain Date} of the appointment.
     */
    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    /**
     * @param dateOfAppointment The {@linkplain Date} you want to set.
     * @throws NullPointerException
     */
    public void setDateOfAppointment(Date dateOfAppointment) throws NullPointerException {
        this.dateOfAppointment = Objects.requireNonNull(dateOfAppointment, "Date of appointment cannot be null!");
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) throws NullPointerException {
        this.operation = Objects.requireNonNull(operation, "Operation cannot be nulL!");
    }
}