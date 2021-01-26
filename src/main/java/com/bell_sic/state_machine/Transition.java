package com.bell_sic.state_machine;

/**
 * Use this enum to add a new {@code Transition} to the UI-state-machine-system.
 */
public enum Transition {
    NullTransition,
    GoToMainMenu,
    LogOut,
    GoToAdminMenu,
    GoToAddDoctorMenu,
    GoToAddReceptionistMenu,
    GoToAddPatientMenu,
    GoToModifyEmployeeMenu,
    GoToAppointmentRegistrationMenu,
    GoToAddOperationMenu,
    GoToReplaceDoctorMenu,
    GoToInsertPatientMenu,
}
