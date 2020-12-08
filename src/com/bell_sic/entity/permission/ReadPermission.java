package com.bell_sic.entity.permission;

public abstract class ReadPermission extends PermissionContainer implements ReadPermissionInt {
    public ReadPermission(String name) {
        super(name);
    }

    @Override
    public String getActions() {
        return "read_capability_" + getName();
    }
}
