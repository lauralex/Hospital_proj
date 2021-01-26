package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.LogoutPermission;
import com.bell_sic.entity.permission.ReadHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;

public class MainMenu extends UIState {
    private Object options;
    public MainMenu() {
        super(StateId.MainMenu);

        stateOperations.addOperation("logout", () -> UILoop.setTransition(Transition.LogOut), LogoutPermission.get());
        stateOperations.addOperation("admin menu", () -> UILoop.setTransition(Transition.GoToAdminMenu, options), ReadHospitalInfoPermission.get());
        stateOperations.addOperation("Exit", UILoop::breakLoop, ExitPermission.get());
    }

    @Override
    public void doBeforeEntering(Object options) {
        this.options = options;
    }

    @Override
    public void executeUI() {
        stateOperations.checkUserInputAndExecute();
    }
}
