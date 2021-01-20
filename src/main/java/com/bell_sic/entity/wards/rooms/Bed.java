package com.bell_sic.entity.wards.rooms;

import com.bell_sic.entity.Patient;

import java.time.Period;

public class Bed {
    private String bedCode;
    private Patient patient;
    private Period hospitalizationDuration;

    public Bed(String bedCode) throws IllegalArgumentException, NullPointerException {
        if (bedCode.isBlank()) {
            throw new IllegalArgumentException("Bedcode cannot be blank!");
        }
        this.bedCode = bedCode;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Period getHospitalizationDuration() {
        return hospitalizationDuration;
    }

    public void setHospitalizationDuration(Period hospitalizationDuration) {
        this.hospitalizationDuration = hospitalizationDuration;
    }

    public void removePatient() {
        patient = null;
        hospitalizationDuration = null;
    }

    public String getBedCode() {
        return bedCode;
    }

    public void setBedCode(String bedCode) throws IllegalArgumentException, NullPointerException {
        if (bedCode.isBlank()) {
            throw new IllegalArgumentException("Bedcode cannot be blank!");
        }
        this.bedCode = bedCode;
    }

    public Room getRoom() {
        throw new UnsupportedOperationException();
        // TODO to implement
    }
}
