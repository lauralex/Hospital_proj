package com.bell_sic.entity.employees;

public abstract class DoctorBuilderAdapter extends EmployeeBuilderAdapter {
    private String qualification = "No buoino";

    protected String getQualification() {
        return qualification;
    }
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
