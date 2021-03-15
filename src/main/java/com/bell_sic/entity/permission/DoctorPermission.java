package com.bell_sic.entity.permission;

public class DoctorPermission extends WritePermission {
    /**
     * @throws NullPointerException     If {@code name} is {@code null}.
     * @throws IllegalArgumentException If {@code name} is empty.
     */
    private DoctorPermission() throws NullPointerException, IllegalArgumentException {
        super("hospital.doctor");
    }

    private static class InstanceHolder {
        private static final DoctorPermission instance = new DoctorPermission();
    }

    public static DoctorPermission get() {
        return InstanceHolder.instance;
    }
}
