package com.bell_sic.state_machine.states;

import com.bell_sic.entity.Doctor;
import com.bell_sic.entity.Employee;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.LogoutPermission;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.utility.ConsoleColoredPrinter;

public class AddDoctor extends AddEmployee {

    public AddDoctor() {
        super(StateId.AddDoctorMenu);

        addEmployeeUI();

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

    @Override
    public void executeUI() {
        checkUserInputAndExecute(getPermissibleOperations());
    }


}
