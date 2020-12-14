package com.bell_sic.entity;

import com.bell_sic.entity.wards.Ward;
import com.bell_sic.entity.wards.WardView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient {
    PersonalInfo personalInfo;
    String diagnosis;
    Date dateOfAppointment;

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public void setPersonalInfo(String name, String lastName, LocalDate dateOfBirth, String cityOfBirth) {
        personalInfo = new PersonalInfo(name, lastName, dateOfBirth, cityOfBirth);
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Date getDateOfAppointment() {
        return dateOfAppointment;
    }

    public void setDateOfAppointment(Date dateOfAppointment) {
        this.dateOfAppointment = dateOfAppointment;
    }

    public Patient(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    public Patient(String name, String lastName, LocalDate dateOfBirth, String cityOfBirth) {
        personalInfo = new PersonalInfo(name, lastName, dateOfBirth, cityOfBirth);
    }

    public static List<Patient> getAllPatients() {
        return WardView.getWards().stream().map(Ward::getPatients).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }
}
