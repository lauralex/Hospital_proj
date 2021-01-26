package com.bell_sic.entity.wards;

import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.Operation;
import com.bell_sic.entity.Patient;
import com.bell_sic.entity.employees.Employee;
import com.bell_sic.entity.wards.rooms.Room;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public abstract class Ward {
    // TODO change all these fields to "Set" instead of "List" (no duplicates allowed)
    private final Set<Employee> employees = new HashSet<>();
    private final Set<Patient> patients = new HashSet<>();
    private final Set<Room> rooms = new HashSet<>();
    private final Set<Operation> operations = new HashSet<>();

    public void addEmployeeToWard(Employee employee) throws NullPointerException, IllegalStateException {
        if (Hospital.EmployeeView.containsEmployee(employee)) {
            throw new IllegalStateException("Duplicate employee!");
        }
        employees.add(Objects.requireNonNull(employee, "Employee cannot be null!"));
    }

    public boolean removeEmployeeFromWard(Employee employee) throws NullPointerException {
        return employees.remove(Objects.requireNonNull(employee, "Employee cannot be null!"));
    }

    public void addPatientToWard(Patient patient) throws NullPointerException {
        patients.add(Objects.requireNonNull(patient, "Patient cannot be null!"));
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public Set<Patient> getPatients() {
        return patients;
    }



    public String toString() {
        return getClass().getSimpleName();
    }

    public Set<Room> getRooms() {
        return Collections.unmodifiableSet(rooms);
    }

    public void addRoom(Room room) throws NullPointerException {
        rooms.add(Objects.requireNonNull(room, "Room cannot be null!"));
    }

    public boolean removeRoom(Room room) throws NullPointerException {
        return rooms.remove(Objects.requireNonNull(room, "Room cannot be null"));
    }

    public Set<Operation> getOperations() {
        return Collections.unmodifiableSet(operations);
    }

    /**
     * @param operation The {@linkplain Operation} to add to the ward.
     * @throws NullPointerException If {@code operation} is {@code null}.
     * @throws IllegalStateException If {@code operation} already exists in the current ward.
     * @throws IllegalArgumentException If {@code operation} has not enough durations.
     */
    public void addOperation(Operation operation) throws NullPointerException, IllegalStateException, IllegalArgumentException {
        if (operation.getPossibleRehabilitationDurations().size() == 0) {
            throw new IllegalArgumentException("There must be at least ONE rehabilitation duration!");
        }
        if (Hospital.OperationView.containsOperation(operation)) {
            throw new IllegalStateException("Duplicate operation!");
        }
        operations.add(operation);
    }

    public boolean removeOperation(Operation operation) throws NullPointerException {
        return operations.remove(Objects.requireNonNull(operation, "Operation cannot be null!"));
    }
}
