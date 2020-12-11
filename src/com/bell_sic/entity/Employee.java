package com.bell_sic.entity;

import com.bell_sic.entity.permission.Credentials;
import com.bell_sic.entity.permission.PermissionContainer;
import com.bell_sic.entity.permission.ReadPermissionInt;
import com.bell_sic.entity.permission.WritePermissionInt;

import java.security.Permissions;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class Employee {
    private static final List<Employee> employees = new ArrayList<>();
    private final Credentials credentials;
    private final PersonalInfo personalInfo;
    private Permissions allowedPermissions = new Permissions();
    private Permissions disallowedPermissions = new Permissions();

    public Employee(String name, String lastName, String userName, String password, LocalDate dateOfBirth, String cityOfBirth) {
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

    /**
     * @param employee The {@linkplain Employee} object to add to the list.
     * @throws NullPointerException If {@code employee} is {@code null}.
     */
    public static void addEmployee(Employee employee) throws NullPointerException {
        employees.add(Objects.requireNonNull(employee));
    }

    public static List<Employee> getAll() {
        return employees;
    }

    /**
     * @param name The name of the employee to search for.
     * @return A list of found employees with the given {@code name}.
     * @throws NullPointerException If {@code name} is {@code null}.
     */
    public static List<Employee> searchEmployeeByName(String name) throws NullPointerException {
        var results = employees.stream().filter(employee -> (employee.getPersonalInfo().getName()
                + employee.getPersonalInfo().getLastName()).contains(Objects.requireNonNull(name, "Name cannot be null!")));
        return results.collect(Collectors.toList());
    }

    /**
     * @param type The employee category as a class-type.
     * @return A list of found employees.
     * @throws NullPointerException If {@code type} is {@code null}.
     */
    public static List<Employee> searchEmployeeByType(Class<? extends Employee> type) throws NullPointerException {
        var results = employees.stream().filter(type::isInstance);
        return results.collect(Collectors.toList());
    }

    /**
     * @param employee Remove the employee from the list.
     * @return True, if the employee was removed successfully.
     * @throws NullPointerException When {@code employee} is {@code null}.
     */
    public static boolean removeEmployee(Employee employee) throws NullPointerException {
        return employees.remove(Objects.requireNonNull(employee));
    }

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

    public static class PersonalInfo {
        private String name;
        private String lastName;
        private LocalDate dateOfBirth;
        private String cityOfBirth;

        /**
         * @param name The name of the employee.
         * @param lastName The last name of the employee.
         * @param dateOfBirth The date of birth of the employee.
         * @param cityOfBirth The city of birth of the employee.
         * @throws IllegalArgumentException When any personal info is blank.
         * @throws NullPointerException When any personal info is {@code null}.
         */
        public PersonalInfo(String name, String lastName, LocalDate dateOfBirth, String cityOfBirth) throws IllegalArgumentException, NullPointerException {
            if (name.isBlank()) throw new IllegalArgumentException("Name cannot be empty!");
            if (lastName.isBlank()) throw new IllegalArgumentException("Last name cannot be empty!");
            if (cityOfBirth.isBlank()) throw new IllegalArgumentException("City of birth cannot be empty!");

            this.name = name;
            this.lastName = lastName;
            this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Date of birth cannot be null");
            this.cityOfBirth = cityOfBirth;
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

        /**
         * @param name The name of the employee.
         * @throws IllegalArgumentException When {@code name} is blank.
         * @throws NullPointerException     When {@code name} is {@code null}.
         */
        public void setName(String name) throws IllegalArgumentException, NullPointerException {
            if (name.isBlank()) {
                throw new IllegalArgumentException("Name cannot be empty!");
            }
            this.name = name;
        }

        public String getLastName() {
            return lastName;
        }

        /**
         * @param lastName The last name of the employee.
         * @throws IllegalArgumentException When {@code lastName} is blank.
         * @throws NullPointerException     When {@code lastName} is {@code null}.
         */
        public void setLastName(String lastName) throws IllegalArgumentException, NullPointerException {
            if (lastName.isBlank()) {
                throw new IllegalArgumentException("Last name cannot be empty!");
            }
            this.lastName = lastName;
        }

        public LocalDate getDateOfBirth() {
            return dateOfBirth;
        }

        /**
         * @param dateOfBirth The date of birth of the employee.
         * @throws NullPointerException When {@code dateOfBirth} is {@code null}.
         */
        public void setDateOfBirth(LocalDate dateOfBirth) throws NullPointerException {
            this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Date of birth cannot be null!");
        }

        public String getCityOfBirth() {
            return cityOfBirth;
        }

        /**
         * @param cityOfBirth The city of birth of the employee.
         * @throws IllegalArgumentException When the {@code cityOfBirth} is blank.
         * @throws NullPointerException When the {@code cityOfBirth} is {@code null}.
         */
        public void setCityOfBirth(String cityOfBirth) throws IllegalArgumentException, NullPointerException {
            if (cityOfBirth.isBlank()) {
                throw new IllegalArgumentException("City of birth cannot be empty!");
            }
            this.cityOfBirth = cityOfBirth;
        }
    }
}
