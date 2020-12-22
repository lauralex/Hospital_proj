package com.bell_sic.entity.employees;

import com.bell_sic.entity.Operation;
import com.bell_sic.entity.PersonalInfo;
import com.bell_sic.entity.permission.Credentials;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.LogoutPermission;
import com.bell_sic.entity.permission.ManagePatientInfoPermission;

import java.time.LocalDate;

public class Doctor extends Employee {
    private Operation operationOfCompetence;

    public Doctor(String name, String lastName, String userName, String password, LocalDate dateOfBirth, String cityOfBirth) {
        super(name, lastName, userName, password, dateOfBirth, cityOfBirth);
    }

    public Doctor(PersonalInfo personalInfo, Credentials credentials) {
        super(personalInfo, credentials);
        addPermission(ExitPermission.get());
        addPermission(LogoutPermission.get());
        addPermission(ManagePatientInfoPermission.get());
    }

    public static EmployeeBuilder builder(PersonalInfo personalInfo, Credentials credentials) {
        return new DoctorBuilderAdapter() {
            @Override
            public Employee build() {
                Doctor doc = (Doctor) builderHelper(new Doctor(personalInfo, credentials));
                doc.setOperationOfCompetence(getCompetence());
                return doc;
            }
        };
    }

    public Operation getOperationOfCompetence() {
        return operationOfCompetence;
    }

    public void setOperationOfCompetence(Operation operationOfCompetence) {
        this.operationOfCompetence = operationOfCompetence;
    }
}
