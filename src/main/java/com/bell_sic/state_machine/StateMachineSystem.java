package com.bell_sic.state_machine;

import java.util.ArrayList;

public class StateMachineSystem {
    private final ArrayList<UIState> states;
    private StateId currentStateId;
    private UIState currentState;

    public StateMachineSystem() {
        states = new ArrayList<>();
    }

    public StateId getCurrentStateId() {
        return currentStateId;
    }

    public UIState getCurrentState() {
        return currentState;
    }

    public void addState(UIState state) {
        if (state == null) {
            System.err.println("Null is not allowed!");
            return;
        }
        if (states.size() == 0) {
            states.add(state);
            currentState = state;
            currentStateId = state.getCurrentStateId();
            return;
        }
        if (states.stream().anyMatch(uiState -> uiState.getCurrentStateId() == state.getCurrentStateId())) {
            System.err.println("State has already been added!");
            return;
        }
        states.add(state);
    }

    public void deleteState(StateId stateId) {
        if (stateId == StateId.NullStateId) {
            System.err.println("Null State not allowed!");
            return;
        }
        for (var state :
                states.stream().filter(uiState -> uiState.getCurrentStateId() == stateId).toArray()) {
            states.remove(state);
            return;
        }
        System.err.println("StateId: " + stateId + " was not on the list!");
    }

    public void performTransition(Transition transition, Object options) {
        if (transition == Transition.NullTransition) {
            System.err.println("Null transition is not allowed!");
            return;
        }
        var id = currentState.getOutputState(transition);
        if (id == StateId.NullStateId) {
            System.err.println("Stateid: " + id + " does not have a target state for transition " + transition);
            return;
        }
        currentStateId = id;

        boolean found = false;

        for (var state :
                states.stream().filter(uiState -> uiState.getCurrentStateId() == currentStateId).toArray()) {
            currentState.doBeforeLeaving(options);
            currentState = (UIState) state;
            currentState.doBeforeEntering(options);
            found = true;
            break;
        }
        if (!found) System.err.println("Cannot find a valid state for transition: " + transition);
    }
}
