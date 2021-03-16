package com.bell_sic.state_machine.states;

import com.bell_sic.entity.employees.Doctor;
import com.bell_sic.entity.employees.EmployeeBuilder;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleDelay;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;
import java.util.Locale;

public class AddDoctor extends AddEmployee {
    private String qualification;

    public AddDoctor() {
        super(StateId.AddDoctorMenu);

        addEmployeeUI();
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }

    @Override
    protected void additionalOperations() {
        stateOperations.addOperation("Modify qualification: ", qualification == null ? "" : qualification,
                () -> {
                    System.out.print("Enter the qualification: ");
                    try {
                        this.qualification = ConsoleLineReader.getBufferedReader().readLine().toLowerCase(Locale.ROOT).trim();
                        stateOperations.modifyOperationString("modify qu", qualification);
                    } catch (IOException e) {
                        ConsoleColoredPrinter.println(e.getMessage());
                        ConsoleDelay.addDelay();
                    }
                }, WriteHospitalInfoPermission.get());
    }

    @Override
    protected EmployeeBuilder setEmployeeBuilder() {
        return Doctor.builder(personalInfo, credentials)
                //.addPermission(WriteHospitalInfoPermission.get())
                .setQualification(qualification);
    }

    @Override
    protected String setEmployeeType() {
        return "Doctor";
    }
}
