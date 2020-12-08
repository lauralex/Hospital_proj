package com.bell_sic.state_machine.states;

import com.bell_sic.UILoop;
import com.bell_sic.entity.permission.ExitPermission;
import com.bell_sic.entity.permission.LogoutPermission;
import com.bell_sic.entity.permission.WriteHospitalInfoPermission;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;

public class AdminControl extends UIState {
    public AdminControl() {
        super(StateId.AdminMenu);

        addOperation("logout", () -> UILoop.setTransition(Transition.LogOut), LogoutPermission.get());
        addOperation("Add a doctor", () -> UILoop.setTransition(Transition.GoToAddDoctorMenu), WriteHospitalInfoPermission.get());
        addOperation("Add a receptionist", () -> UILoop.setTransition(Transition.GoToAddReceptionistMenu), WriteHospitalInfoPermission.get());
        addOperation("Return to main menu", () -> UILoop.setTransition(Transition.GoToMainMenu), ExitPermission.get());
        addOperation("Exit application", UILoop::breakLoop, ExitPermission.get());
    }

    @Override
    public void executeUI() {
        var permissibleOperations = getPermissibleOperations();

        checkUserInputAndExecute(permissibleOperations);
    }

}
