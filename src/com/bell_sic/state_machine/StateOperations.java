package com.bell_sic.state_machine;

import com.bell_sic.entity.permission.PermissionContainer;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleLineReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class StateOperations {
    private final Operations operations = new Operations();

    public void checkUserInputAndExecute(ArrayList<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<Operations.StringTuple, Runnable>, PermissionContainer>> permissibleOperations) {
        var opLength = permissibleOperations.size();

        while (true) {
            for (int i = 0; i < opLength; i++) {
                var operationStringTuple = permissibleOperations.get(i).first().first();
                System.out.println(i + ") " + operationStringTuple.first() + operationStringTuple.second());
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

    public void addOperation(CharSequence operationString, Runnable operationAction, PermissionContainer permission) {
        operations.getOperations().add(new ConsoleOptionWriter.Pair<>(
                new ConsoleOptionWriter.Pair<>(new Operations.StringTuple(operationString.toString(), ""), operationAction), permission)
        );
    }

    public void modifyOperationString(int index, CharSequence charSequence) {
        try {
            operations.getOperations().get(index).first().first().setSecond(charSequence.toString());
        } catch (IndexOutOfBoundsException e) {
            ConsoleColoredPrinter.println("Invalid operation index!");
            e.printStackTrace();
        }
    }

    public void modifyOperationString(CharSequence search, CharSequence charSequence) {
        operations.getOperations().stream().filter(pairPermissionContainerPair -> pairPermissionContainerPair.first().first().first()
                .toLowerCase().contains(search.toString().toLowerCase())).findFirst()
                .ifPresent(pairPermissionContainerPair -> pairPermissionContainerPair
                        .first().first().setSecond(charSequence.toString()));
    }

    public void clearOperations() {
        operations.getOperations().clear();
    }

    public List<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<Operations.StringTuple, Runnable>, PermissionContainer>> getOperations() {
        return operations.getOperations();
    }

    public ArrayList<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<Operations.StringTuple, Runnable>, PermissionContainer>> getPermissibleOperations() {
        ArrayList<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<Operations.StringTuple, Runnable>, PermissionContainer>> permissibleOperations = new ArrayList<>();

        for (ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<Operations.StringTuple, Runnable>, PermissionContainer> operation :
                operations.getOperations()) {
            if (SessionManager.getCurrentUser().checkPermission(operation.second())) {
                permissibleOperations.add(operation);
            }
        }
        return permissibleOperations;
    }

    public static class Operations {
        private final List<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<StringTuple, Runnable>, PermissionContainer>> operations = new ArrayList<>();

        public static class StringTuple extends ConsoleOptionWriter.Pair<String, String> {

            public StringTuple(String first, String second) {
                super(first, second);
            }
        }
        public List<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<StringTuple, Runnable>, PermissionContainer>> getOperations() {
            return operations;
        }
    }
}
