package com.bell_sic.entity;

import com.bell_sic.entity.wards.Ward;

import java.time.Period;
import java.util.*;

public class Operation {
    private String description;
    private OperationType operationType;
    private final List<Period> possibleRehabilitationDurations = new ArrayList<>();

    /**
     * @param description A brief description of the operation.
     * @param operationType Either {@linkplain OperationType}{@code .SURGERY} or {@linkplain OperationType}{@code .EXAMINATION}.
     * @throws NullPointerException If any argument is {@code null}.
     * @throws IllegalArgumentException If {@code description} is blank.
     */
    public Operation(String description, OperationType operationType) throws NullPointerException, IllegalArgumentException {
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank!");
        }
        this.description = description;
        this.operationType = Objects.requireNonNull(operationType, "operation type cannot be null!");
    }

    @Override
    public String toString() {
        return "Operation{" +
                "description='" + description + '\'' +
                ", operationType=" + operationType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return description.equals(operation.description) && operationType == operation.operationType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, operationType);
    }

    /**
     * Get the current operation's description.
     * @return The description of the current operation.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description of the operation.
     * @throws NullPointerException If {@code description} is {@code null}.
     * @throws IllegalArgumentException If {@code description} is blank.
     */
    public void setDescription(String description) throws NullPointerException, IllegalArgumentException {
        if (description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be blank!");
        }
        this.description = description;
    }

    /**
     * Get the {@link OperationType} of the current operation.
     * @return The {@linkplain OperationType} of the current operation.
     */
    public OperationType getOperationType() {
        return operationType;
    }

    /**
     * @param operationType The {@linkplain OperationType} you want to set.
     * @throws NullPointerException If {@code operationType} is {@code null}.
     */
    public void setOperationType(OperationType operationType) throws NullPointerException {
        this.operationType = Objects.requireNonNull(operationType, "OperationType cannot be null!");
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

    public Ward getWard() throws NoSuchElementException {
        return Hospital.WardView.getWards().stream().filter(ward -> ward.getOperations().contains(this)).findAny()
                .orElseThrow(() -> new NoSuchElementException("No ward found!"));
    }
}
