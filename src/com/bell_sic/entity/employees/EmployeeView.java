package com.bell_sic.entity.employees;

import com.bell_sic.entity.wards.Ward;
import com.bell_sic.entity.wards.WardView;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeView {
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
