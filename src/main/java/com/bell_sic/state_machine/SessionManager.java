package com.bell_sic.state_machine;

import com.bell_sic.entity.employees.Employee;

/**
 * A utility class that should be used for saving/retrieving session info.
 */
public class SessionManager {
    private static Employee currentUser;

    /**
     * Retrieve the logged-in {@link Employee}.
     * @return The logged-in {@linkplain Employee}.
     */
    public static Employee getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the logged-in {@link Employee}.
     * @param currentUser The logged-in {@linkplain Employee}.
     */
    public static void setCurrentUser(Employee currentUser) {
        SessionManager.currentUser = currentUser;
    }
}
