package com.bell_sic.entity.wards;

import com.bell_sic.entity.Operation;
import com.bell_sic.entity.Patient;
import com.bell_sic.entity.employees.Employee;
import com.bell_sic.entity.wards.rooms.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Ward {
    private final List<Employee> employees = new ArrayList<>();
    private final List<Patient> patients = new ArrayList<>();
    private final List<Room> rooms = new ArrayList<>();
    private final List<Operation> operations = new ArrayList<>();

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



    public abstract String toString();

    public List<Room> getRooms() {
        return Collections.unmodifiableList(rooms);
    }

    public void addRoom(Room room) throws NullPointerException {
        rooms.add(Objects.requireNonNull(room, "Room cannot be null!"));
    }

    public boolean removeRoom(Room room) throws NullPointerException {
        return rooms.remove(Objects.requireNonNull(room, "Room cannot be null"));
    }

    public List<Operation> getOperations() {
        return Collections.unmodifiableList(operations);
    }

    public void addOperation(Operation operation) throws NullPointerException {
        operations.add(Objects.requireNonNull(operation, "Operation cannot be null!"));
    }

    public boolean removeOperation(Operation operation) throws NullPointerException {
        return operations.remove(Objects.requireNonNull(operation, "Operation cannot be null!"));
    }
}
