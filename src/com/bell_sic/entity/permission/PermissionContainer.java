package com.bell_sic.entity.permission;

import java.security.BasicPermission;

public abstract class PermissionContainer extends BasicPermission {
    /**
     * @param name The permission path.
     * @throws NullPointerException If {@code name} is {@code null}.
     * @throws IllegalArgumentException If {@code name} is empty.
     */
    public PermissionContainer(String name) throws NullPointerException, IllegalArgumentException {
        super(name);
    }

}
