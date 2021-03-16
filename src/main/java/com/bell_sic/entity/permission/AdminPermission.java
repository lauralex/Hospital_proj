package com.bell_sic.entity.permission;

public class AdminPermission extends WritePermission implements ReadPermissionInt {
    /**
     * @param name The Write-Permission path.
     * @throws NullPointerException     If {@code name} is {@code null}.
     * @throws IllegalArgumentException If {@code name} is empty.
     */
    private AdminPermission() throws NullPointerException, IllegalArgumentException {
        super("hospital.admin");
    }

    private static class InstanceHolder {
        private static final AdminPermission instance = new AdminPermission();
    }

    public static AdminPermission get() {
        return AdminPermission.InstanceHolder.instance;
    }
}
