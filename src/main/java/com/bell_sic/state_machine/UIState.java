package com.bell_sic.state_machine;

import java.util.HashMap;
import java.util.Map;

public abstract class UIState {
    protected final StateOperations stateOperations = new StateOperations();
    private final Map<Transition, StateId> map = new HashMap<>();
    private final StateId currentStateId;

    public UIState(StateId stateId) {
        currentStateId = stateId;
    }

    public StateId getCurrentStateId() {
        return currentStateId;
    }

    /**
     * Add a {@code transition} from the current UIState to the UIState with the specified {@code stateId}.
     * @param transition The {@linkplain Transition} from the current {@code UIState} instance.
     * @param stateId The final {@linkplain StateId}.
     */
    public void addTransition(Transition transition, StateId stateId) {
        if (transition == Transition.NullTransition) {
            System.out.println("Error, Null State Transition!");
            return;
        }
        if (stateId == StateId.NullStateId) {
            System.out.println("Null State Id is invalid state!");
            return;
        }
        if (map.containsKey(transition)) {
            System.out.println("State: " + currentStateId + " already has a transition " + transition);
            return;
        }
        map.put(transition, stateId);
    }

    public void deleteTransition(Transition transition) {
        if (transition == Transition.NullTransition) {
            System.out.println("Null transition is invalid transition!");
            return;
        }
        if (map.containsKey(transition)) {
            map.remove(transition);
            return;
        }
        System.out.println("No transition found!");
    }

    public StateId getOutputState(Transition transition) {
        return map.getOrDefault(transition, StateId.NullStateId);
    }

    /**
     * Set additional options for the entrant state.
     * @param options Additional {@code options} for the entrant state.
     */
    public void doBeforeEntering(Object options) {
        // DEFAULT BEHAVIOUR: DO NOTHING!
    }

    /**
     * Set additional options for the leaving state.
     * @param options Additional {@code options} for the leaving state.
     */
    public void doBeforeLeaving(Object options) {
        // DEFAULT BEHAVIOUR: DO NOTHING!
    }

    /**
     * This method represents the main {@code UIState} execution routine.
     */
    public abstract void executeUI();

}
