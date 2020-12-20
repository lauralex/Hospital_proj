package com.bell_sic.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Patient {
    private PersonalInfo personalInfo;
    private String diagnosis;
    private Appointment appointment;

    public Patient(PersonalInfo personalInfo) throws NullPointerException {
        this.personalInfo = Objects.requireNonNull(personalInfo, "personal info cannot be null!");
    }

    public Patient(String name, String lastName, LocalDate dateOfBirth, String cityOfBirth) throws NullPointerException, IllegalArgumentException {
        personalInfo = new PersonalInfo(name, lastName, dateOfBirth, cityOfBirth);
    }

    public static PatientBuilder builder(PersonalInfo personalInfo) throws NullPointerException {
        return new PatientBuilderAdapter() {
            @Override
            public Patient build() {
                var patient = new Patient(personalInfo);
                patient.setAppointment(getAppointment());
                return patient;
            }
        };
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = Objects.requireNonNull(personalInfo, "personal info cannot be null!");
    }

    public void setPersonalInfo(String name, String lastName, LocalDate dateOfBirth, String cityOfBirth) throws NullPointerException, IllegalArgumentException {
        personalInfo = new PersonalInfo(name, lastName, dateOfBirth, cityOfBirth);
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) throws NullPointerException, IllegalArgumentException {
        if (diagnosis.isBlank()) {
            throw new IllegalArgumentException("Diagnosis cannot be empty!");
        }
        this.diagnosis = diagnosis;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public interface PatientBuilder {
        PatientBuilder setAppointment(Appointment appointment);
        Patient build();
    }

    public abstract static class PatientBuilderAdapter implements PatientBuilder {
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
}
