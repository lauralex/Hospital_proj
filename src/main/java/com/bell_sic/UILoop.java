package com.bell_sic;

import com.bell_sic.entity.Hospital;
import com.bell_sic.entity.wards.Ward;
import com.bell_sic.state_machine.StateId;
import com.bell_sic.state_machine.StateMachineSystem;
import com.bell_sic.state_machine.Transition;
import com.bell_sic.state_machine.UIState;
import com.bell_sic.state_machine.states.*;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;

public final class UILoop {
    private static final StateMachineSystem fsm = new StateMachineSystem();
    private static boolean toBreak = false;

    public void execute() {
        Reflections reflections = new Reflections("com.bell_sic");
        var res = reflections.getSubTypesOf(Ward.class);
        for (var subtype :
                res) {
            try {
                Hospital.WardView.addWard(subtype.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
                return;
            }
        }

        Hospital.WardView.getWards().forEach(ward -> {
            for (int i = 0; i < 2; i++) {
                ward.addRoom(10);
            }
        });

        UIState loginState = new Login();
        loginState.addTransition(Transition.GoToMainMenu, StateId.MainMenu);
        loginState.addTransition(Transition.GoToPreLoginMenu, StateId.PreLoginMenu);

        UIState mainMenuState = new MainMenu();
        mainMenuState.addTransition(Transition.LogOut, StateId.Login);
        mainMenuState.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        UIState adminMenuState = new AdminControl();
        adminMenuState.addTransition(Transition.LogOut, StateId.PreLoginMenu);
        adminMenuState.addTransition(Transition.GoToMainMenu, StateId.MainMenu);
        adminMenuState.addTransition(Transition.GoToAddDoctorMenu, StateId.AddDoctorMenu);
        adminMenuState.addTransition(Transition.GoToAddReceptionistMenu, StateId.AddReceptionistMenu);
        adminMenuState.addTransition(Transition.GoToAddPatientMenu, StateId.AddPatientMenu);
        adminMenuState.addTransition(Transition.GoToModifyEmployeeMenu, StateId.ModifyEmployeeMenu);
        adminMenuState.addTransition(Transition.GoToAppointmentRegistrationMenu, StateId.AppointmentRegistrationMenu);
        adminMenuState.addTransition(Transition.GoToAddOperationMenu, StateId.AddOperationMenu);
        adminMenuState.addTransition(Transition.GoToReplaceDoctorMenu, StateId.ReplaceDoctorMenu);

        UIState addDoctorMenu = new AddDoctor();
        addDoctorMenu.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        UIState addReceptionistMenu = new AddReceptionist();
        addReceptionistMenu.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        UIState addPatientMenu = new AddPatient();
        addPatientMenu.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        UIState modifyEmployeeMenu = new ModifyEmployee();
        modifyEmployeeMenu.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        UIState appointmentRegistrationMenu = new AppointmentRegistrationMenu();
        appointmentRegistrationMenu.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);
        appointmentRegistrationMenu.addTransition(Transition.GoToInsertPatientMenu, StateId.InsertPatientMenu);

        UIState addOperationMenu = new AddOperationMenu();
        addOperationMenu.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        UIState replaceDoctorMenu = new DoctorMigrationMenu();
        replaceDoctorMenu.addTransition(Transition.GoToAdminMenu, StateId.AdminMenu);

        UIState insertPatientMenu = new InsertPatient();
        insertPatientMenu.addTransition(Transition.GoToAppointmentRegistrationMenu, StateId.AppointmentRegistrationMenu);

        UIState preLoginMenu = new PreLogin();
        preLoginMenu.addTransition(Transition.LogIn, StateId.Login);


        fsm.addState(preLoginMenu);
        fsm.addState(loginState);
        fsm.addState(mainMenuState);
        fsm.addState(adminMenuState);
        fsm.addState(addDoctorMenu);
        fsm.addState(addReceptionistMenu);
        fsm.addState(addPatientMenu);
        fsm.addState(modifyEmployeeMenu);
        fsm.addState(appointmentRegistrationMenu);
        fsm.addState(addOperationMenu);
        fsm.addState(replaceDoctorMenu);
        fsm.addState(insertPatientMenu);


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

    private static class InstanceHolder{
        private static final UILoop instance = new UILoop();
    }
    public static UILoop get() {
        return InstanceHolder.instance;
    }
}
