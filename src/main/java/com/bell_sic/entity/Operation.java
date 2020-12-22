package com.bell_sic.entity;

import java.util.Objects;

public class Operation {
    private final String description;
    private final OperationType operationType;

    public Operation(String description, OperationType operationType) throws NullPointerException, IllegalArgumentException {
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank!");
        }
        this.description = description;
        this.operationType = Objects.requireNonNull(operationType, "operation type cannot be null!");
    }

    public String getDescription() {
        return description;
    }

    public OperationType getOperationType() {
        return operationType;
    }
}
