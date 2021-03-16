package com.bell_sic.entity.employees;

import java.util.Objects;

public abstract class DoctorBuilderAdapter extends EmployeeBuilderAdapter {
    private String qualification = "No buoino";

    protected String getQualification() {
        return qualification;
    }

    public DoctorBuilderAdapter setQualification(String qualification) throws NullPointerException, IllegalArgumentException {
        if (Objects.requireNonNull(qualification, "qualification cannot be null!").isBlank()) {
            throw new IllegalArgumentException("qualification must not be blank!");
        }
        this.qualification = qualification;
        return this;
    }
}
