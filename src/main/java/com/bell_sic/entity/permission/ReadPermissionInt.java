package com.bell_sic.entity.permission;

/**
 * All Read-Permissions must implement this interface.
 */
public interface ReadPermissionInt {
    default String getActions() {
        return "read_capability_";
    }
}
