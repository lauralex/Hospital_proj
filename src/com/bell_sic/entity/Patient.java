package com.bell_sic.entity;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

public class Patient {
    PersonalInfo personalInfo;
    String diagnosis;
    Date dateOfAppointment;

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

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = Objects.requireNonNull(dateOfAppointment, "Date cannot be null!");
    }

    public Patient(PersonalInfo personalInfo) throws NullPointerException {
        this.personalInfo = Objects.requireNonNull(personalInfo, "personal info cannot be null!");
    }

    public Patient(String name, String lastName, LocalDate dateOfBirth, String cityOfBirth) throws NullPointerException, IllegalArgumentException {
        personalInfo = new PersonalInfo(name, lastName, dateOfBirth, cityOfBirth);
    }

    //endregion

}
