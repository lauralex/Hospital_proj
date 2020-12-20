package com.bell_sic.entity.employees;

import com.bell_sic.entity.PersonalInfo;
import com.bell_sic.entity.permission.Credentials;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.LogoutPermission;
import com.bell_sic.entity.permission.ManagePatientInfoPermission;

import java.time.LocalDate;

public class Receptionist extends Employee {
    public Receptionist(String name, String lastName, String userName, String password, LocalDate dateOfBirth, String cityOfBirth) {
        super(name, lastName, userName, password, dateOfBirth, cityOfBirth);
    }

    public Receptionist(PersonalInfo personalInfo, Credentials credentials) {
        super(personalInfo, credentials);
        addPermission(ExitPermission.get());
        addPermission(LogoutPermission.get());
        addPermission(ManagePatientInfoPermission.get());
    }

    public static EmployeeBuilder builder(PersonalInfo personalInfo, Credentials credentials) {
        return new EmployeeBuilderAdapter() {
            @Override
            public Employee build() {
                return builderHelper(new Receptionist(personalInfo, credentials));
            }
        };
    }

}
