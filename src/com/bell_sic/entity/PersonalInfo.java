package com.bell_sic.entity;

import java.time.LocalDate;
import java.util.Objects;

public class PersonalInfo {
    private String name;
    private String lastName;
    private LocalDate dateOfBirth;
    private String cityOfBirth;

    /**
     * @param name        The name of the employee.
     * @param lastName    The last name of the employee.
     * @param dateOfBirth The date of birth of the employee.
     * @param cityOfBirth The city of birth of the employee.
     * @throws IllegalArgumentException When any personal info is blank.
     * @throws NullPointerException     When any personal info is {@code null}.
     */
    public PersonalInfo(String name, String lastName, LocalDate dateOfBirth, String cityOfBirth) throws IllegalArgumentException, NullPointerException {
        if (name.isBlank()) throw new IllegalArgumentException("Name cannot be empty!");
        if (lastName.isBlank()) throw new IllegalArgumentException("Last name cannot be empty!");
        if (cityOfBirth.isBlank()) throw new IllegalArgumentException("City of birth cannot be empty!");

        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Date of birth cannot be null");
        this.cityOfBirth = cityOfBirth;
    }

    @Override
    public String toString() {
        return "PersonalInfo{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", cityOfBirth='" + cityOfBirth + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    /**
     * @param name The name of the employee.
     * @throws IllegalArgumentException When {@code name} is blank.
     * @throws NullPointerException     When {@code name} is {@code null}.
     */
    public void setName(String name) throws IllegalArgumentException, NullPointerException {
        if (name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty!");
        }
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName The last name of the employee.
     * @throws IllegalArgumentException When {@code lastName} is blank.
     * @throws NullPointerException     When {@code lastName} is {@code null}.
     */
    public void setLastName(String lastName) throws IllegalArgumentException, NullPointerException {
        if (lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be empty!");
        }
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * @param dateOfBirth The date of birth of the employee.
     * @throws NullPointerException When {@code dateOfBirth} is {@code null}.
     */
    public void setDateOfBirth(LocalDate dateOfBirth) throws NullPointerException {
        this.dateOfBirth = Objects.requireNonNull(dateOfBirth, "Date of birth cannot be null!");
    }

    public String getCityOfBirth() {
        return cityOfBirth;
    }

    /**
     * @param cityOfBirth The city of birth of the employee.
     * @throws IllegalArgumentException When the {@code cityOfBirth} is blank.
     * @throws NullPointerException     When the {@code cityOfBirth} is {@code null}.
     */
    public void setCityOfBirth(String cityOfBirth) throws IllegalArgumentException, NullPointerException {
        if (cityOfBirth.isBlank()) {
            throw new IllegalArgumentException("City of birth cannot be empty!");
        }
        this.cityOfBirth = cityOfBirth;
    }
}
