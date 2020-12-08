package com.bell_sic.entity.permission;

public class ExitPermission extends ReadPermission {
    private static ExitPermission instance;

    public static ExitPermission get() {
        if (instance == null) {
            instance = new ExitPermission();
        }
        return instance;
    }

    private ExitPermission() {
        super("hospital.exit");
    }
}
