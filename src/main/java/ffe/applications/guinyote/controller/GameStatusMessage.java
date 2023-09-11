package ffe.applications.guinyote.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameStatusMessage {

    private Long gameId;
    private Long player1Id;
    private String player1Name;
    private Long player2Id;
    private String player2Name;
    private Long player3Id;
    private String player3Name;
    private Long player4Id;
    private String player4Name;
    private String status;
}