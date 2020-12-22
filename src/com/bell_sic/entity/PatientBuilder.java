package com.bell_sic.entity;

public interface PatientBuilder {
    PatientBuilder setAppointment(Appointment appointment);

    Patient build();
}
