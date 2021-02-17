package com.bell_sic.entity;

import com.bell_sic.UILoop;
import com.bell_sic.entity.employees.Employee;
import com.bell_sic.entity.employees.Receptionist;
import com.bell_sic.entity.wards.Ward;
import com.bell_sic.state_machine.Transition;
import javassist.bytecode.DuplicateMemberException;

import java.util.*;
import java.util.stream.Collectors;

public class Hospital {
    private final Set<Ward> wards = new HashSet<>();
    private final Set<Receptionist> receptionists = new HashSet<>();

    public void registerAppointment() {
        UILoop.setTransition(Transition.GoToAppointmentRegistrationMenu);
    }

    public void addOperation() {
        UILoop.setTransition(Transition.GoToAddOperationMenu);
    }

    public void registerNewDoctor() {
        UILoop.setTransition(Transition.GoToAddDoctorMenu);
    }

    public void replaceDoctor() {
        UILoop.setTransition(Transition.GoToReplaceDoctorMenu);
    }

    public Set<Receptionist> getReceptionists() {
        return Collections.unmodifiableSet(receptionists);
    }

    public void addReceptionist(Receptionist receptionist) {
        receptionists.add(Objects.requireNonNull(receptionist, "Receptionist cannot be null!"));
    }

    private static class InstanceHolder {
        private static final Hospital instance = new Hospital();
    }

    public static Hospital get() {
        return InstanceHolder.instance;
    }

    public static class WardView {
        // private static final Set<Ward> wards = new HashSet<>();

        public static void addWard(Ward ward) throws NullPointerException {
            get().wards.add(Objects.requireNonNull(ward, "Ward cannot be null!"));
        }

        public static void clearWards() {
            get().wards.clear();
        }

        public static boolean removeWard(Ward ward) throws NullPointerException {
            return get().wards.remove(Objects.requireNonNull(ward, "Ward cannot be null!"));
        }

        public static Set<Ward> getWards() {
            return get().wards;
        }

        public static Optional<Ward> getWardByType(Class<? extends Ward> wardType) throws NullPointerException {
            return get().wards.stream().filter(wardType::isInstance).findAny();
        }

        public static Ward getAnyWard() throws NoSuchElementException, NullPointerException {
            var defaultWard = get().wards.stream().findAny();
            if (defaultWard.isEmpty()) throw new NoSuchElementException("There is no ward!");
            return Objects.requireNonNull(defaultWard.get(), "Ward cannot be null!");
        }
    }

    public static class OperationView {
        public static Set<Operation> getAllOperations() {
            return WardView.getWards().stream().map(Ward::getOperations).collect(HashSet::new, HashSet::addAll, HashSet::addAll);
        }

        public static boolean containsOperation(Operation operation) {
            return get().wards.stream().anyMatch(ward -> ward.getOperations().contains(Objects.requireNonNull(operation)));
        }

        public static Operation getAnyOperation() throws NoSuchElementException {
            return getAllOperations().stream().findAny().orElseThrow(() -> new NoSuchElementException("There is no operation!"));
        }
    }

    public static class EmployeeView {

        public static Set<Employee> getAllEmployees() {
            return WardView.getWards().stream().map(Ward::getEmployees).collect(HashSet::new, HashSet::addAll, HashSet::addAll);

        }

        public static <T extends Employee> Set<T> getAllEmployeesOfType(Class<T> type) {
            return getAllEmployees().stream().filter(type::isInstance).map(type::cast).collect(Collectors.toUnmodifiableSet());
        }

        public static Optional<Ward> getEmployeeWardQuery(Employee employee) throws NullPointerException {
            return WardView.getWards().stream().filter(ward -> ward.getEmployees()
                    .contains(Objects.requireNonNull(employee, "Employee cannot be null!")) || ward.getEmployees()
            .stream().anyMatch(employee1 -> employee1.getCredentials().getUserName().equals(employee.getCredentials().getUserName()))).findAny();
        }

        public static void reassignEmployeeToWard(Employee employee, Ward ward) throws NullPointerException, NoSuchElementException, DuplicateMemberException {
            getEmployeeWardQuery(Objects.requireNonNull(employee, "Employee cannot be null!")).orElseThrow().getEmployees().remove(employee);
            ward.addEmployeeToWard(employee);
        }

        /**
         * @param name The name of the employee to search for.
         * @return A list of found employees with the given {@code name}.
         * @throws NullPointerException If {@code name} is {@code null}.
         */
        public static Set<Employee> searchEmployeeByName(String name) throws NullPointerException {
            var allEmployees = getAllEmployees();
            var results = allEmployees.stream().filter(employee -> (employee.getPersonalInfo().getName()
                    + " " + employee.getPersonalInfo().getLastName()).contains(Objects.requireNonNull(name, "Name cannot be null!")));
            return results.collect(Collectors.toSet());
        }

        /**
         * @param type The employee category as a class-type.
         * @return A list of found employees.
         * @throws NullPointerException If {@code type} is {@code null}.
         */
        public static <T extends Employee> Set<T> searchEmployeeByType(Class<T> type) throws NullPointerException {
            var allEmployees = getAllEmployees();
            var results = allEmployees.stream().filter(type::isInstance).map(type::cast);
            return results.collect(Collectors.toSet());
        }

        public static <T extends Employee> T getAnyEmployeeOfType(Class<T> type) throws NoSuchElementException {
            return searchEmployeeByType(type).stream().findAny().orElseThrow(() -> new NoSuchElementException("There is no employee of this type"));
        }

        public static Set<Employee> searchEmployeeByWard(Ward ward) throws NullPointerException {
            return ward.getEmployees();
        }

        public static Set<Employee> searchEmployeeByWard(Class<? extends Ward> ward) throws NoSuchElementException, NullPointerException {
            return WardView.getWardByType(ward).orElseThrow().getEmployees();
        }

        public static boolean containsEmployee(Employee employee) throws NullPointerException {
            return getEmployeeWardQuery(employee).isPresent();
        }

        /**
         * @param employee Remove the employee from the list.
         * @return True, if the employee was removed successfully.
         * @throws NullPointerException When {@code employee} is {@code null}.
         */
        public static boolean removeEmployee(Employee employee) throws NullPointerException {
            return WardView.getWards().stream().anyMatch(ward -> ward.getEmployees().remove(Objects.requireNonNull(employee)));
        }
    }
}
