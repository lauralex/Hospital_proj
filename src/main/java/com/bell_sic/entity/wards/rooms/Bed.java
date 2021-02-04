package com.bell_sic.entity.wards.rooms;

import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.Patient;
import com.bell_sic.entity.wards.Ward;

import java.time.Period;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bed bed = (Bed) o;
        return bedCode.equals(bed.bedCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bedCode);
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

    public Room getRoom() throws NoSuchElementException {
        return Hospital.WardView.getWards().stream().map(Ward::getRooms)
                .collect(HashSet<Room>::new, HashSet::addAll, HashSet::addAll)
                .stream().filter(room -> room.getBeds().contains(this)).findAny().orElseThrow(() -> new NoSuchElementException("No room found!"));

    }
}
