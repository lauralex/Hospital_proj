package com.bell_sic.entity.permission;

import java.util.Objects;

public class Credentials {
    private String userName;
    private String password;

    /**
     * @param userName The username to be set.
     * @param password The password to be set.
     * @throws NullPointerException If either {@code userName} or {@code password} are {@code null}.
     */
    public Credentials(String userName, String password) throws NullPointerException {
        this.userName = Objects.requireNonNull(userName, "Username cannot be null!");
        this.password = Objects.requireNonNull(password, "Password cannot be null!");
    }

    @Override
    public String toString() {
        return "Credentials{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The username to be set.
     * @throws NullPointerException If {@code userName} is {@code null}.
     * @throws IllegalArgumentException If {@code userName} is empty.
     */
    public void setUserName(String userName) throws NullPointerException, IllegalArgumentException {
        if (userName.isBlank()) throw new IllegalArgumentException("Username cannot be empty!");
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @param password The password to be set.
     * @throws NullPointerException If {@code password} is {@code null}.
     * @throws IllegalArgumentException If {@code password} is empty.
     */
    public void setPassword(String password) throws NullPointerException, IllegalArgumentException {
        if (password.isBlank()) throw new IllegalArgumentException("Password cannot be empty!");
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credentials that = (Credentials) o;
        return Objects.equals(userName, that.userName) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password);
    }
}