package com.bell_sic.entity;

import com.bell_sic.UILoop;
import com.bell_sic.entity.employees.Employee;
import com.bell_sic.entity.employees.Receptionist;
import com.bell_sic.entity.wards.Ward;
import com.bell_sic.state_machine.Transition;

import java.util.*;
import java.util.stream.Collectors;

public class Hospital {
    private final List<Ward> wards = new ArrayList<>();
    private final List<Receptionist> receptionists = new ArrayList<>();

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

    public List<Receptionist> getReceptionists() {
        return Collections.unmodifiableList(receptionists);
    }

    public List<Ward> getWards() {
        return Collections.unmodifiableList(wards);
    }

    public void addWard(Ward ward) {
        wards.add(Objects.requireNonNull(ward, "Ward cannot be null!"));
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
        private static final Set<Ward> wards = new HashSet<>();

        public static void addWard(Ward ward) throws NullPointerException {
            wards.add(Objects.requireNonNull(ward, "Ward cannot be null!"));
        }

        public static void clearWards() {
            wards.clear();
        }

        public static boolean removeWard(Ward ward) throws NullPointerException {
            return wards.remove(Objects.requireNonNull(ward, "Ward cannot be null!"));
        }

        public static Set<Ward> getWards() {
            return wards;
        }

        public static Optional<Ward> getWardByType(Class<? extends Ward> wardType) throws NullPointerException {
            return wards.stream().filter(wardType::isInstance).findAny();
        }

        public static Ward getAnyWard() throws NoSuchElementException, NullPointerException {
            var defaultWard = WardView.getWards().stream().findAny();
            if (defaultWard.isEmpty()) throw new NoSuchElementException("There is no ward!");
            return Objects.requireNonNull(defaultWard.get(), "Ward cannot be null!");
        }
    }

    public static class EmployeeView {
        public static List<Employee> getAllEmployees() {
            return WardView.getWards().stream().map(Ward::getEmployees).collect(ArrayList::new, ArrayList::addAll, ArrayList::addAll);

        }

        public static Optional<Ward> getEmployeeWardQuery(Employee employee) throws NullPointerException {
            return WardView.getWards().stream().filter(ward -> ward.getEmployees()
                    .contains(Objects.requireNonNull(employee, "Employee cannot be null!"))).findAny();
        }

        public static void reassignEmployeeToWard(Employee employee, Ward ward) throws NullPointerException, NoSuchElementException {
            getEmployeeWardQuery(Objects.requireNonNull(employee, "Employee cannot be null!")).orElseThrow().getEmployees().remove(employee);
            ward.addEmployeeToWard(employee);
        }

        /**
         * @param name The name of the employee to search for.
         * @return A list of found employees with the given {@code name}.
         * @throws NullPointerException If {@code name} is {@code null}.
         */
        public static List<Employee> searchEmployeeByName(String name) throws NullPointerException {
            var allEmployees = getAllEmployees();
            var results = allEmployees.stream().filter(employee -> (employee.getPersonalInfo().getName()
                    + " " + employee.getPersonalInfo().getLastName()).contains(Objects.requireNonNull(name, "Name cannot be null!")));
            return results.collect(Collectors.toList());
        }

        /**
         * @param type The employee category as a class-type.
         * @return A list of found employees.
         * @throws NullPointerException If {@code type} is {@code null}.
         */
        public static List<Employee> searchEmployeeByType(Class<? extends Employee> type) throws NullPointerException {
            var allEmployees = getAllEmployees();
            var results = allEmployees.stream().filter(type::isInstance);
            return results.collect(Collectors.toList());
        }

        public static List<Employee> searchEmployeeByWard(Ward ward) throws NullPointerException {
            return ward.getEmployees();
        }

        public static List<Employee> searchEmployeeByWard(Class<? extends Ward> ward) throws NoSuchElementException, NullPointerException {
            return WardView.getWardByType(ward).orElseThrow().getEmployees();
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
