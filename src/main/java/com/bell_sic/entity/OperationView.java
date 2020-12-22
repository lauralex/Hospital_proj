package com.bell_sic.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class OperationView {
    private static Set<Operation> operations = new HashSet<>();

    public static Set<Operation> getOperations() {
        return operations;
    }

    public static void setOperations(Set<Operation> operations) throws NullPointerException {
        OperationView.operations = Objects.requireNonNull(operations);
    }

    public static boolean add(Operation operation) {
        return operations.add(Objects.requireNonNull(operation, "Operation cannot be null!"));
    }

    public static boolean remove(Operation operation) {
        return operations.remove(operation);
    }
}
