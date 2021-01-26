package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Patient;
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

public class InsertPatient extends UIState {
    private AppointmentRegistrationMenu appState;

    public InsertPatient() {
        super(StateId.InsertPatientMenu);
        stateOperations.addOperation("Modify personal info: ", this::modifyPersonalInfo, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Apply", this::apply, WriteHospitalInfoPermission.get());
    }

    @Override
    public void doBeforeEntering(Object options) {
        if (options == null) {
            ConsoleColoredPrinter.println("Nope, you must enter the old appointment state");
            UILoop.setTransition(Transition.GoToAppointmentRegistrationMenu);
            return;
        }
        if (!(options instanceof AppointmentRegistrationMenu)) {
            ConsoleColoredPrinter.println("You must enter a valid appointment state to the options!");
            UILoop.setTransition(Transition.GoToAppointmentRegistrationMenu);
            return;
        }

        appState = (AppointmentRegistrationMenu) options;
        updateOpStrings();
    }

    private void updateOpStrings() {
        stateOperations.modifyOperationString("modify per", appState.getSelectedPatient().toString());
    }

    private void modifyPersonalInfo() {
        do {
            System.out.print("Insert a name: ");


            try {
                appState.getSelectedPatient().getPersonalInfo().setName(ConsoleLineReader.getBufferedReader().readLine());
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for name!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                break;

            } catch (IllegalArgumentException e) {
                ConsoleColoredPrinter.println(e.getMessage());
                e.printStackTrace();
                ConsoleDelay.addDelay();
                break;
            }
            System.out.print("Insert last name: ");
            try {
                appState.getSelectedPatient().getPersonalInfo().setLastName(ConsoleLineReader.getBufferedReader().readLine());
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for last name!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;

            } catch (IllegalArgumentException e) {
                ConsoleColoredPrinter.println(e.getMessage());
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;
            }
            System.out.print("Insert date of birth: ");
            try {
                appState.getSelectedPatient().getPersonalInfo().setDateOfBirth(LocalDate.parse(ConsoleLineReader.getBufferedReader().readLine()));
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for local date!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;

            } catch (DateTimeParseException dateTimeParseException) {
                ConsoleColoredPrinter.println("Invalid date-time format. Use YYYY-MM-DD");
                dateTimeParseException.printStackTrace();
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;
            }
            System.out.print("Insert city of birth: ");
            try {
                appState.getSelectedPatient().getPersonalInfo().setCityOfBirth(ConsoleLineReader.getBufferedReader().readLine());
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Invalid input string for city of birth!");
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;
            } catch (IllegalArgumentException e) {
                ConsoleColoredPrinter.println(e.getMessage());
                e.printStackTrace();
                ConsoleDelay.addDelay();
                resetPersonalInfo();
                break;
            }
        } while (false);
        updateOpStrings();
    }

    private void apply() {
        UILoop.setTransition(Transition.GoToAppointmentRegistrationMenu, appState);
    }

    private void resetPersonalInfo() {
        appState.setSelectedPatient(new Patient("Nope", "Nope", LocalDate.now(), "Nope"));
        updateOpStrings();
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }
}
