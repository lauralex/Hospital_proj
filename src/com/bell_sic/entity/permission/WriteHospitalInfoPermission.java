package com.bell_sic.entity.permission;

public class WriteHospitalInfoPermission extends WritePermission {
    private static class InstanceHolder {
        private static final WriteHospitalInfoPermission instance = new WriteHospitalInfoPermission();
    }

    public static WriteHospitalInfoPermission get() {
        return InstanceHolder.instance;
    }

    private WriteHospitalInfoPermission() {
        super("hospital.info");
    }
}
