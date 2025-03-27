package com.schedulo.schedulo.enums;

public enum MeetingStatus {
    SCHEDULED("S"),
    ONGOING("O"),
    CANCELLED("I"),
    COMPLETED("C");


    private final String code;

    MeetingStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
