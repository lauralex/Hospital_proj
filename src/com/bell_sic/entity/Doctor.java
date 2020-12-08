package com.bell_sic.entity;

import com.bell_sic.entity.permission.Credentials;

import java.time.LocalDate;

public class Doctor extends Employee {
    public Doctor(String name, String lastName, String userName, String password, LocalDate dateOfBirth, String cityOfBirth) {
        super(name, lastName, userName, password, dateOfBirth, cityOfBirth);
    }

    public Doctor(PersonalInfo personalInfo, Credentials credentials) {
        super(personalInfo, credentials);
    }

    public static Builder builder(PersonalInfo personalInfo, Credentials credentials) {
        return new EmployeeBuilderAdapter() {
            @Override
            public Employee build() {
                return builderHelper(new Doctor(personalInfo, credentials));
            }
        };
    }
}
