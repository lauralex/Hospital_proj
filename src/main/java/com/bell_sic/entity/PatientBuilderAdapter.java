package com.bell_sic.entity;

import java.util.Objects;

public abstract class PatientBuilderAdapter implements PatientBuilder {
    private Appointment appointment;

    @Override
    public PatientBuilder setAppointment(Appointment appointment) throws NullPointerException {
        this.appointment = Objects.requireNonNull(appointment, "Appointment cannot be null!");
        return this;
    }

    protected Appointment getAppointment() {
        return appointment;
    }
}
