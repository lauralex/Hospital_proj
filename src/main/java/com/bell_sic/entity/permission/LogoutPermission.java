package com.bell_sic.entity.permission;

public class LogoutPermission extends ReadPermission {
    private static class InstanceHolder {
        private static final LogoutPermission instance = new LogoutPermission();
    }

    public static LogoutPermission get() {
        return InstanceHolder.instance;
    }

    private LogoutPermission() {
        super("hospital.logout");
    }
}
