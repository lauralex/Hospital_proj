package com.bell_sic.entity.wards;

import com.bell_sic.entity.Operation;
import com.bell_sic.entity.Patient;
import com.bell_sic.entity.employees.Employee;
import com.bell_sic.entity.wards.rooms.Room;

import java.util.*;

public abstract class Ward {
    // TODO change all these fields to "Set" instead of "List" (no duplicates allowed)
    private final List<Employee> employees = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();
    private final Set<Operation> operations = new HashSet<>();

    public void addEmployeeToWard(Employee employee) throws NullPointerException {
        employees.add(Objects.requireNonNull(employee, "Employee cannot be null!"));
    }

    public boolean removeEmployeeFromWard(Employee employee) throws NullPointerException {
        return employees.remove(Objects.requireNonNull(employee, "Employee cannot be null!"));
    }

    public void addPatientToWard(Patient patient) throws NullPointerException {
        patients.add(Objects.requireNonNull(patient, "Patient cannot be null!"));
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Patient> getPatients() {
        return patients;
    }



    public String toString() {
        return getClass().getSimpleName();
    }

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
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
        if (!operations.add(operation)) {
            throw new IllegalStateException("Duplicate operation!");
        }
    }

    public boolean removeOperation(Operation operation) throws NullPointerException {
        return operations.remove(Objects.requireNonNull(operation, "Operation cannot be null!"));
    }
}
