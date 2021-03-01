package com.bell_sic.state_machine.states;

import com.bell_sic.entity.employees.Doctor;
import com.bell_sic.entity.permission.*;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.utility.ConsoleColoredPrinter;
import javassist.bytecode.DuplicateMemberException;

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
            try {
                ward.addEmployeeToWard(
                        Doctor.builder(personalInfo, credentials)
                                //.addPermission(WriteHospitalInfoPermission.get())
                                .addPermission(ReadHospitalInfoPermission.get()).build());
                ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "Operation applied!");
                resetData();
            } catch (NullPointerException | IllegalStateException | DuplicateMemberException e) {
                ConsoleColoredPrinter.println(e.getMessage());
            }
        }, WriteHospitalInfoPermission.get());
    }
}
