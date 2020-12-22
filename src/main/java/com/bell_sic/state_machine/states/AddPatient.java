package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Patient;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.utility.ConsoleColoredPrinter;

public class AddPatient extends AddEmployee {


    public AddPatient() {
        super(StateId.AddPatientMenu);

        addEmployeeUI();
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }

    @Override
    protected void modifyCredentials() {

    }

    @Override
    protected void addEmployeeUI() {
        // INDEX 0 OPERATION
        stateOperations.addOperation("Go To admin", () -> {
            resetData();
            UILoop.setTransition(Transition.GoToAdminMenu);
        }, ExitPermission.get());

        // INDEX 1 OPERATION (CHECK THE updateOperationStrings() METHOD)
        stateOperations.addOperation("Modify personal info: ", personalInfo.toString(), this::modifyPersonalInfo, WriteHospitalInfoPermission.get());

        // INDEX 2 OPERATION
        stateOperations.addOperation("Modify assigned ward: ", ward.toString(), this::modifyWard, WriteHospitalInfoPermission.get());

        addToWardOperation();
    }

    @Override
    protected void addToWardOperation() {
        // APPLY OPERATION
        stateOperations.addOperation("Apply operation (add patient)", () -> {
            ward.addPatientToWard(
                    new Patient(personalInfo));
            ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "Operation applied!");
            resetData();
        }, WriteHospitalInfoPermission.get());
    }

    @Override
    protected void updateOperationStrings() {
        stateOperations.modifyOperationString("modify perso", personalInfo.toString());
        stateOperations.modifyOperationString("modify assi", ward.toString());
    }
}
