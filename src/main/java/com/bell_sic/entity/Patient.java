package com.bell_sic.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Patient {
    private PersonalInfo personalInfo;


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

}
