package com.bell_sic.state_machine;

import com.bell_sic.entity.Employee;

public class SessionManager {
    private static Employee currentUser;

    public static Employee getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Employee currentUser) {
        SessionManager.currentUser = currentUser;
    }
}
