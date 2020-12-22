package com.bell_sic.entity.permission;

public abstract class ReadPermission extends PermissionContainer implements ReadPermissionInt {
    /**
     * @param name The Read-Permission path.
     * @throws NullPointerException If {@code name} is {@code null}.
     * @throws IllegalArgumentException If {@code name} is empty.
     */
    public ReadPermission(String name) throws NullPointerException, IllegalArgumentException {
        super(name);
    }

    @Override
    public String getActions() {
        return "read_capability_" + getName();
    }
}
