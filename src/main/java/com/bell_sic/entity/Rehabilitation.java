package com.bell_sic.entity;

import java.time.Period;
import java.util.Objects;

public class Rehabilitation {
    private Period rehabilitationDuration;
    private Patient patient;
    private RehabilitationState rehabilitationState;
    private String description;

    public Rehabilitation(Patient patient, String description, Period rehabilitationDuration) throws IllegalArgumentException, NullPointerException {
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank!");
        }
        this.patient = Objects.requireNonNull(patient, "Patient cannot be null!");
        this.rehabilitationDuration = Objects.requireNonNull(rehabilitationDuration, "RehabilitationDuration cannot be null!");
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) throws NullPointerException {
        this.patient = Objects.requireNonNull(patient, "Patient cannot be null!");
    }

    public Period getRehabilitationDuration() {
        return rehabilitationDuration;
    }

    public void setRehabilitationDuration(Period rehabilitationDuration) throws NullPointerException {
        this.rehabilitationDuration = Objects.requireNonNull(rehabilitationDuration, "RehabilitationDuration cannot be null!");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) throws IllegalArgumentException, NullPointerException {
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank!");
        }
        this.description = description;
    }
}
