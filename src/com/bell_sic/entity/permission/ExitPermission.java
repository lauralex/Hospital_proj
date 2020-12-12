package com.bell_sic.entity.permission;

public class ExitPermission extends ReadPermission {
    private static class InstanceHolder {
        private static final ExitPermission instance = new ExitPermission();
    }

    public static ExitPermission get() {
        return InstanceHolder.instance;
    }

    private ExitPermission() {
        super("hospital.exit");
    }
}
