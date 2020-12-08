package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Admin;
import com.bell_sic.entity.Doctor;
import com.bell_sic.entity.Employee;
import com.bell_sic.entity.permission.*;
import com.bell_sic.state_machine.SessionManager;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;
import java.time.LocalDate;

public class Login extends UIState {
    public Login() {
        super(StateId.Login);
    }

    @Override
    public void executeUI() {
        while (true) {
            System.out.print("Enter username: ");
            try {
                String username = ConsoleLineReader.getBufferedReader().readLine();
                System.out.print("Enter password: ");
                String password = ConsoleLineReader.getBufferedReader().readLine();

                if (checkCredentials(new Credentials(username, password))) {
                    UILoop.setTransition(Transition.GoToMainMenu);
                    break;
                }
            } catch (IOException e) {
                System.err.println("Cannot read the input!");
                e.printStackTrace();
            }
        }
    }

    private boolean checkCredentials(Credentials credentials) {
        if (credentials.equals(new Credentials("lauralex", "coccode"))) {
            var admin = Admin.builder(new Employee.PersonalInfo("alex", "bell",
                    LocalDate.now(), "Bronte"), new Credentials("lauralex", "coccode"))
                    .addPermission(ReadHospitalInfoPermission.get()).build();
            Employee.addEmployee(admin);

            admin.addPermission(LogoutPermission.get());
            admin.addPermission(ExitPermission.get());

            SessionManager.setCurrentUser(admin);
        } else {
            var doctor = Doctor.builder(new Employee.PersonalInfo("Cacca",
                    "boggio", LocalDate.now(), "bront"), new Credentials(
                            "menga", "ciliegia"
            )).build();

            doctor.addPermission(LogoutPermission.get());
            doctor.addPermission(ExitPermission.get());

            SessionManager.setCurrentUser(doctor);
        }
        return true;

    }
}
