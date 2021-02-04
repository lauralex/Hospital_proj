package com.bell_sic.entity.employees;

import com.bell_sic.entity.permission.PermissionContainer;

import java.security.Permissions;
import java.util.Objects;

public abstract class EmployeeBuilderAdapter implements EmployeeBuilder {
    private Permissions allowedPermissions = new Permissions();
    private Permissions disallowedPermissions = new Permissions();

    @Override
    public EmployeeBuilder addPermission(PermissionContainer permission) {
        allowedPermissions.add(permission);
        Permissions newDisallowedPermissions = new Permissions();
        disallowedPermissions.elements().asIterator().forEachRemaining(disallowedPermission -> {
            if (!disallowedPermission.implies(permission)) newDisallowedPermissions.add(disallowedPermission);
        });
        disallowedPermissions = newDisallowedPermissions;
        return this;
    }


    protected Permissions getAllowedPermissions() {
        return allowedPermissions;
    }

    protected Permissions getDisallowedPermissions() {
        return disallowedPermissions;
    }

    /**
     * @param employee The {@linkplain Employee} object to complete.
     * @return The completed {@linkplain Employee} object.
     * @throws NullPointerException If {@code employee} is {@code null}.
     */
    protected Employee builderHelper(Employee employee) throws NullPointerException {
        getAllowedPermissions().elements().asIterator().forEachRemaining(permission -> employee.addPermission((PermissionContainer) permission));
        getDisallowedPermissions().elements().asIterator().forEachRemaining(permission -> employee.removePermission((PermissionContainer) permission));
        return employee;
    }

    @Override
    public EmployeeBuilder setAllowedPermissions(Permissions permissions) throws NullPointerException {
        allowedPermissions = Objects.requireNonNull(permissions);
        return this;
    }

    @Override
    public EmployeeBuilder setDisallowedPermissions(Permissions permissions) throws NullPointerException {
        disallowedPermissions = Objects.requireNonNull(permissions);
        return this;
    }

    @Override
    public EmployeeBuilder removePermission(PermissionContainer permission) throws IllegalArgumentException, NullPointerException {
        if (permission.getName().contains("*")) {
            throw new IllegalArgumentException("The specified permission cannot contain wildcards!");
        }
        disallowedPermissions.add(permission);
        return this;
    }

    public abstract Employee build();

}
