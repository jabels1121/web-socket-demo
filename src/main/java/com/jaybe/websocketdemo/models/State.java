package com.jaybe.websocketdemo.models;

public enum State {

    ARM(1), DISARM(2);

    private final Integer stateCode;

    State(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public static State valueOfCommandCode(Integer stateCode) {
        for (State value : values()) {
            if (value.getStateCode().equals(stateCode)) {
                return value;
            }
        }
        return null;
    }

}
