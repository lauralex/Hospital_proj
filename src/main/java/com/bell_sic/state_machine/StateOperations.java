package com.bell_sic.state_machine;

import com.bell_sic.entity.employees.Employee;
import com.bell_sic.entity.permission.PatientPermission;
import com.bell_sic.entity.permission.PermissionContainer;
import com.bell_sic.utility.ConsoleColoredPrinter;
import com.bell_sic.utility.ConsoleLineReader;
import com.bell_sic.utility.OnlyElemCollector;
import com.bell_sic.utility.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

public class StateOperations {
    private final Operations operations = new Operations();

    /**
     * Display to the console-output the {@code permissibleOperations} for the logged-in user.
     * @param permissibleOperations The {@linkplain Operations} to display to the console-output.
     * @throws NullPointerException If {@code permissibleOperations} is {@code null}.
     */
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

                    if (!(opNumber < opLength)) {
                        ConsoleColoredPrinter.println("Not a valid number: it should be from 0 to " + (opLength - 1));
                        continue;
                    }
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

    /**
     * Display to the console-output the permissible {@link Operations} retrieved with the {@code getPermissibleOperations()} method.
     */
    public void checkUserInputAndExecute() {
        checkUserInputAndExecute(getPermissibleOperations());
    }

    /**
     * Add a new operation.
     * @param operationString Brief primary description.
     * @param operationAction A {@linkplain Runnable} representing the method to be executed.
     * @param permission A subclass of {@linkplain PermissionContainer} representing the permission required for the operation.
     * @throws NullPointerException
     */
    public void addOperation(CharSequence operationString, Runnable operationAction, PermissionContainer permission) throws NullPointerException {
        operations.add(new Pair<>(
                new Pair<>(new Pair<>(operationString.toString(), ""), operationAction), permission)
        );
    }

    /**
     * @param operationString Brief primary description.
     * @param second Brief secondary description.
     * @param operationAction A {@linkplain Runnable} representing the method to be executed.
     * @param permission A subclass of {@linkplain PermissionContainer} representing the permission required for the operation.
     * @throws NullPointerException
     */
    public void addOperation(CharSequence operationString, CharSequence second, Runnable operationAction, PermissionContainer permission) throws NullPointerException {
        operations.add(new Pair<>(
                new Pair<>(new Pair<>(operationString.toString(), second.toString()), operationAction), permission)
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

    public void modifyOperationString(CharSequence search, CharSequence second) throws NoSuchElementException, IllegalArgumentException, NullPointerException {
        modifyOperationString(search, null, second);
    }

    public void modifyOperationString(CharSequence search, CharSequence first, CharSequence second) throws NoSuchElementException, IllegalArgumentException, NullPointerException {
        var res = operations.stream().filter(pairPermissionContainerPair -> pairPermissionContainerPair.first().first().first()
                .toLowerCase().contains(search.toString().toLowerCase()));


        var stringTuple = res.collect(OnlyElemCollector.getOnly())
                .first()
                .first();
        stringTuple.setFirst(Objects.requireNonNullElse(first, stringTuple.first()).toString());
        stringTuple.setSecond(second.toString());
    }

    public void clearOperations() {
        operations.clear();
    }

    /**
     * @return The {@linkplain Operations} with all the operations.
     */
    public Operations getOperations() {
        return operations;
    }

    /**
     * @return Permissible {@linkplain Operations}, according to the permissions of the logged-in user.
     * @see PermissionContainer
     * @see Employee
     */
    public Operations getPermissibleOperations() {
        if (SessionManager.getCurrentUser() == null) {
            return operations.stream().filter(pairPermissionContainerPair -> pairPermissionContainerPair
                    .second() instanceof PatientPermission).collect(Operations::new, Operations::add, Operations::addAll);
        }
        return operations.stream().filter(operation -> SessionManager.getCurrentUser().checkPermission(operation.second())).collect(Collectors.toCollection(Operations::new));

    }

    public static class Operations extends ArrayList<Pair<Pair<Pair<String, String>, Runnable>, PermissionContainer>> {
    }
}
