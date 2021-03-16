package com.bell_sic.state_machine.states;

import com.bell_sic.entity.employees.EmployeeBuilder;
import com.bell_sic.entity.employees.Receptionist;
import com.bell_sic.entity.permission.ReadHospitalInfoPermission;
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
    protected EmployeeBuilder setEmployeeBuilder() {
        return Receptionist.builder(personalInfo, credentials)
                .addPermission(WriteHospitalInfoPermission.get());
    }

    @Override
    protected String setEmployeeType() {
        return "Receptionist";
    }
}
