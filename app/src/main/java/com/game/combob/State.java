package com.game.combob;


public enum State {


    EMPTY(-1),

    NOTSELECTED(-2),

    COMP(-3),

    BLOCK(-4),

    SELECTED(-5);

    private int stateValue;

    private State(int value) {
        stateValue = value;
    }

    public int getValue() {
        return stateValue;
    }

    public static State fromInt(int i) {
        for (State s : values()) {
            if (s.getValue() == i) {
                return s;
            }
        }
        return EMPTY;
    }
}
