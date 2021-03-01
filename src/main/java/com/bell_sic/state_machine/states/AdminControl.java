package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.PatientView;
import com.bell_sic.entity.employees.Doctor;
import com.bell_sic.entity.employees.Employee;
import com.bell_sic.entity.employees.Receptionist;
import com.bell_sic.entity.permission.*;
import com.bell_sic.state_machine.*;
import com.bell_sic.utility.ConsoleColoredPrinter;

public class AdminControl extends UIState {
    private StateOperations ops;

    public AdminControl() {
        super(StateId.AdminMenu);

        stateOperations.addOperation("logout", this::logout, LogoutPermission.get());
        //stateOperations.addOperation("COnfirm app", () -> ((Doctor)SessionManager.getCurrentUser()).confirmAppointments(), DoctorPermission.get() );
        stateOperations.addOperation("Add a doctor", () -> Hospital.get().registerNewDoctor(), WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Register an appointment", () -> Hospital.get().registerAppointment(), WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Add an operation", () -> Hospital.get().addOperation(), WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Replace a doctor", () -> Hospital.get().replaceDoctor(), WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Add a receptionist", () -> UILoop.setTransition(Transition.GoToAddReceptionistMenu), AdminPermission.get());
        stateOperations.addOperation("Add patient", () -> UILoop.setTransition(Transition.GoToAddPatientMenu), WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Show all employees", AdminControl::showAllEmployees, ReadHospitalInfoPermission.get());
        stateOperations.addOperation("Show all patients", AdminControl::showAllPatients, ReadHospitalInfoPermission.get());
        stateOperations.addOperation("Show receptionists", () -> showEmployeesByType(Receptionist.class), ReadHospitalInfoPermission.get());
        stateOperations.addOperation("Show doctors", () -> showEmployeesByType(Doctor.class), ReadHospitalInfoPermission.get());
        stateOperations.addOperation("Modify employee", () -> UILoop.setTransition(Transition.GoToModifyEmployeeMenu), AdminPermission.get());
        stateOperations.addOperation("Return to main menu", () -> UILoop.setTransition(Transition.GoToMainMenu), ExitPermission.get());
        stateOperations.addOperation("Exit application", UILoop::breakLoop, ExitPermission.get());
    }

    private void logout() {
        if (ops != null) {
            ops.getOperations().forEach(pairPermissionContainerPair -> stateOperations.getOperations().remove(pairPermissionContainerPair));
            ops = null;
        }
        UILoop.setTransition(Transition.LogOut);
        SessionManager.setCurrentUser(null);
    }

    private static void showAllEmployees() {
        var res = Hospital.EmployeeView.getAllEmployees();
        res.forEach(employee -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "TYPE: " + employee.getClass().getSimpleName() + "; WARD: " + Hospital.EmployeeView.getEmployeeWardQuery(employee).orElseThrow() + "; " + employee.getPersonalInfo().toString()));
    }

    private static void showEmployeesByType(Class<? extends Employee> employeeType) {
        var res = Hospital.EmployeeView.searchEmployeeByType(employeeType);
        res.forEach(employee -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "TYPE: " + employee.getClass().getSimpleName() + "; WARD: " + Hospital.EmployeeView.getEmployeeWardQuery(employee).orElseThrow() + "; " + employee.getPersonalInfo().toString()));
    }

    private static void showAllPatients() {
        var res = PatientView.getAllPatients();
        res.forEach(patient -> ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "TYPE: " + patient.getClass().getSimpleName() + "; WARD: " + PatientView.getPatientWardQuery(patient).orElseThrow() + "; " + patient.getPersonalInfo()));
    }

    @Override
    public void doBeforeEntering(Object options) {
        if (options instanceof Employee) {
            ops = ((Employee) options).getSpecificOperations();
            if (ops == null) return;
            stateOperations.getOperations().addAll(1, ops.getOperations());
        }
    }


    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }

}
