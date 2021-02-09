package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.employees.Doctor;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.StateOperations;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleDelay;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;

public class DoctorMigrationMenu extends UIState {
    private Doctor firstDoctor;
    private Doctor secondDoctor;

    public DoctorMigrationMenu() {
        super(StateId.ReplaceDoctorMenu);
        stateOperations.addOperation("Search first doctor by name: ", this::firstDoctorInfo, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Search second doctor by name: ", this::secondDoctorInfo, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Apply", this::apply, WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Back", () -> UILoop.setTransition(Transition.GoToAdminMenu), WriteHospitalInfoPermission.get());
    }

    @Override
    public void doBeforeEntering(Object options) {
        firstDoctor = null;
        secondDoctor = null;
        stateOperations.modifyOperationString("first doc", "");
        stateOperations.modifyOperationString("second doc", "");
    }

    private void firstDoctorInfo() {
        System.out.print("Enter the first doctor's name: ");
        String name;
        try {
            name = ConsoleLineReader.getBufferedReader().readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        StateOperations foundDoctors = new StateOperations();
        Hospital.EmployeeView.getAllEmployeesOfType(Doctor.class).stream().filter(doctor -> (doctor.getPersonalInfo().getName() +
                " " + doctor.getPersonalInfo().getLastName()).toLowerCase().contains(name.toLowerCase())).forEach(doctor ->
                foundDoctors.addOperation(doctor.toString(), () -> {
                    firstDoctor = doctor;
                    stateOperations.modifyOperationString("first doctor", doctor.toString());
                }, WriteHospitalInfoPermission.get()));
        foundDoctors.addOperation("Cancel", () -> {}, WriteHospitalInfoPermission.get());
        foundDoctors.checkUserInputAndExecute();
    }

    private void secondDoctorInfo() {
        System.out.print("Enter the second doctor's name: ");
        String lastName;
        try {
            lastName = ConsoleLineReader.getBufferedReader().readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        StateOperations foundDoctors = new StateOperations();
        Hospital.EmployeeView.getAllEmployeesOfType(Doctor.class).stream().filter(doctor -> (doctor.getPersonalInfo().getName() +
                " " + doctor.getPersonalInfo().getLastName()).toLowerCase().contains(lastName.toLowerCase())).forEach(doctor ->
                foundDoctors.addOperation(doctor.toString(), () -> {
                    secondDoctor = doctor;
                    stateOperations.modifyOperationString("second doctor", doctor.toString());
                }, WriteHospitalInfoPermission.get()));
        foundDoctors.addOperation("Cancel", () -> {}, WriteHospitalInfoPermission.get());
        foundDoctors.checkUserInputAndExecute();
    }

    private void apply() {
        try {
            if (firstDoctor == secondDoctor) {
                throw new IllegalStateException("You cannot select two equal doctors!");
            }
            secondDoctor.setAppointments(firstDoctor.getAppointments());
            firstDoctor.clearAppointments();
            secondDoctor.setRehabilitations(firstDoctor.getRehabilitations());
            firstDoctor.clearRehabilitations();
        } catch (NullPointerException | IllegalStateException e) {
            ConsoleColoredPrinter.println(e.getMessage());
            ConsoleDelay.addDelay(1000);
        }
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }
}
