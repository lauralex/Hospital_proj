package com.bell_sic.state_machine;

import com.bell_sic.entity.permission.PermissionContainer;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleLineReader;
import com.bell_sic.utility.OnlyElemCollector;
import com.bell_sic.utility.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class StateOperations {
    private final Operations operations = new Operations();

    public void checkUserInputAndExecute(Operations permissibleOperations) throws NullPointerException {
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

    public void addOperation(CharSequence operationString, Runnable operationAction, PermissionContainer permission) throws NullPointerException {
        operations.add(new Pair<>(
                new Pair<>(new Pair<>(operationString.toString(), ""), operationAction), permission)
        );
    }

    public void modifyOperationString(int index, CharSequence charSequence) throws NullPointerException {
        try {
            operations.get(index).first().first().setSecond(charSequence.toString());
        } catch (IndexOutOfBoundsException e) {
            ConsoleColoredPrinter.println("Invalid operation index!");
            e.printStackTrace();
        }
    }

    public void modifyOperationString(CharSequence search, CharSequence charSequence) throws NoSuchElementException, IllegalArgumentException, NullPointerException {
        var res = operations.stream().filter(pairPermissionContainerPair -> pairPermissionContainerPair.first().first().first()
                .toLowerCase().contains(search.toString().toLowerCase()));


        res.collect(OnlyElemCollector.getOnly())
                .first()
                .first()
                .setSecond(charSequence.toString());
    }

    public void clearOperations() {
        operations.clear();
    }

    public Operations getOperations() {
        return operations;
    }

    public Operations getPermissibleOperations() {

        return operations.stream().filter(operation -> SessionManager.getCurrentUser().checkPermission(operation.second())).collect(Collectors.toCollection(Operations::new));

    }

    public static class Operations extends ArrayList<Pair<Pair<Pair<String, String>, Runnable>, PermissionContainer>> {
    }
}