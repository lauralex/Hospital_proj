package com.bell_sic.entity;

import com.bell_sic.entity.permission.Credentials;
import com.bell_sic.entity.permission.PermissionContainer;
import com.bell_sic.entity.permission.ReadPermissionInt;
import com.bell_sic.entity.permission.WritePermissionInt;

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
        this(personalInfo.getName(), personalInfo.getLastName(), credentials.getUserName(), credentials.getPassword(), personalInfo.getDateOfBirth(), personalInfo.getCityOfBirth());
    }

    public static void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public static List<Employee> getAll() {
        return employees;
    }

    public static List<Employee> searchEmployeeByName(String name) {
        var results = employees.stream().filter(employee -> (employee.getPersonalInfo().getName() + employee.getPersonalInfo().getLastName()).contains(name));
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

    public PersonalInfo getPersonalInfo() {
        return personalInfo;
    }

    public Credentials getCredentials() {
        return credentials;
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
            getAllowedPermissions().elements().asIterator().forEachRemaining(permission -> employee.addPermission((PermissionContainer) permission));
            getDisallowedPermissions().elements().asIterator().forEachRemaining(permission -> employee.addPermission((PermissionContainer) permission));
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

        @Override
        public String toString() {
            return "PersonalInfo{" +
                    "name='" + name + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", dateOfBirth=" + dateOfBirth +
                    ", cityOfBirth='" + cityOfBirth + '\'' +
                    '}';
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
