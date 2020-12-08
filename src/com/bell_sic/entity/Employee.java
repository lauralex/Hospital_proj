package com.bell_sic.entity;

import com.bell_sic.entity.permission.Credentials;
import com.bell_sic.entity.permission.PermissionContainer;

import java.security.Permissions;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Employee {
    private static final List<Employee> employees = new ArrayList<>();
    private final Credentials credentials = new Credentials();
    private final PersonalInfo personalInfo = new PersonalInfo();
    private Permissions allowedPermissions;
    private Permissions disallowedPermissions;

    public Employee(String name, String lastName, String userName, String password, LocalDate dateOfBirth, String cityOfBirth) {
        this.personalInfo.setName(name);
        this.personalInfo.setLastName(lastName);
        this.credentials.setUserName(userName);
        this.credentials.setPassword(password);
        this.personalInfo.setDateOfBirth(dateOfBirth);
        this.personalInfo.setCityOfBirth(cityOfBirth);
        allowedPermissions = new Permissions();
        disallowedPermissions = new Permissions();
    }

    public Employee(PersonalInfo personalInfo, Credentials credentials) {
        this.personalInfo.setName(personalInfo.getName());
        this.personalInfo.setLastName(personalInfo.getLastName());
        this.credentials.setUserName(credentials.getUserName());
        this.credentials.setPassword(credentials.getPassword());
        this.personalInfo.setDateOfBirth(personalInfo.getDateOfBirth());
        this.personalInfo.setCityOfBirth(personalInfo.getCityOfBirth());
        allowedPermissions = new Permissions();
        disallowedPermissions = new Permissions();
    }

    public static void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public static List<Employee> getAll() {
        return employees;
    }

    public static List<Employee> searchEmployeeByName(String name) {
        var results = employees.stream().filter(employee -> (employee.getName() + employee.getLastName()).contains(name));
        return results.collect(Collectors.toList());
    }

    public static List<Employee> searchEmployeeByType(Class<? extends Employee> type) {
        var results = employees.stream().filter(type::isInstance);
        return results.collect(Collectors.toList());
    }

    public static boolean removeEmployee(Employee employee) {
        return employees.remove(employee);
    }

    public Permissions getAllowedPermissions() {
        return allowedPermissions;
    }

    public void setAllowedPermissions(Permissions allowedPermissions) {
        this.allowedPermissions = allowedPermissions;
    }

    public void addPermission(PermissionContainer permission) {
        allowedPermissions.add(permission);
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
     * @throws IllegalArgumentException if the passed permission contains a wildcard (*).
     */
    public void removePermission(PermissionContainer permission) throws IllegalArgumentException {
        if (permission.getName().contains("*")) {
            throw new IllegalArgumentException("The specified permission cannot contain wildcards!");
        }
        disallowedPermissions.add(permission);
    }

    public String getCityOfBirth() {
        return personalInfo.getCityOfBirth();
    }

    public void setCityOfBirth(String cityOfBirth) {
        this.personalInfo.setCityOfBirth(cityOfBirth);
    }

    public LocalDate getDateOfBirth() {
        return personalInfo.getDateOfBirth();
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.personalInfo.setDateOfBirth(dateOfBirth);
    }

    public String getPassword() {
        return credentials.getPassword();
    }

    public void setPassword(String password) {
        this.credentials.setPassword(password);
    }

    public String getUserName() {
        return credentials.getUserName();
    }

    public void setUserName(String userName) {
        this.credentials.setUserName(userName);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getLastName() {
        return personalInfo.getLastName();
    }

    public void setLastName(String lastName) {
        this.personalInfo.setLastName(lastName);
    }

    public String getName() {
        return personalInfo.getName();
    }

    public void setName(String name) {
        this.personalInfo.setName(name);
    }

    public Permissions getDisallowedPermissions() {
        return disallowedPermissions;
    }

    public void setDisallowedPermissions(Permissions disallowedPermissions) {
        this.disallowedPermissions = disallowedPermissions;
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

        protected Employee builderHelper(Employee employee) {
            employee.setAllowedPermissions(getAllowedPermissions());
            employee.setDisallowedPermissions(getDisallowedPermissions());
            return employee;
        }

        @Override
        public Builder setAllowedPermissions(Permissions permissions) {
            allowedPermissions = permissions;
            return this;
        }

        @Override
        public Builder setDisallowedPermissions(Permissions permissions) {
            disallowedPermissions = permissions;
            return this;
        }

        @Override
        public Builder removePermission(PermissionContainer permission) throws IllegalArgumentException {
            if (permission.getName().contains("*")) {
                throw new IllegalArgumentException("The specified permission cannot contain wildcards!");
            }
            disallowedPermissions.add(permission);
            return this;
        }

        public abstract Employee build();

    }

    public static class PersonalInfo {
        private String name;
        private String lastName;
        private LocalDate dateOfBirth;
        private String cityOfBirth;

        public PersonalInfo(String name, String lastName, LocalDate dateOfBirth, String cityOfBirth) {
            this.name = name;
            this.lastName = lastName;
            this.dateOfBirth = dateOfBirth;
            this.cityOfBirth = cityOfBirth;
        }

        private PersonalInfo() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        public void setDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
        }

        public String getCityOfBirth() {
            return cityOfBirth;
        }

        public void setCityOfBirth(String cityOfBirth) {
            this.cityOfBirth = cityOfBirth;
        }
    }
}
