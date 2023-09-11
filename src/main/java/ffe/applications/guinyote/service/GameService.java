package ffe.applications.guinyote.service;

import ffe.applications.guinyote.apiModel.GameMessage;
import ffe.applications.guinyote.apiModel.GameRequest;
import ffe.applications.guinyote.apiModel.PlayerToGameRequest;
import ffe.applications.guinyote.controller.GameWSController;
import ffe.applications.guinyote.converter.GameConverter;
import ffe.applications.guinyote.exception.AlreadyExistsElementException;
import ffe.applications.guinyote.exception.ExistsPlayerInGameException;
import ffe.applications.guinyote.exception.NoMorePlayersAdmitedException;
import ffe.applications.guinyote.exception.NotFoundElementException;
import ffe.applications.guinyote.model.CardModel;
import ffe.applications.guinyote.model.GameModel;
import ffe.applications.guinyote.model.PlayerModel;
import ffe.applications.guinyote.model.StatusEnumModel;
import ffe.applications.guinyote.repository.GameRepository;
import ffe.applications.guinyote.repository.PlayerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class GameService {
    @Autowired
    SimpMessagingTemplate messagingTemplate;
    @Autowired
    GameRepository repo;
    @Autowired
    PlayerRepository playerRepo;
    @Autowired
    CardService cardService;

    @Autowired
    GameWSController gameWSController;

    public GameRequest getGame(Long id) throws NotFoundElementException {
        GameModel gameModel = repo.findById(id).orElseThrow(() -> new NotFoundElementException("Game", id));
        GameRequest gameRequest = new GameRequest();
        GameConverter.convertFromModelToRequest(gameModel, gameRequest);
        return gameRequest;
    }

    public GameRequest addNewGame(GameRequest gameRequest) throws AlreadyExistsElementException {

        Optional<GameModel> byName = repo.findByName(gameRequest.getName());
        if (byName.isEmpty()) {
            GameModel gameModel = new GameModel();
            GameConverter.convertFromRequestToModel(gameRequest, gameModel);
            gameModel.setStatus(StatusEnumModel.PENDING);
            gameModel.setPlayers(new ArrayList<>());

            List<CardModel> cards = cardService.generateCardsToNewGame();
            gameModel.setCardsToPlay(cards);

            gameModel = repo.save(gameModel);
            GameRequest gameRequestToResponse = new GameRequest();
            GameConverter.convertFromModelToRequest(gameModel, gameRequestToResponse);
            return gameRequestToResponse;
        } else {
            throw new AlreadyExistsElementException("Game", gameRequest.getName());
        }
    }

    public GameRequest addPlayerToGame(PlayerToGameRequest playerToGameRequest) throws NotFoundElementException, NoMorePlayersAdmitedException, ExistsPlayerInGameException {

        GameModel gameModel = repo.findById(playerToGameRequest.getGameId())
                .orElseThrow(() -> new NotFoundElementException("Game", playerToGameRequest.getGameId()));

        PlayerModel playerModel = playerRepo.findById(playerToGameRequest.getPlayerId())
                .orElseThrow(() -> new NotFoundElementException("Player", playerToGameRequest.getPlayerId()));

        if (gameModel.getPlayers().size() >= 4) {
            throw new NoMorePlayersAdmitedException();
        }

        if (gameModel.getPlayers().stream().anyMatch(actualPlayer -> actualPlayer.equals(playerModel))) {
            throw new ExistsPlayerInGameException();
        }

        gameModel.getPlayers().add(playerModel);

        // Si hay cuatro jugadores, el juego puede empezar
        if (gameModel.getPlayers().size() == 4) {
            gameModel.setStatus(StatusEnumModel.STARTING);
        }

        gameModel = repo.save(gameModel);
        GameRequest gameRequestToResponse = new GameRequest();
        GameConverter.convertFromModelToRequest(gameModel, gameRequestToResponse);
        return gameRequestToResponse;
    }

    public void startGame(GameModel gameModel) {

        // Primer reparto de cartas
        giveCardsToEachPlayer(gameModel, 3, gameModel.getPlayers().get(0));

        // Segundo reparto de cartas
        giveCardsToEachPlayer(gameModel, 3, gameModel.getPlayers().get(0));

        // Carta en la mesa
        gameModel.setCardOnTable(gameModel.getCardsToPlay().remove(0));

        // Se establece el estado de juego empezado
        gameModel.setStatus(StatusEnumModel.PLAYING);

        // El turno es del primer jugador
        gameModel.setPlayerInTurn(gameModel.getPlayers().get(0));

        log.info("Juego {} comenzado", gameModel.getName());

        repo.save(gameModel);
    }

    private void giveCardsToEachPlayer(GameModel gameModel, int cardsToGive, PlayerModel firstPlayer) {
        int firstPlayerPosition = gameModel.getPlayers().indexOf(firstPlayer);

        for (int j = 0; j < gameModel.getPlayers().size(); j++) {
            PlayerModel player = gameModel.getPlayers()
                    .get((j + firstPlayerPosition) % gameModel.getPlayers().size());

            for (int i = 0; i < cardsToGive; i++) {
                CardModel cardToPlayer = gameModel.getCardsToPlay().remove(0);
                player.getCardsInHand().add(cardToPlayer);
                gameWSController.sendCardToPlayerMessage(gameModel.getGameId(), player.getPlayerId(), cardToPlayer.getCardId());
            }
        }
    }

    public List<GameRequest> getAllGames() {
        List<GameModel> all = repo.findAll();
        List<GameRequest> result = new ArrayList<>();

        all.forEach(gameModel -> {
            GameRequest gameRequestToResponse = new GameRequest();
            GameConverter.convertFromModelToRequest(gameModel, gameRequestToResponse);
            result.add(gameRequestToResponse);
        });

        return result;
    }

    public void sendGameMessage(String gameId, GameMessage message) {
        messagingTemplate.convertAndSend("/topic/game/" + gameId, message);
    }
}
