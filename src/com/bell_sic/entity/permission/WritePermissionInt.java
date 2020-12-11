package com.bell_sic.entity.permission;

/**
 * All Write-Permissions must implement this interface.
 */
public interface WritePermissionInt {
    default String getActions() {
        return "write_capability_";
    }
}
