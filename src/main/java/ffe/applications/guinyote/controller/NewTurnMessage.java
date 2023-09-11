package ffe.applications.guinyote.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewTurnMessage {

    private Long newTurnPlayerId;
    private Long gameId;
}