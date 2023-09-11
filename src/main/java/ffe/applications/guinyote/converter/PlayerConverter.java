package ffe.applications.guinyote.converter;

import ffe.applications.guinyote.apiModel.PlayerRequest;
import ffe.applications.guinyote.apiModel.PlayerTypeEnumRequest;
import ffe.applications.guinyote.model.PlayerModel;
import ffe.applications.guinyote.model.PlayerTypeEnumModel;

public class PlayerConverter {

    public static void convertFromModelToRequest(PlayerModel gameModel, PlayerRequest gameRequest) {
        gameRequest.setPlayerId(gameModel.getPlayerId());
        gameRequest.setName(gameModel.getName());
        gameRequest.setType(PlayerTypeEnumRequest.valueOf(gameModel.getType().toString()));
    }

    public static void convertFromRequestToModel(PlayerRequest gameRequest, PlayerModel gameModel) {
        gameModel.setPlayerId(gameRequest.getPlayerId());
        gameModel.setName(gameRequest.getName());
        if (gameRequest.getType() != null) {
            gameModel.setType(PlayerTypeEnumModel.valueOf(gameRequest.getType().toString()));
        }
    }
}
