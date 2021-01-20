package com.bell_sic.entity;

import com.bell_sic.entity.wards.Ward;

import java.util.*;

public class PatientView {
    public static Optional<Ward> getPatientWardQuery(Patient patient) throws NullPointerException {
        return Hospital.WardView.getWards().stream().filter(ward -> ward.getPatients()
                .contains(Objects.requireNonNull(patient, "Cannot search for null patient!"))).findAny();
    }

    public static void reassignPatientToWard(Patient patient, Ward ward) throws NullPointerException, NoSuchElementException {
        getPatientWardQuery(Objects.requireNonNull(patient, "Patient cannot be null!")).orElseThrow().getPatients().remove(patient);
        ward.addPatientToWard(patient);
    }

    public static List<Patient> getAllPatients() {
        return Hospital.WardView.getWards().stream().map(Ward::getPatients).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);
    }
}
