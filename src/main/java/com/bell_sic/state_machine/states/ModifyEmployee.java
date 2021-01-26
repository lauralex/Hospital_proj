package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.employees.Employee;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;

public class ModifyEmployee extends UIState {
    private Employee selectedEmployee;

    public ModifyEmployee() {
        super(StateId.ModifyEmployeeMenu);
        stateOperations.addOperation("Search an employee ", "No employee", this::employeeSearchRoutine, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Reassign selected employee", this::reassignSelectedEmployeeRoutine, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Go back", () -> {
            UILoop.setTransition(Transition.GoToAdminMenu);
            resetData();
        }, WriteHospitalInfoPermission.get());
    }

    private void employeeSearchRoutine() {
        String enteredName = "";
        System.out.print("Enter the employee name: ");
        try {
            enteredName = ConsoleLineReader.getBufferedReader().readLine();
        } catch (IOException e) {
            ConsoleColoredPrinter.println("There is an IO read error!");
            e.printStackTrace();
        }
        if (enteredName.isBlank()) throw new IllegalArgumentException("Name cannot be blank!");

        var res = Hospital.EmployeeView.searchEmployeeByName(enteredName);
        if (res.isEmpty()) {
            ConsoleColoredPrinter.println("No employee found!");
            return;
        }
        selectedEmployee = (Employee) res.toArray()[0];
        stateOperations.modifyOperationString("search", "TYPE: " + selectedEmployee.getClass().getSimpleName() +
                 " WARD: " + Hospital.EmployeeView.getEmployeeWardQuery(selectedEmployee).orElseThrow() + " " + selectedEmployee.getPersonalInfo());
    }

    private void reassignSelectedEmployeeRoutine() {

    }

    private void resetData() {
        selectedEmployee = null;
        stateOperations.modifyOperationString("search", "No employee");
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }
}
