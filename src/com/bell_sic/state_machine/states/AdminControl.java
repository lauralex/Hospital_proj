package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Doctor;
import com.bell_sic.entity.Employee;
import com.bell_sic.entity.Receptionist;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.LogoutPermission;
import com.bell_sic.entity.permission.ReadHospitalInfoPermission;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;

public class AdminControl extends UIState {
    public AdminControl() {
        super(StateId.AdminMenu);

        addOperation("logout", () -> UILoop.setTransition(Transition.LogOut), LogoutPermission.get());
        addOperation("Add a doctor", () -> UILoop.setTransition(Transition.GoToAddDoctorMenu), WriteHospitalInfoPermission.get());
        addOperation("Add a receptionist", () -> UILoop.setTransition(Transition.GoToAddReceptionistMenu), WriteHospitalInfoPermission.get());
        addOperation("Show all employees", AdminControl::showAllEmployees, ReadHospitalInfoPermission.get());
        addOperation("Show receptionists", () -> showEmployeesByType(Receptionist.class), ReadHospitalInfoPermission.get());
        addOperation("Show doctors", () -> showEmployeesByType(Doctor.class), ReadHospitalInfoPermission.get());
        addOperation("Return to main menu", () -> UILoop.setTransition(Transition.GoToMainMenu), ExitPermission.get());
        addOperation("Exit application", UILoop::breakLoop, ExitPermission.get());
    }

    @Override
    public void executeUI() {
        var permissibleOperations = getPermissibleOperations();

        checkUserInputAndExecute(permissibleOperations);
    }

    private static void showAllEmployees() {
        var res = Employee.getAll();
        res.forEach(employee -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "TYPE: " + employee.getClass().getSimpleName() + "; " + employee.getPersonalInfo().toString()));
    }

    private static void showEmployeesByType(Class<? extends Employee> employeeType) {
        var res = Employee.searchEmployeeByType(employeeType);
        res.forEach(employee -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "TYPE: " + employee.getClass().getSimpleName() + "; " + employee.getPersonalInfo().toString()));
    }

}
