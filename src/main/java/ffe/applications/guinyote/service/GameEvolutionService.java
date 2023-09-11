package ffe.applications.guinyote.service;

import ffe.applications.guinyote.controller.GameStatusMessage;
import ffe.applications.guinyote.controller.GameWSController;
import ffe.applications.guinyote.controller.NewTurnMessage;
import ffe.applications.guinyote.model.GameModel;
import ffe.applications.guinyote.model.StatusEnumModel;
import ffe.applications.guinyote.repository.GameRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EnableScheduling
@Log4j2
public class GameEvolutionService {

    @Autowired
    GameWSController gameWSController;

    @Autowired
    GameRepository gameRepository;

    @Autowired
    GameService gameService;

    @Scheduled(fixedDelay = 5 * 1000) // Cada 5 segundos
    public void gameEvolution() {

        try {
            startGames();
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }

        newPlayerTurn();
    }

    private void startGames() throws InterruptedException {
        List<GameModel> games = gameRepository.findByStatus(StatusEnumModel.STARTING);

        if (!games.isEmpty()) {
            Thread.sleep(5000);
        }

        games.forEach(gameModel -> {
            gameService.startGame(gameModel);
            GameStatusMessage gameStatusMessage = new GameStatusMessage();
            gameStatusMessage.setGameId(gameModel.getGameId());
            gameStatusMessage.setStatus(gameModel.getStatus().toString());
            gameStatusMessage.setPlayer1Id(gameModel.getPlayers().get(0).getPlayerId());
            gameStatusMessage.setPlayer2Id(gameModel.getPlayers().get(1).getPlayerId());
            gameStatusMessage.setPlayer3Id(gameModel.getPlayers().get(2).getPlayerId());
            gameStatusMessage.setPlayer4Id(gameModel.getPlayers().get(3).getPlayerId());
            gameStatusMessage.setPlayer1Name(gameModel.getPlayers().get(0).getName());
            gameStatusMessage.setPlayer2Name(gameModel.getPlayers().get(1).getName());
            gameStatusMessage.setPlayer3Name(gameModel.getPlayers().get(2).getName());
            gameStatusMessage.setPlayer4Name(gameModel.getPlayers().get(3).getName());
            gameWSController.sendStartGameMessage(gameStatusMessage);
        });
    }

    private void newPlayerTurn() {
        List<GameModel> games = gameRepository.findByStatus(StatusEnumModel.PLAYING);

        games.forEach(gameModel -> {
            int playerInTurnPosition = gameModel.getPlayers().indexOf(gameModel.getPlayerInTurn());
            gameModel.setPlayerInTurn(gameModel.getPlayers().get((playerInTurnPosition + 1) % 4));

            gameRepository.save(gameModel);

            NewTurnMessage newTurnMessage = new NewTurnMessage();
            newTurnMessage.setNewTurnPlayerId(gameModel.getPlayerInTurn().getPlayerId());
            newTurnMessage.setGameId(gameModel.getGameId());
            gameWSController.sendNewTurnMessage(newTurnMessage);
        });
    }

}
