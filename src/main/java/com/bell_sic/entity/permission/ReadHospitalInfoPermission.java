package com.bell_sic.entity.permission;

public class ReadHospitalInfoPermission extends ReadPermission {
    private static class InstanceHolder {
        private static final ReadHospitalInfoPermission instance = new ReadHospitalInfoPermission();
    }

    public static ReadHospitalInfoPermission get() {
        return InstanceHolder.instance;
    }

    private ReadHospitalInfoPermission() {
        super("hospital.info");
    }
}
