package com.bell_sic.entity;

import com.bell_sic.entity.permission.*;

import java.time.LocalDate;

public class Admin extends Employee {
    public Admin(String name, String lastName, String userName, String password, LocalDate dateOfBirth, String cityOfBirth) {
        super(name, lastName, userName, password, dateOfBirth, cityOfBirth);
    }

    public Admin(PersonalInfo personalInfo, Credentials credentials) {
        super(personalInfo, credentials);
        addPermission(ReadHospitalInfoPermission.get());
        addPermission(WriteHospitalInfoPermission.get());
        addPermission(ExitPermission.get());
        addPermission(LogoutPermission.get());
    }

    public static Builder builder(PersonalInfo personalInfo, Credentials credentials) {
        return new EmployeeBuilderAdapter() {
            @Override
            public Employee build() {
                return builderHelper(new Admin(personalInfo, credentials));
            }
        };
    }

}
