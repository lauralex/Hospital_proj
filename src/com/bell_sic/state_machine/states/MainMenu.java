package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.LogoutPermission;
import com.bell_sic.entity.permission.PermissionContainer;
import com.bell_sic.entity.permission.ReadHospitalInfoPermission;
import com.bell_sic.state_machine.*;

import java.util.ArrayList;

public class MainMenu extends UIState {
    public MainMenu() {
        super(StateId.MainMenu);

        stateOperations.addOperation("logout", () -> UILoop.setTransition(Transition.LogOut), LogoutPermission.get());
        stateOperations.addOperation("admin menu", () -> UILoop.setTransition(Transition.GoToAdminMenu), ReadHospitalInfoPermission.get());
        stateOperations.addOperation("Exit", UILoop::breakLoop, ExitPermission.get());
    }

    @Override
    public void executeUI() {
        ArrayList<ConsoleOptionWriter.Pair<ConsoleOptionWriter.Pair<StateOperations.Operations.StringTuple, Runnable>, PermissionContainer>> permissibleOperations = stateOperations.getPermissibleOperations();
        stateOperations.checkUserInputAndExecute(permissibleOperations);
    }
}
