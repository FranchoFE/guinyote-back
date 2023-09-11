package ffe.applications.guinyote.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardToPlayerMessage {

    private Long playerId;
    private Long gameId;
    private Long cardId;
}