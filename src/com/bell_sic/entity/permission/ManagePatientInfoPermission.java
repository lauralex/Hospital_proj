package com.bell_sic.entity.permission;

public class ManagePatientInfoPermission extends WritePermission implements ReadPermissionInt {
    private static class InstanceHolder {
        private static final ManagePatientInfoPermission instance = new ManagePatientInfoPermission();
    }

    private ManagePatientInfoPermission() {
        super("hospital.manage_patient");
    }

    public static ManagePatientInfoPermission get() {
        return InstanceHolder.instance;
    }

    @Override
    public String getActions() {
        return "read_write_capability_" + getName();
    }
}
