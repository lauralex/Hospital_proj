package com.bell_sic.entity.permission;

import java.security.BasicPermission;

public abstract class PermissionContainer extends BasicPermission {
    public PermissionContainer(String name) {
        super(name);
    }

}
