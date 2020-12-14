package com.bell_sic.state_machine.states;

import com.bell_sic.entity.Doctor;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.LogoutPermission;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.utility.ConsoleColoredPrinter;

public class AddDoctor extends AddEmployee {

    public AddDoctor() {
        super(StateId.AddDoctorMenu);

        addEmployeeUI();
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }


    @Override
    protected void addToWardOperation() {
        // APPLY OPERATION
        stateOperations.addOperation("Apply operation (add doctor)", () -> {
            ward.addEmployeeToWard(
                    Doctor.builder(personalInfo, credentials)
                            .addPermission(ExitPermission.get())
                            .addPermission(LogoutPermission.get()).build());
            ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "Operation applied!");
            resetData();
        }, WriteHospitalInfoPermission.get());
    }
}
