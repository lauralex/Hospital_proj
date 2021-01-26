package com.bell_sic.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Patient {
    private PersonalInfo personalInfo;
    private String diagnosis; // TODO to evaluate (?)
    private Appointment appointment; // TODO to delete

    @Override
    public String toString() {
        return "Patient{" +
                "personalInfo=" + personalInfo +
                '}';
    }

    /**
     * Construct a new {@code Patient} using {@link PersonalInfo}.
     * @param personalInfo The {@linkplain PersonalInfo} you want to set for the patient.
     * @throws NullPointerException If {@code personalInfo} is {@code null}.
     */
    public Patient(PersonalInfo personalInfo) throws NullPointerException {
        this.personalInfo = Objects.requireNonNull(personalInfo, "personal info cannot be null!");
    }

    /**
     * Construct a new {@code Patient} using info passed as arguments to the constructor.
     * @param name The name of the patient.
     * @param lastName The last name of the patient.
     * @param dateOfBirth The date of birth of the patient.
     * @param cityOfBirth The city of birth of the patient.
     * @throws NullPointerException If any argument is {@code null}.
     * @throws IllegalArgumentException If any argument is {@code blank}.
     */
    public Patient(String name, String lastName, LocalDate dateOfBirth, String cityOfBirth) throws NullPointerException, IllegalArgumentException {
        personalInfo = new PersonalInfo(name, lastName, dateOfBirth, cityOfBirth);
    }

    /**
     * This static method gives you a properly configured {@link PatientBuilder} for the patient.
     * @param personalInfo The {@linkplain PersonalInfo} you want to set for the patient.
     * @return A properly configured {@linkplain PatientBuilder}.
     * @throws NullPointerException If {@code PersonalInfo} is {@code null}.
     */
    public static PatientBuilder builder(PersonalInfo personalInfo) throws NullPointerException {
        return new PatientBuilderAdapter() {
            @Override
            public Patient build() {
                var patient = new Patient(personalInfo);
                patient.setAppointment(getAppointment()); // TODO to delete
                return patient;
            }
        };
    }

    /**
     * Get the current patient's {@link PersonalInfo}.
     * @return The {@linkplain PersonalInfo} associated with the current patient.
     */
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
        // TODO to evaluate (?)
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) throws NullPointerException, IllegalArgumentException {
        // TODO to evaluate (?)
        if (diagnosis.isBlank()) {
            throw new IllegalArgumentException("Diagnosis cannot be empty!");
        }
        this.diagnosis = diagnosis;
    }

    /**
     * @return The current {@linkplain Appointment} set for the patient.
     */
    public Appointment getAppointment() {
        // TODO to delete
        return appointment;
    }

    /**
     * @param appointment The {@linkplain Appointment} you want to set for the patient.
     *                    Can be {@code null} if it can't be specified at the moment.
     */
    public void setAppointment(Appointment appointment) {
        // TODO to delete
        this.appointment = appointment;
    }

}
