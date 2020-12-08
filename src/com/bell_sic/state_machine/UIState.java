package com.bell_sic.state_machine;

import com.bell_sic.entity.permission.PermissionContainer;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UIState {
    private final Map<Transition, StateId> map = new HashMap<>();
    private final StateId currentStateId;
    private final Operations operations = new Operations();

    public UIState(StateId stateId) {
        currentStateId = stateId;
    }

    protected static void checkUserInputAndExecute(ArrayList<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<String, Runnable>, PermissionContainer>> permissibleOperations) {
        var opLength = permissibleOperations.size();

        while (true) {
            for (int i = 0; i < opLength; i++) {
                System.out.println(i + ") " + permissibleOperations.get(i).first().first());
            }
            try {
                var option = ConsoleLineReader.getBufferedReader().readLine();

                try {
                    int opNumber = Integer.parseInt(option);

                    if (!(opNumber < opLength)) continue;
                    permissibleOperations.get(opNumber).first().second().run();
                    break;

                } catch (NumberFormatException e) {
                    ConsoleColoredPrinter.println("Cannot parse the given input! Try again!");
                    e.printStackTrace();
                }
            } catch (IOException e) {
                ConsoleColoredPrinter.println("Cannot read user input!");
                e.printStackTrace();
            }
        }
    }

    protected void addOperation(CharSequence operationString, Runnable operationAction, PermissionContainer permission) {
        operations.getOperations().add(new ConsoleOptionWriter.Pair<>(new ConsoleOptionWriter.Pair<>(operationString.toString(),
                operationAction), permission));
    }

    protected void modifyOperationString(int index, CharSequence charSequence) {
        try {
            operations.getOperations().get(index).first().setFirst(charSequence.toString());
        } catch (IndexOutOfBoundsException e) {
            ConsoleColoredPrinter.println("Invalid operation index!");
            e.printStackTrace();
        }
    }

    protected void clearOperations() {
        operations.getOperations().clear();
    }

    protected List<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<String, Runnable>, PermissionContainer>> getOperations() {
        return operations.getOperations();
    }

    protected ArrayList<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<String, Runnable>, PermissionContainer>> getPermissibleOperations() {
        var permissibleOperations = new ArrayList<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<String, Runnable>, PermissionContainer>>();

        for (var operation :
                operations.getOperations()) {
            if (SessionManager.getCurrentUser().checkPermission(operation.second())) {
                permissibleOperations.add(operation);
            }
        }
        return permissibleOperations;
    }

    public StateId getCurrentStateId() {
        return currentStateId;
    }

    public void addTransition(Transition transition, StateId stateId) {
        if (transition == Transition.NullTransition) {
            System.out.println("Error, Null State Transition!");
            return;
        }
        if (stateId == StateId.NullStateId) {
            System.out.println("Null State Id is invalid state!");
            return;
        }
        if (map.containsKey(transition)) {
            System.out.println("State: " + currentStateId + " already has a transition " + transition);
            return;
        }
        map.put(transition, stateId);
    }

    public void deleteTransition(Transition transition) {
        if (transition == Transition.NullTransition) {
            System.out.println("Null transition is invalid transition!");
            return;
        }
        if (map.containsKey(transition)) {
            map.remove(transition);
            return;
        }
        System.out.println("No transition found!");
    }

    public StateId getOutputState(Transition transition) {
        return map.getOrDefault(transition, StateId.NullStateId);
    }

    public void doBeforeEntering(Object options) {
    }

    public void doBeforeLeaving(Object options) {
    }

    public abstract void executeUI();

    public static class Operations {
        private final List<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<String, Runnable>, PermissionContainer>> operations = new ArrayList<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<String, Runnable>, PermissionContainer>>();

        public List<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<String, Runnable>, PermissionContainer>> getOperations() {
            return operations;
        }
    }
}
