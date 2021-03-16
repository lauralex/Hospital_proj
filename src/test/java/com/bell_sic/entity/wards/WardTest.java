package com.bell_sic.entity.wards;

import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.employees.Doctor;
import com.bell_sic.entity.employees.Receptionist;
import javassist.bytecode.DuplicateMemberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

// Unit tests for doctor and receptionist registration
class WardTest {
    private final Doctor doc = new Doctor("ciccio", "ciccia", "lauralex", "coccode",
            LocalDate.now(), "bronte");
    private final Receptionist receptionist = new Receptionist("ciccio", "palla", "piccolo",
            "grande", LocalDate.now(), "Catania");
    private final EmergencyWard testWard = new EmergencyWard();

    @BeforeEach
    void setUp() {
        Hospital.WardView.clearWards();
        Hospital.WardView.addWard(testWard);
    }

    @Test
    @DisplayName("Doctor registration test")
    void doctorRegistration() throws DuplicateMemberException {
        testWard.addEmployeeToWard(doc);
        Assertions.assertThrows(DuplicateMemberException.class, () -> testWard.addEmployeeToWard(doc),
                "Duplicate member check failed! (Doctor)");
        Assertions.assertTrue(testWard.getEmployees().contains(doc), "Doctor registration failed!");
    }

    @Test
    @DisplayName("Receptionist registration test")
    void receptionistRegistration() throws DuplicateMemberException {
        testWard.addEmployeeToWard(receptionist);
        Assertions.assertThrows(DuplicateMemberException.class, () -> testWard.addEmployeeToWard(receptionist),
                "Duplicate member check failed! (Receptionist)");
        Assertions.assertTrue(testWard.getEmployees().contains(receptionist), "Receptionist registration failed!");
    }
}