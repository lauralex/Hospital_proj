package com.bell_sic.entity;

import com.bell_sic.entity.permission.Credentials;
import com.bell_sic.entity.permission.PermissionContainer;
import com.bell_sic.entity.permission.ReadPermissionInt;
import com.bell_sic.entity.permission.WritePermissionInt;

import java.security.Permissions;
import java.time.LocalDate;
import java.util.Objects;

public abstract class Employee {
    private final Credentials credentials;
    private final PersonalInfo personalInfo;
    private Permissions allowedPermissions = new Permissions();
    private Permissions disallowedPermissions = new Permissions();

    public Employee(String name, String lastName, String userName, String password, LocalDate dateOfBirth, String cityOfBirth) throws NullPointerException, IllegalArgumentException {
        this.personalInfo = new PersonalInfo(name, lastName, dateOfBirth, cityOfBirth);
        this.credentials = new Credentials(userName, password);
    }

    /**
     * @param personalInfo The {@linkplain PersonalInfo} of the employee to create.
     * @see PersonalInfo
     * @param credentials The {@linkplain Credentials} of the employee to create.
     * @see Credentials
     * @throws NullPointerException If {@code personalInfo} or {@code credentials} are {@code null}.
     */
    public Employee(PersonalInfo personalInfo, Credentials credentials) throws NullPointerException {
        this.personalInfo = Objects.requireNonNull(personalInfo, "Personal info cannot be null!");
        this.credentials = Objects.requireNonNull(credentials, "Credentials cannot be null!");
    }


    //endregion

    public Permissions getAllowedPermissions() {
        return allowedPermissions;
    }

    /**
     * @param allowedPermissions The permission-list to grant to the employee.
     * @throws NullPointerException If {@code allowedPermissions} is {@code null}.
     */
    public void setAllowedPermissions(Permissions allowedPermissions) throws NullPointerException {
        this.allowedPermissions = Objects.requireNonNull(allowedPermissions, "Allowed permission cannot be null!");
    }

    public Permissions getAllowedReadPermissions() {
        Permissions readPermissions = new Permissions();
        allowedPermissions.elementsAsStream().filter(permission -> permission instanceof ReadPermissionInt).forEach(readPermissions::add);
        return readPermissions;
    }

    public Permissions getAllowedWritePermissions() {
        Permissions allowedWritePermissions = new Permissions();
        allowedPermissions.elementsAsStream().filter(permission -> permission instanceof WritePermissionInt).forEach(allowedWritePermissions::add);
        return allowedWritePermissions;
    }

    /**
     * @param permission The {@code permission} to grant to the employee.
     * @throws NullPointerException If the {@code permission} is {@code null}.
     */
    public void addPermission(PermissionContainer permission) throws NullPointerException {
        allowedPermissions.add(Objects.requireNonNull(permission, "Permission cannot be null!"));
        Permissions newDisallowedPermissions = new Permissions();
        disallowedPermissions.elements().asIterator().forEachRemaining(disallowedPermission -> {
            if (!disallowedPermission.implies(permission)) newDisallowedPermissions.add(disallowedPermission);
        });
        disallowedPermissions = newDisallowedPermissions;
    }

    public boolean checkPermission(PermissionContainer permission) {
        return allowedPermissions.implies(permission) && !disallowedPermissions.implies(permission);
    }

    /**
     * @param permission the permission do deny.
     * @throws IllegalArgumentException If the passed permission contains a wildcard (*).
     * @throws NullPointerException When the passed {@code permission} is {@code null}.
     */
    public void removePermission(PermissionContainer permission) throws IllegalArgumentException, NullPointerException {
        if (permission.getName().contains("*")) {
            throw new IllegalArgumentException("The specified permission cannot contain wildcards!");
        }
        disallowedPermissions.add(permission);
    }

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public Permissions getDisallowedPermissions() {
        return disallowedPermissions;
    }

    /**
     * @param disallowedPermissions The disallowed permissions to set for the employee.
     * @throws NullPointerException When {@code disallowedPermissions} is {@code null}.
     */
    public void setDisallowedPermissions(Permissions disallowedPermissions) throws NullPointerException {
        this.disallowedPermissions = Objects.requireNonNull(disallowedPermissions);
    }

    public interface Builder {
        Builder addPermission(PermissionContainer permission);

        Builder setAllowedPermissions(Permissions permissions);

        Builder setDisallowedPermissions(Permissions permissions);

        Builder removePermission(PermissionContainer permission) throws IllegalArgumentException;

        Employee build();
    }

    public static abstract class EmployeeBuilderAdapter implements Builder {
        private Permissions allowedPermissions = new Permissions();
        private Permissions disallowedPermissions = new Permissions();

        @Override
        public Builder addPermission(PermissionContainer permission) {
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
            getDisallowedPermissions().elements().asIterator().forEachRemaining(permission -> employee.addPermission((PermissionContainer) permission));
            return employee;
        }

        @Override
        public Builder setAllowedPermissions(Permissions permissions) throws NullPointerException {
            allowedPermissions = Objects.requireNonNull(permissions);
            return this;
        }

        @Override
        public Builder setDisallowedPermissions(Permissions permissions) throws NullPointerException {
            disallowedPermissions = Objects.requireNonNull(permissions);
            return this;
        }

        @Override
        public Builder removePermission(PermissionContainer permission) throws IllegalArgumentException, NullPointerException {
            if (permission.getName().contains("*")) {
                throw new IllegalArgumentException("The specified permission cannot contain wildcards!");
            }
            disallowedPermissions.add(permission);
            return this;
        }

        public abstract Employee build();

    }

}
