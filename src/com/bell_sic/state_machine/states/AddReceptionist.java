package com.bell_sic.state_machine.states;

import com.bell_sic.entity.Employee;
import com.bell_sic.entity.Receptionist;
import com.bell_sic.entity.permission.ManagePatientInfoPermission;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.utility.ConsoleColoredPrinter;

public class AddReceptionist extends AddEmployee{
    public AddReceptionist() {
        super(StateId.AddReceptionistMenu);

        addEmployeeUI();

        // Apply operation
        addOperation("Apply operation: (add receptionist",
                () -> {
                    Employee.addEmployee(Receptionist.builder(personalInfo, credentials)
                            .addPermission(ManagePatientInfoPermission.get()).build());
                    ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "Operation applied");
                    resetData();
                }, WriteHospitalInfoPermission.get());

    }

    @Override
    public void executeUI() {
        checkUserInputAndExecute(getPermissibleOperations());
    }
}
