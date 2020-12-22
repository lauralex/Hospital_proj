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

    /**
     * @param personalInfo The {@linkplain PersonalInfo} you want to set for the patient.
     * @throws NullPointerException If {@code personalInfo} is {@code null}.
     */
    public void setPersonalInfo(PersonalInfo personalInfo) throws NullPointerException {
        this.personalInfo = Objects.requireNonNull(personalInfo, "personal info cannot be null!");
    }

    /**
     * @param name The name you want to set for the patient.
     * @param lastName The last name you want to set for the patient.
     * @param dateOfBirth The date of birth you want to set for the patient.
     * @param cityOfBirth The city of birth you want to set for the patient.
     * @throws NullPointerException If any of the arguments is {@code null}.
     * @throws IllegalArgumentException If {@code name} or {@code lastName} or {@code cityOfBirth} are {@code null}.
     */
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

    /**
     * @return The current {@linkplain Appointment} set for the patient.
     */
    public Appointment getAppointment() {
        return appointment;
    }

    /**
     * @param appointment The {@linkplain Appointment} you want to set for the patient.
     *                    Can be {@code null} if it can't be specified at the moment.
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

}
