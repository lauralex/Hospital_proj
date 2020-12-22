package com.bell_sic.entity.employees;

import com.bell_sic.entity.permission.PermissionContainer;

import java.security.Permissions;

public interface EmployeeBuilder {
    EmployeeBuilder addPermission(PermissionContainer permission);

    EmployeeBuilder setAllowedPermissions(Permissions permissions);

    EmployeeBuilder setDisallowedPermissions(Permissions permissions);

    EmployeeBuilder removePermission(PermissionContainer permission) throws IllegalArgumentException;

    Employee build();
}
