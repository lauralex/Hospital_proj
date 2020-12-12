package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Doctor;
import com.bell_sic.entity.Employee;
import com.bell_sic.entity.Receptionist;
import com.bell_sic.entity.permission.*;
import com.bell_sic.state_machine.*;
import com.bell_sic.utility.ConsoleColoredPrinter;

import java.util.ArrayList;

public class AdminControl extends UIState {
    public AdminControl() {
        super(StateId.AdminMenu);

        stateOperations.addOperation("logout", () -> UILoop.setTransition(Transition.LogOut), LogoutPermission.get());
        stateOperations.addOperation("Add a doctor", () -> UILoop.setTransition(Transition.GoToAddDoctorMenu), WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Add a receptionist", () -> UILoop.setTransition(Transition.GoToAddReceptionistMenu), WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Show all employees", AdminControl::showAllEmployees, ReadHospitalInfoPermission.get());
        stateOperations.addOperation("Show receptionists", () -> showEmployeesByType(Receptionist.class), ReadHospitalInfoPermission.get());
        stateOperations.addOperation("Show doctors", () -> showEmployeesByType(Doctor.class), ReadHospitalInfoPermission.get());
        stateOperations.addOperation("Return to main menu", () -> UILoop.setTransition(Transition.GoToMainMenu), ExitPermission.get());
        stateOperations.addOperation("Exit application", UILoop::breakLoop, ExitPermission.get());
    }

    @Override
    public void executeUI() {
        ArrayList<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<StateOperations.Operations.StringTuple, Runnable>, PermissionContainer>> permissibleOperations = stateOperations.getPermissibleOperations();

        stateOperations.checkUserInputAndExecute(permissibleOperations);
    }

    private static void showAllEmployees() {
        var res = Employee.getAllEmployees();
        res.forEach(employee -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "TYPE: " + employee.getClass().getSimpleName() + "; " + employee.getPersonalInfo().toString()));
    }

    private static void showEmployeesByType(Class<? extends Employee> employeeType) {
        var res = Employee.searchEmployeeByType(employeeType);
        res.forEach(employee -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "TYPE: " + employee.getClass().getSimpleName() + "; " + employee.getPersonalInfo().toString()));
    }

}
