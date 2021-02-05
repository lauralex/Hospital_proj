package com.bell_sic.entity.wards.rooms;

import com.bell_sic.entity.Appointment;

import java.time.LocalDate;
import java.time.Period;
import java.util.Objects;

public class Bed {
    private String bedCode;
    private final Room parentRoom;
    private Appointment appointment;
    private LocalDate dateIn;
    private LocalDate dateOut;

    public Bed(String bedCode, Room room) throws IllegalArgumentException, NullPointerException {
        if (bedCode.isBlank()) {
            throw new IllegalArgumentException("Bedcode cannot be blank!");
        }
        this.parentRoom = Objects.requireNonNull(room);
        this.bedCode = bedCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bed bed = (Bed) o;
        return bedCode.equals(bed.bedCode) && parentRoom.equals(bed.parentRoom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bedCode, parentRoom);
    }

    @Override
    public String toString() {
        return "Bed{" +
                "bedCode='" + bedCode + '\'' +
                ", parentRoom=" + parentRoom +
                ", appointment=" + appointment +
                ", dateIn=" + dateIn +
                ", dateOut=" + dateOut +
                '}';
    }

    public Appointment getPatientAppointment() {
        return appointment;
    }

    public void setPatientAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Period getHospitalizationDuration() {
        return dateIn.until(dateOut);
    }

    public void setHospitalizationDuration(Period hospitalizationDuration) {
        this.dateIn = LocalDate.now();
        this.dateOut = dateIn.plus(hospitalizationDuration);
    }

    public LocalDate getDateIn() {
        return dateIn;
    }

    public LocalDate getDateOut() {
        return dateOut;
    }

    public void removePatient() {
        appointment = null;
        dateIn = null;
        dateOut = null;
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

    public Room getParentRoom() {
        return parentRoom;
    }
}
