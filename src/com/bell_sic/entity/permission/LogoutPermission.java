package com.bell_sic.entity.permission;

public class LogoutPermission extends ReadPermission {
    private static LogoutPermission instance;

    public static LogoutPermission get() {
        if (instance == null) {
            instance = new LogoutPermission();
        }
        return instance;
    }

    private LogoutPermission() {
        super("hospital.logout");
    }
}
