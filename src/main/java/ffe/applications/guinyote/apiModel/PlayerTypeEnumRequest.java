package ffe.applications.guinyote.apiModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PlayerTypeEnumRequest {
    HUMAN("HUMAN"),
    AI("AI");

    private String value;

    PlayerTypeEnumRequest(String value) {
        this.value = value;
    }

    @JsonCreator
    public static PlayerTypeEnumRequest fromValue(String value) {
        for (PlayerTypeEnumRequest b : PlayerTypeEnumRequest.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
