package ffe.applications.guinyote.apiModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusEnumRequest {
    PENDING("PENDING"),
    PLAYING("PLAYING"),
    STARTING("STARTING"),
    ABORTED("ABORTED"),
    DONE("DONE");

    private String value;

    StatusEnumRequest(String value) {
        this.value = value;
    }

    @JsonCreator
    public static StatusEnumRequest fromValue(String value) {
        for (StatusEnumRequest b : StatusEnumRequest.values()) {
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
