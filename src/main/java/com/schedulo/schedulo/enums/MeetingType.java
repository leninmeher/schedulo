package com.schedulo.schedulo.enums;

public enum MeetingType {

    SCHEDULED("S"),
    CURRENT("C");

    private final String code;

    MeetingType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
