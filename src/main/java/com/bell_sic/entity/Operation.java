package com.bell_sic.entity;

import java.util.Objects;

public class Operation {
    private final String description;
    private final OperationType operationType;

    /**
     * @param description A brief description of the operation.
     * @param operationType Either {@linkplain OperationType}{@code .SURGERY} or {@linkplain OperationType}{@code .EXAMINATION}.
     * @throws NullPointerException If any argument is {@code null}.
     * @throws IllegalArgumentException If {@code description} is {@code null}.
     */
    public Operation(String description, OperationType operationType) throws NullPointerException, IllegalArgumentException {
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank!");
        }
        this.description = description;
        this.operationType = Objects.requireNonNull(operationType, "operation type cannot be null!");
    }

    /**
     * Get the current operation's description.
     * @return The description of the current operation.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the {@link OperationType} of the current operation.
     * @return The {@linkplain OperationType} of the current operation.
     */
    public OperationType getOperationType() {
        return operationType;
    }
}
