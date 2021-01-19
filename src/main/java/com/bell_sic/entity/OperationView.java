package com.bell_sic.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class OperationView {
    private static Set<Operation> operations = new HashSet<>();

    public static Set<Operation> getOperations() {
        return operations;
    }

    /**
     * @param operations a {@linkplain Set<Operation>} of operations.
     * @throws NullPointerException If {@code operations} is {@code null}.
     */
    public static void setOperations(Set<Operation> operations) throws NullPointerException {
        OperationView.operations = Objects.requireNonNull(operations);
    }

    /**
     * Add an {@link Operation} to the View.
     * @param operation The {@linkplain Operation} you want to add to the View.
     * @return {@code True} if the operation was not already present in the View.
     */
    public static boolean add(Operation operation) {
        return operations.add(Objects.requireNonNull(operation, "Operation cannot be null!"));
    }

    /**
     * Remove an {@link Operation} from the View.
     * @param operation The {@linkplain Operation} you want to remove from the View.
     * @return {@code True} if the operation was contained in the View.
     */
    public static boolean remove(Operation operation) {
        return operations.remove(operation);
    }
}
