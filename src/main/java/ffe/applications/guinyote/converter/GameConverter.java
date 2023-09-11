package ffe.applications.guinyote.converter;

import ffe.applications.guinyote.apiModel.GameRequest;
import ffe.applications.guinyote.apiModel.StatusEnumRequest;
import ffe.applications.guinyote.model.GameModel;
import ffe.applications.guinyote.model.StatusEnumModel;

import java.util.ArrayList;

public class GameConverter {

    public static void convertFromModelToRequest(GameModel gameModel, GameRequest gameRequest) {
        gameRequest.setGameId(gameModel.getGameId());
        gameRequest.setName(gameModel.getName());
        gameRequest.setStatus(StatusEnumRequest.valueOf(gameModel.getStatus().toString()));

        gameRequest.setPlayers(new ArrayList<>());
        gameModel.getPlayers().forEach(playerModel -> gameRequest.getPlayers().add(playerModel.getName()));
    }

    public static void convertFromRequestToModel(GameRequest gameRequest, GameModel gameModel) {
        gameModel.setGameId(gameRequest.getGameId());
        gameModel.setName(gameRequest.getName());
        if (gameRequest.getStatus() != null) {
            gameModel.setStatus(StatusEnumModel.valueOf(gameRequest.getStatus().toString()));
        }
    }
}
