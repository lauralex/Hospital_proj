package com.bell_sic.entity.permission;

public class ReadHospitalInfoPermission extends ReadPermission {
    private static ReadHospitalInfoPermission instance;

    public static ReadHospitalInfoPermission get() {
        if (instance == null) {
            instance = new ReadHospitalInfoPermission();
        }
        return instance;
    }

    private ReadHospitalInfoPermission() {
        super("hospital.info");
    }
}
