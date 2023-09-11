package ffe.applications.guinyote.exception;

import lombok.Getter;

@Getter
public class AlreadyExistsElementException extends Exception {

    private String type;
    private String name;

    public AlreadyExistsElementException(String type, String name) {
        this.type = type;
        this.name = name;
    }
}
