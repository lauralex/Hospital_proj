package com.bell_sic.entity.permission;

public class WriteHospitalInfoPermission extends WritePermission {
    private static WriteHospitalInfoPermission instance;

    public static WriteHospitalInfoPermission get() {
        if (instance == null) {
            instance = new WriteHospitalInfoPermission();
        }
        return instance;
    }

    private WriteHospitalInfoPermission() {
        super("hospital.info");
    }
}
