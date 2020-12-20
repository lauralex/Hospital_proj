package com.bell_sic.entity.employees;

import com.bell_sic.entity.Operation;

import java.util.Objects;

public abstract class DoctorBuilderAdapter extends EmployeeBuilderAdapter {
    private Operation operationOfCompetence;

    public EmployeeBuilder setCompetence(Operation operation) throws NullPointerException {
        operationOfCompetence = Objects.requireNonNull(operation, "Operation cannot be null!");
        return this;
    }

    protected Operation getCompetence() {
        return operationOfCompetence;
    }
}
