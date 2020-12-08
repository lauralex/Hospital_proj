package com.bell_sic.entity.permission;

public class ManagePatientInfoPermission extends WritePermission implements ReadPermissionInt {
    private static ManagePatientInfoPermission instance;

    private ManagePatientInfoPermission() {
        super("hospital.manage_patient");
    }

    public static ManagePatientInfoPermission get() {
        if (instance == null) {
            instance = new ManagePatientInfoPermission();
        }
        return instance;
    }

    @Override
    public String getActions() {
        return "read_write_capability_" + getName();
    }
}
