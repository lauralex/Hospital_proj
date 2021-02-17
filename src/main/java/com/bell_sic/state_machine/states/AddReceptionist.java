package com.bell_sic.state_machine.states;

import com.bell_sic.entity.employees.Receptionist;
import com.bell_sic.entity.permission.ManagePatientInfoPermission;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.utility.ConsoleColoredPrinter;
import javassist.bytecode.DuplicateMemberException;

public class AddReceptionist extends AddEmployee {
    public AddReceptionist() {
        super(StateId.AddReceptionistMenu);

        addEmployeeUI();
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }

    @Override
    protected void addToWardOperation() {
        // Apply operation
        stateOperations.addOperation("Apply operation: (add receptionist)",
                () -> {
                    try {
                        ward.addEmployeeToWard(Receptionist.builder(personalInfo, credentials)
                                .addPermission(ManagePatientInfoPermission.get()).build());
                        ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "Operation applied");
                        resetData();
                    } catch (NullPointerException | IllegalStateException | DuplicateMemberException e) {
                        ConsoleColoredPrinter.println(e.getMessage());
                    }
                }, WriteHospitalInfoPermission.get()
        );
    }
}
