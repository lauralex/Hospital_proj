package com.bell_sic.entity;

public interface PatientBuilder {
    /**
     * @param appointment The {@linkplain Appointment} that should be set for the constructed {@linkplain Patient}.
     * @return The current instance of {@code PatientBuilder}.
     */
    PatientBuilder setAppointment(Appointment appointment);

    /**
     * This method should return the properly constructed {@link Patient}.
     * @return The constructed {@linkplain Patient}.
     */
    Patient build();
}
