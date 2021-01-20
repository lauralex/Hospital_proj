package com.bell_sic.entity;

import com.bell_sic.entity.wards.Ward;

import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Operation {
    private final String description;
    private final OperationType operationType;
    private final List<Period> possibleRehabilitationDurations = new ArrayList<>();

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

    public List<Period> getPossibleRehabilitationDurations() {
        return Collections.unmodifiableList(possibleRehabilitationDurations);
    }

    public void addPossibleDuration(Period period) throws NullPointerException {
        possibleRehabilitationDurations.add(Objects.requireNonNull(period, "Period cannot be null!"));
    }

    public boolean removePossibleDuration(Period period) throws NullPointerException {
        return possibleRehabilitationDurations.remove(Objects.requireNonNull(period, "Period cannot be null!"));
    }

    public Ward getWard() {
        throw new UnsupportedOperationException();
        // TODO to implement
    }
}
