package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.PersonalInfo;
import com.bell_sic.entity.permission.Credentials;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.entity.wards.Ward;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.StateOperations;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleDelay;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public abstract class AddEmployee extends UIState {
    protected PersonalInfo personalInfo = AddEmployee.getDefaultPersonalInfo();
    protected Credentials credentials = AddEmployee.getDefaultCredentials();
    protected Ward ward = AddEmployee.getDefaultWard();

    public AddEmployee(StateId stateId) {
        super(stateId);
    }

    private static PersonalInfo getDefaultPersonalInfo() {
        return new PersonalInfo("Giovanni",
                "Neve", LocalDate.now(), "Bronte");
    }

    private static Credentials getDefaultCredentials() {
        return new Credentials("bongo", "cresto");
    }

    private static Ward getDefaultWard() {
        return Hospital.WardView.getAnyWard();
    }

    protected void modifyPersonalInfo() {
        do {
            System.out.print("Insert a name: ");

            try {
                personalInfo.setName(ConsoleLineReader.getBufferedReader().readLine());
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for name!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                break;

            } catch (IllegalArgumentException e) {
                ConsoleColoredPrinter.println(e.getMessage());
                ConsoleDelay.addDelay();
                break;
            }
            System.out.print("Insert last name: ");
            try {
                personalInfo.setLastName(ConsoleLineReader.getBufferedReader().readLine());
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for last name!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;

            } catch (IllegalArgumentException e) {
                ConsoleColoredPrinter.println(e.getMessage());
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;
            }
            System.out.print("Insert date of birth: ");
            try {
                personalInfo.setDateOfBirth(LocalDate.parse(ConsoleLineReader.getBufferedReader().readLine()));
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for local date!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;

            } catch (DateTimeParseException dateTimeParseException) {
                ConsoleColoredPrinter.println("Invalid date-time format. Use YYYY-MM-DD");
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;
            }
            System.out.print("Insert city of birth: ");
            try {
                personalInfo.setCityOfBirth(ConsoleLineReader.getBufferedReader().readLine());
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for city of birth!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;
            } catch (IllegalArgumentException e) {
                ConsoleColoredPrinter.println(e.getMessage());
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;
            }
        } while (false);
        updateOperationStrings();

    }

    protected void modifyCredentials() {
        do {
            System.out.print("Insert username: ");

            try {
                String username = ConsoleLineReader.getBufferedReader().readLine();
                if (Hospital.EmployeeView.getAllEmployees().stream().anyMatch(employee -> employee.getCredentials().getUserName().equals(username))) {
                    throw new IllegalArgumentException("Username already exists!");
                }
                credentials.setUserName(username);
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for username!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetCredentials();
                break;
            } catch (IllegalArgumentException e) {
                ConsoleColoredPrinter.println(e.getMessage());
                ConsoleDelay.addDelay();
                resetCredentials();
                break;
            }

            System.out.print("Insert password: ");

            try {
                credentials.setPassword(ConsoleLineReader.getBufferedReader().readLine());
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for password!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetCredentials();
                break;
            } catch (IllegalArgumentException e) {
                ConsoleColoredPrinter.println(e.getMessage());
                ConsoleDelay.addDelay();
                resetCredentials();
                break;
            }
        } while (false);
        updateOperationStrings();

    }

    protected void modifyWard() {
        StateOperations operations = new StateOperations();
        for (var ward :
                Hospital.WardView.getWards()) {
            operations.addOperation(ward.toString(), () -> {
                this.ward = ward;
                updateOperationStrings();
            }, WriteHospitalInfoPermission.get());
        }
        operations.addOperation("Go back", () -> {}, WriteHospitalInfoPermission.get());
        operations.checkUserInputAndExecute();
    }

    protected void resetData() {
        personalInfo = AddEmployee.getDefaultPersonalInfo();
        credentials = AddEmployee.getDefaultCredentials();
        ward = AddEmployee.getDefaultWard();
        updateOperationStrings();
    }

    protected void resetPersonalInfo() {
        personalInfo = AddEmployee.getDefaultPersonalInfo();
        updateOperationStrings();
    }

    protected void resetCredentials() {
        credentials = AddEmployee.getDefaultCredentials();
        updateOperationStrings();
    }

    protected void resetWard() {
        ward = AddEmployee.getDefaultWard();
    }

    protected void updateOperationStrings() {
        stateOperations.modifyOperationString("modify personal", personalInfo.toString());
        stateOperations.modifyOperationString("modify cred", credentials.toString());
        stateOperations.modifyOperationString("modify assigned", ward.toString());
    }

    protected abstract void addToWardOperation();

    protected void addEmployeeUI() {
        // INDEX 0 OPERATION
        stateOperations.addOperation("Go To admin", () -> {
            resetData();
            UILoop.setTransition(Transition.GoToAdminMenu);
        }, ExitPermission.get());

        // INDEX 1 OPERATION (CHECK THE updateOperationStrings() METHOD)
        stateOperations.addOperation("Modify personal info: ", personalInfo.toString(), this::modifyPersonalInfo, WriteHospitalInfoPermission.get());

        // INDEX 2 OPERATION (CHECK THE updateOperationStrings() METHOD)
        stateOperations.addOperation("Modify credentials: ", credentials.toString(), this::modifyCredentials, WriteHospitalInfoPermission.get());

        // INDEX 3 OPERATION
        stateOperations.addOperation("Modify assigned ward: ", ward.toString(), this::modifyWard, WriteHospitalInfoPermission.get());

        addToWardOperation();

    }
}
