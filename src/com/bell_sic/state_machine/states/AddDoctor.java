package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Doctor;
import com.bell_sic.entity.Employee;
import com.bell_sic.entity.permission.Credentials;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.LogoutPermission;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleDelay;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddDoctor extends UIState {
    private Employee.PersonalInfo personalInfo = getDefaultPersonalInfo();
    private Credentials credentials = getDefaultCredentials();

    public AddDoctor() {
        super(StateId.AddDoctorMenu);

        // INDEX 0 OPERATION
        addOperation("Go To admin", () -> {
            resetData();
            UILoop.setTransition(Transition.GoToAdminMenu);
        }, ExitPermission.get());

        // INDEX 1 OPERATION
        addOperation("Modify personal info: " + personalInfo, this::modifyPersonalInfo, WriteHospitalInfoPermission.get());

        // INDEX 2 OPERATION
        addOperation("Modify credentials: " + credentials, this::modifyCredentials, WriteHospitalInfoPermission.get());

        // INDEX 3 OPERATION
        addOperation("Apply operation (add doctor)", () -> {
                    Employee.addEmployee(
                            Doctor.builder(personalInfo, credentials)
                                    .addPermission(ExitPermission.get())
                                    .addPermission(LogoutPermission.get()).build());
                    ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "Operation applied!");
                    resetData();
                },
                WriteHospitalInfoPermission.get());
    }

    private static Employee.PersonalInfo getDefaultPersonalInfo() {
        return new Employee.PersonalInfo("Giovanni",
                "Neve", LocalDate.now(), "Bronte");
    }

    private static Credentials getDefaultCredentials() {
        return new Credentials("bongo", "cresto");
    }

    @Override
    public void executeUI() {
        checkUserInputAndExecute(getPermissibleOperations());
    }

    private void modifyPersonalInfo() {
        System.out.print("Insert a name: ");

        try {
            personalInfo.setName(ConsoleLineReader.getBufferedReader().readLine());
        } catch (IOException e) {
            ConsoleColoredPrinter.println("Invalid input string for name!");
            e.printStackTrace();
            ConsoleDelay.addDelay();

        }
        System.out.print("Insert last name: ");
        try {
            personalInfo.setLastName(ConsoleLineReader.getBufferedReader().readLine());
        } catch (IOException e) {
            ConsoleColoredPrinter.println("Invalid input string for last name!");
            e.printStackTrace();
            ConsoleDelay.addDelay();

        }
        System.out.print("Insert date of birth: ");
        try {
            personalInfo.setDateOfBirth(LocalDate.parse(ConsoleLineReader.getBufferedReader().readLine()));
        } catch (IOException e) {
            ConsoleColoredPrinter.println("Invalid input string for local date!");
            e.printStackTrace();
            ConsoleDelay.addDelay();

        } catch (DateTimeParseException dateTimeParseException) {
            ConsoleColoredPrinter.println("Invalid date-time format. Use YYYY-MM-DD");
            dateTimeParseException.printStackTrace();
            ConsoleDelay.addDelay();
        }
        System.out.print("Insert city of birth: ");
        try {
            personalInfo.setCityOfBirth(ConsoleLineReader.getBufferedReader().readLine());
        } catch (IOException e) {
            ConsoleColoredPrinter.println("Invalid input string for city of birth!");
            e.printStackTrace();
            ConsoleDelay.addDelay();
        }
        updateOperationStrings();

    }

    private void modifyCredentials() {
        System.out.print("Insert username: ");

        try {
            credentials.setUserName(ConsoleLineReader.getBufferedReader().readLine());
        } catch (IOException e) {
            ConsoleColoredPrinter.println("Invalid input string for username!");
            e.printStackTrace();
            ConsoleDelay.addDelay();
        }

        System.out.print("Insert password: ");

        try {
            credentials.setPassword(ConsoleLineReader.getBufferedReader().readLine());
        } catch (IOException e) {
            ConsoleColoredPrinter.println("Invalid input string for password!");
            e.printStackTrace();
            ConsoleDelay.addDelay();
        }
        updateOperationStrings();

    }

    private void resetData() {
        personalInfo = getDefaultPersonalInfo();
        credentials = getDefaultCredentials();
        updateOperationStrings();
    }

    private void updateOperationStrings() {
        modifyOperationString(1, personalInfo.toString());
        modifyOperationString(2, credentials.toString());
    }


}
