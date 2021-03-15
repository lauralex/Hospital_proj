package com.bell_sic.entity.permission;

public class PatientPermission extends ReadPermission{
    /**
     * @throws NullPointerException     If {@code name} is {@code null}.
     * @throws IllegalArgumentException If {@code name} is empty.
     */
    private PatientPermission() throws NullPointerException, IllegalArgumentException {
        super("hospital.patient");
    }

    private static class InstanceHolder {
        private static final PatientPermission instance = new PatientPermission();
    }

    public static PatientPermission get() {
        return InstanceHolder.instance;
    }
}
