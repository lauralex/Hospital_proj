package com.bell_sic.entity.wards;

import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.employees.Doctor;
import javassist.bytecode.DuplicateMemberException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class WardTest {
    private final Doctor doc = new Doctor("ciccio", "ciccia", "lauralex", "coccode",
            LocalDate.now(), "bronte");
    private final EmergencyWard testWard = new EmergencyWard();

    @BeforeEach
    void setUp() {
        Hospital.WardView.addWard(testWard);
    }

    @Test
    @DisplayName("Doctor registration test")
    void doctorRegistration() throws DuplicateMemberException {
        testWard.addEmployeeToWard(doc);
        Assertions.assertThrows(DuplicateMemberException.class, () -> testWard.addEmployeeToWard(doc),
                "Duplicate member check failed!");
        Assertions.assertTrue(testWard.getEmployees().contains(doc), "Doctor registration failed!");
    }
}