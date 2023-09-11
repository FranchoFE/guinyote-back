package ffe.applications.guinyote.exception;

import lombok.Getter;

@Getter
public class NotFoundElementException extends Exception {

    private String type;
    private Long id;

    public NotFoundElementException(String type, Long id) {
        this.type = type;
        this.id = id;

    }
}
