package com.bell_sic.entity.permission;

public abstract class WritePermission extends PermissionContainer {
    public WritePermission(String name) {
        super(name);
    }

    @Override
    public String getActions() {
        return "write_capability_" + getName();
    }
}
