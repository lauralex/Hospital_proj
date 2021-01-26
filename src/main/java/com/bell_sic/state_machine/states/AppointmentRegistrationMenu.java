package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Appointment;
import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.Operation;
import com.bell_sic.entity.Patient;
import com.bell_sic.entity.employees.Doctor;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.StateOperations;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleDelay;
import com.bell_sic.utility.ConsoleLineReader;
import javassist.bytecode.DuplicateMemberException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

public class AppointmentRegistrationMenu extends UIState {
    private Operation selectedOperation;
    private Doctor selectedDoctor;
    private Patient selectedPatient;
    private LocalDate dateOfApp;

    public AppointmentRegistrationMenu() {
        super(StateId.AppointmentRegistrationMenu);
        stateOperations.addOperation("Select an operation: ",
                this::selectOperation,
                WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Select a doctor: ", this::selectDoctor, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Insert a patient: ", this::insertPatient, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Insert date: ", this::insertDateOfAppointment, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Apply", this::apply, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Go back", () -> UILoop.setTransition(Transition.GoToAdminMenu), WriteHospitalInfoPermission.get());
    }

    @Override
    public void doBeforeEntering(Object options) {
        if (options != null) {
            var oldApp = (AppointmentRegistrationMenu)options;
            for (var item :
                    AppointmentRegistrationMenu.class.getDeclaredFields()) {
                try {
                    item.set(this, item.get(oldApp));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    return;
                }
            }
            updateOpStrings();
            return;
        }
        resetData();
    }

    private void resetData() {
        try {
            selectedOperation = Hospital.OperationView.getAnyOperation();
            selectedDoctor = Hospital.EmployeeView.getAnyEmployeeOfType(Doctor.class);
        } catch (NoSuchElementException e) {
            ConsoleColoredPrinter.println(e.getMessage());
            ConsoleDelay.addDelay(1000);
            UILoop.setTransition(Transition.GoToAdminMenu);
            return;
        }
        selectedPatient = new Patient("Pincopallino", "Ciccio", LocalDate.now(), "BRONTE");
        dateOfApp = null;

        updateOpStrings();
    }

    private void updateOpStrings() {
        stateOperations.modifyOperationString("select an op", selectedOperation + " Ward: " + selectedOperation.getWard());
        stateOperations.modifyOperationString("select a doc", selectedDoctor + " Ward: " + selectedDoctor.getWard());
        stateOperations.modifyOperationString("insert a pat", selectedPatient.toString());
        stateOperations.modifyOperationString("insert date", dateOfApp != null ? dateOfApp.toString() : "");
    }

    private void selectOperation() {
        StateOperations operationSelectionOperations = new StateOperations();
        for (var item :
                Hospital.OperationView.getAllOperations()) {
            operationSelectionOperations.addOperation(item.toString() + " Ward: " + item.getWard(),
                    () -> selectedOperation = item,
                    WriteHospitalInfoPermission.get());
        }

        operationSelectionOperations.checkUserInputAndExecute();
        updateOpStrings();
    }

    private void selectDoctor() {
        StateOperations doctorSelectOperations = new StateOperations();
        for (var item :
                Hospital.EmployeeView.searchEmployeeByType(Doctor.class)) {
            doctorSelectOperations.addOperation(item.toString() + " Ward: " + item.getWard(),
                    () -> selectedDoctor = item,
                    WriteHospitalInfoPermission.get());
        }
        doctorSelectOperations.checkUserInputAndExecute();
        updateOpStrings();
    }

    private void insertPatient() {
        UILoop.setTransition(Transition.GoToInsertPatientMenu, this);
    }

    private void insertDateOfAppointment() {
        System.out.print("Insert a date (PnYnMnD): ");
        try {
            dateOfApp = LocalDate.parse(ConsoleLineReader.getBufferedReader().readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (DateTimeParseException e) {
            ConsoleColoredPrinter.println(e.getMessage());
            ConsoleDelay.addDelay(1000);
        }
        updateOpStrings();
    }

    private void apply() {
        try {
            selectedDoctor.addAppointment(new Appointment(dateOfApp, selectedOperation, selectedPatient));
        } catch (DuplicateMemberException | NullPointerException e) {
            ConsoleColoredPrinter.println(e.getMessage());
            return;
        }

        ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "Appointment registration completed!");
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }

    public void setSelectedPatient(Patient selectedPatient) {
        this.selectedPatient = selectedPatient;
    }

    public Patient getSelectedPatient() {
        return selectedPatient;
    }
}
