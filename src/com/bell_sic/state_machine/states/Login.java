package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Admin;
import com.bell_sic.entity.Employee;
import com.bell_sic.entity.permission.*;
import com.bell_sic.state_machine.SessionManager;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleDelay;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;
import java.time.LocalDate;

public class Login extends UIState {
    private boolean firstInit = true;

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
                } else {
                    ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.RED, "Wrong credentials");
                }
            } catch (IOException e) {
                ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.RED, "Cannot read the input!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
            }
        }
    }

    private boolean checkCredentials(Credentials credentials) {
        if (firstInit) {
            var admin = Admin.builder(new Employee.PersonalInfo("alex", "bell",
                    LocalDate.now(), "Bronte"), new Credentials("lauralex", "coccode"))
                    .addPermission(ReadHospitalInfoPermission.get()).addPermission(WriteHospitalInfoPermission.get()).build();
            Employee.addEmployee(admin);

            admin.addPermission(LogoutPermission.get());
            admin.addPermission(ExitPermission.get());
            firstInit = false;
        }

        var res = Employee.getAll().stream().filter(employee -> employee.getCredentials().equals(credentials)).findFirst();

        if (res.isPresent()) {
            SessionManager.setCurrentUser(res.get());
            return true;
        }
        return false;
    }
}
