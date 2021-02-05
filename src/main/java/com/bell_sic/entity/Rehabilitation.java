package com.bell_sic.entity;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Rehabilitation {
    private LocalDate dateIn;
    private LocalDate dateOut;
    private Patient patient;
    private RehabilitationState rehabilitationState;
    private String description;

    public Rehabilitation(Patient patient, String description, Period rehabilitationDuration) throws IllegalArgumentException, NullPointerException {
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank!");
        }
        this.patient = Objects.requireNonNull(patient, "Patient cannot be null!");
        this.description = description;
        this.dateIn = LocalDate.now();
        this.dateOut = LocalDate.now().plus(rehabilitationDuration);
        this.rehabilitationState = RehabilitationState.ACCEPTED;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) throws NullPointerException {
        this.patient = Objects.requireNonNull(patient, "Patient cannot be null!");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rehabilitation that = (Rehabilitation) o;
        return dateIn.equals(that.dateIn) && dateOut.equals(that.dateOut) && patient.equals(that.patient) && rehabilitationState == that.rehabilitationState && description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateIn, dateOut, patient, rehabilitationState, description);
    }

    @Override
    public String toString() {
        return "Rehabilitation{" +
                "dateIn=" + dateIn +
                ", dateOut=" + dateOut +
                ", patient=" + patient +
                ", rehabilitationState=" + rehabilitationState +
                ", description='" + description + '\'' +
                '}';
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

    public RehabilitationState getRehabilitationState() {
        return rehabilitationState;
    }

    public void setRehabilitationState(RehabilitationState rehabilitationState) {
        this.rehabilitationState = rehabilitationState;
    }

    public LocalDate getDateIn() {
        return dateIn;
    }

    public void setDateIn(LocalDate dateIn) throws NullPointerException {
        this.dateIn = Objects.requireNonNull(dateIn, "dateIn cannot be null!");
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void setDateOut(LocalDate dateOut) throws NullPointerException {
        this.dateOut = Objects.requireNonNull(dateOut, "dateOut cannot be null!");
    }
}
