package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.PersonalInfo;
import com.bell_sic.entity.employees.Admin;
import com.bell_sic.entity.permission.*;
import com.bell_sic.entity.wards.EmergencyWard;
import com.bell_sic.state_machine.SessionManager;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleDelay;
import com.bell_sic.utility.ConsoleLineReader;
import javassist.bytecode.DuplicateMemberException;

import java.io.IOException;
import java.time.LocalDate;

public class Login extends UIState {
    private boolean firstInit = true;

    public Login() {
        super(StateId.Login);
    }

    @Override
    public void executeUI() {

        System.out.print("Enter username: ");
        try {
            String username = ConsoleLineReader.getBufferedReader().readLine();
            System.out.print("Enter password: ");
            String password = ConsoleLineReader.getBufferedReader().readLine();

            if (checkCredentials(new Credentials(username, password))) {
                UILoop.setTransition(Transition.GoToMainMenu, SessionManager.getCurrentUser());
            } else {
                ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.RED, "Wrong credentials");
                UILoop.setTransition(Transition.GoToPreLoginMenu);
            }
        } catch (IOException e) {
            ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.RED, "Cannot read the input!");
            e.printStackTrace();
            ConsoleDelay.addDelay();
        }

    }

    /**
     * This method checks if the passed {@link Credentials} belong to any employee in the EmployeeView.
     *
     * @param credentials The {@linkplain Credentials} to be checked.
     * @return {@code True} if the {@code credentials} are valid. {@code False} otherwise.
     */
    private boolean checkCredentials(Credentials credentials) {
        if (firstInit) {
            var admin = Admin.builder(new PersonalInfo("alex", "bell",
                    LocalDate.now(), "Bronte"), new Credentials("lauralex", "coccode"))
                    .addPermission(ReadHospitalInfoPermission.get()).addPermission(WriteHospitalInfoPermission.get()).build();
            Hospital.WardView.getWardByType(EmergencyWard.class).ifPresent(ward -> {
                try {
                    ward.addEmployeeToWard(admin);
                } catch (DuplicateMemberException e) {
                    ConsoleColoredPrinter.println(e.getMessage());
                }
            });

            admin.addPermission(LogoutPermission.get());
            admin.addPermission(ExitPermission.get());
            firstInit = false;
        }

        var res = Hospital.EmployeeView.getAllEmployees().stream().filter(employee -> employee.getCredentials().equals(credentials)).findFirst();

        if (res.isPresent()) {
            SessionManager.setCurrentUser(res.get());
            return true;
        }
        return false;
    }
}
