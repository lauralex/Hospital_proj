package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.permission.PatientPermission;
import com.bell_sic.state_machine.SessionManager;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.utility.ConsoleColoredPrinter;

public class PreLogin extends UIState {
    public PreLogin() {
        super(StateId.PreLoginMenu);
        stateOperations.addOperation("Login", () -> UILoop.setTransition(Transition.LogIn), PatientPermission.get());
        stateOperations.addOperation("Show operations", this::showOps, PatientPermission.get());
    }

    private void showOps() {
        var allOps = Hospital.OperationView.getAllOperations();
        for (var op :
                allOps) {
            ConsoleColoredPrinter.println(ConsoleColoredPrinter.Color.GREEN, op.toString());
        }
    }

    @Override
    public void doBeforeEntering(Object options) {
        SessionManager.setCurrentUser(null);
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }
}
