package com.bell_sic;

import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.StateMachineSystem;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.state_machine.states.*;

public final class UILoop {
    private static final StateMachineSystem fsm = new StateMachineSystem();
    private static boolean toBreak = false;

    public static void execute() {
        UIState loginState = new Login();
        loginState.addTransition(Transition.GoToMainMenu, StateId.MainMenu);

        UIState mainMenuState = new MainMenu();
        mainMenuState.addTransition(Transition.LogOut, StateId.Login);
        mainMenuState.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        UIState adminMenuState = new AdminControl();
        adminMenuState.addTransition(Transition.LogOut, StateId.Login);
        adminMenuState.addTransition(Transition.GoToMainMenu, StateId.MainMenu);
        adminMenuState.addTransition(Transition.GoToAddDoctorMenu, StateId.AddDoctorMenu);
        adminMenuState.addTransition(Transition.GoToAddReceptionistMenu, StateId.AddReceptionistMenu);

        UIState addDoctorMenu = new AddDoctor();
        addDoctorMenu.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        UIState addReceptionistMenu = new AddReceptionist();
        addReceptionistMenu.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        fsm.addState(loginState);
        fsm.addState(mainMenuState);
        fsm.addState(adminMenuState);
        fsm.addState(addDoctorMenu);
        fsm.addState(addReceptionistMenu);

        while (!toBreak) {
            fsm.getCurrentState().executeUI();
        }
    }

    public static void breakLoop() {
        toBreak = true;
    }

    public static void setTransition(Transition transition, Object options) {
        fsm.performTransition(transition, options);
    }

    public static void setTransition(Transition transition) {
        fsm.performTransition(transition, null);
    }
}
