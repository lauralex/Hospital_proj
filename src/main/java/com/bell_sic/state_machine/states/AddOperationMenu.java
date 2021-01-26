package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.Operation;
import com.bell_sic.entity.OperationType;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.entity.wards.Ward;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.StateOperations;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleDelay;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.stream.Collectors;

public class AddOperationMenu extends UIState {
    private Operation operationToAdd = new Operation("EXAMPLE", OperationType.SURGERY);
    private Ward selectedWard = Hospital.WardView.getAnyWard();

    public AddOperationMenu() {
        super(StateId.AddOperationMenu);
        stateOperations.addOperation("Add operation description: ",
                operationToAdd.getDescription(),
                this::addOperationDesc,
                WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Set operation type: ",
                operationToAdd.getOperationType().toString(),
                this::setType,
                WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Select a ward: ",
                selectedWard.toString(),
                this::setSelectedWard,
                WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Add a rehabilitation duration: ",
                operationToAdd.getPossibleRehabilitationDurations().toString(),
                this::addOperationDurations,
                WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Apply",
                this::apply,
                WriteHospitalInfoPermission.get());
        stateOperations.addOperation("Go back",
                () -> {
                    resetData();
                    UILoop.setTransition(Transition.GoToAdminMenu);
                },
                WriteHospitalInfoPermission.get());
    }

    private void addOperationDesc() {
        System.out.print("Enter a description: ");
        try {
            operationToAdd.setDescription(ConsoleLineReader.getBufferedReader().readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (NullPointerException | IllegalArgumentException e) {
            ConsoleColoredPrinter.println(e.getMessage());
            ConsoleDelay.addDelay(50);
        }
        updateOpStrings();
    }

    private void setType() {
        StateOperations typeOperations = new StateOperations();
        typeOperations.addOperation("EXAMINATION",
                () -> operationToAdd.setOperationType(OperationType.EXAMINATION),
                WriteHospitalInfoPermission.get());
        typeOperations.addOperation("SURGERY",
                () -> operationToAdd.setOperationType(OperationType.SURGERY),
                WriteHospitalInfoPermission.get());
        typeOperations.checkUserInputAndExecute();
        updateOpStrings();
    }

    private void setSelectedWard() {
        StateOperations wardSelectionOperations = new StateOperations();
        for (var ward :
                Hospital.WardView.getWards()) {
            wardSelectionOperations.addOperation(ward.toString(),
                    () -> selectedWard = ward,
                    WriteHospitalInfoPermission.get());
        }
        wardSelectionOperations.checkUserInputAndExecute();
        updateOpStrings();
    }

    private void addOperationDurations() {
        System.out.print("Please, add a new duration in the PnYnMnD format: ");
        try {
            operationToAdd.addPossibleDuration(Period.parse(ConsoleLineReader.getBufferedReader().readLine()).normalized());
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (DateTimeParseException e) {
            ConsoleColoredPrinter.println("No buoino, you must enter a valid period");
        }
        updateOpStrings();
    }

    private void apply() {
        try {
            selectedWard.addOperation(operationToAdd);
            ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, "Operation successfully added to " + selectedWard);
        } catch (IllegalStateException | IllegalArgumentException e) {
            ConsoleColoredPrinter.println(e.getMessage());
        }
    }

    private void resetData() {
        operationToAdd = new Operation("EXAMPLE", OperationType.SURGERY);
        selectedWard = Hospital.WardView.getAnyWard();
        updateOpStrings();

    }

    private void updateOpStrings() {
        stateOperations.modifyOperationString("add operation desc", operationToAdd.getDescription());
        stateOperations.modifyOperationString("operation ty", operationToAdd.getOperationType().toString());
        stateOperations.modifyOperationString("select a ward", selectedWard.toString());
        stateOperations.modifyOperationString("duration", operationToAdd.getPossibleRehabilitationDurations().stream().map(
                period -> period.getYears() + " years " + period.getMonths() + " months " + period.getDays() + " days"
        ).collect(Collectors.toList()).toString());
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }
}
