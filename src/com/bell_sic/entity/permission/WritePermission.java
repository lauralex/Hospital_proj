package com.bell_sic.entity.permission;

public abstract class WritePermission extends PermissionContainer implements WritePermissionInt {
    /**
     * @param name The Write-Permission path.
     * @throws NullPointerException If {@code name} is {@code null}.
     * @throws IllegalArgumentException If {@code name} is empty.
     */
    public WritePermission(String name) throws NullPointerException, IllegalArgumentException {
        super(name);
    }

    @Override
    public String getActions() {
        return "write_capability_" + getName();
    }
}
