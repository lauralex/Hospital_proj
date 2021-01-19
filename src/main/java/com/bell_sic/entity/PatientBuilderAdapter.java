package com.bell_sic.entity;

import java.util.Objects;

public abstract class PatientBuilderAdapter implements PatientBuilder {
    private Appointment appointment;

    /**
     * @param appointment The {@linkplain Appointment} that should be set for the constructed {@linkplain Patient}.
     * @return The current instance of {@code PatientBuilderAdapter}.
     * @throws NullPointerException If {@code appointment} is {@code null}.
     */
    @Override
    public PatientBuilder setAppointment(Appointment appointment) throws NullPointerException {
        this.appointment = Objects.requireNonNull(appointment, "Appointment cannot be null!");
        return this;
    }

    /**
     * This method should be used by subclasses of this class as a mean to access the {@link Appointment}
     * of the new patient.
     * @return The {@linkplain Appointment} of the constructed {@linkplain Patient}.
     */
    protected Appointment getAppointment() {
        return appointment;
    }
}
