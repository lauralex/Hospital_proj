package com.bell_sic.entity.wards;

import com.bell_sic.entity.Employee;
import com.bell_sic.entity.Patient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class Ward {
    private final List<Employee> employees = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();

    public void addEmployeeToWard(Employee employee) {
        employees.add(Objects.requireNonNull(employee, "Employee cannot be null!"));
    }

    public void addPatientToWard(Patient patient) {
        patients.add(Objects.requireNonNull(patient, "Patient cannot be null!"));
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Patient> getPatients() {
        return patients;
    }



    public abstract String toString();

}
