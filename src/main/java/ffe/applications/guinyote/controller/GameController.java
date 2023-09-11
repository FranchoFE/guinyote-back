package ffe.applications.guinyote.controller;

import ffe.applications.guinyote.apiModel.GameRequest;
import ffe.applications.guinyote.apiModel.PlayerToGameRequest;
import ffe.applications.guinyote.exception.AlreadyExistsElementException;
import ffe.applications.guinyote.exception.ExistsPlayerInGameException;
import ffe.applications.guinyote.exception.NoMorePlayersAdmitedException;
import ffe.applications.guinyote.exception.NotFoundElementException;
import ffe.applications.guinyote.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class GameController {

    @Autowired
    GameService gameService;

    @GetMapping(value = "/v1/games/{gameId}", produces = {"application/json"})
    public ResponseEntity<GameRequest> getGame(@PathVariable("gameId") Long id) {
        log.info("getGame - {}", id);

        try {
            GameRequest gameRequest = gameService.getGame(id);
            log.info("foundGame - {} -> {}", id, gameRequest.getName());
            return ResponseEntity.ok(gameRequest);
        } catch (NotFoundElementException ex) {
            log.info("foundGame - not found {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/v1/games", produces = {"application/json"})
    public ResponseEntity<List<GameRequest>> getGames() {
        log.info("getGames");

        List<GameRequest> gameRequests = gameService.getAllGames();
        log.info("getGames - {}", gameRequests.size());
        return ResponseEntity.ok(gameRequests);
    }

    @PostMapping(value = "/v1/games", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<GameRequest> addNewGame(@RequestBody GameRequest gameRequest) {
        log.info("addNewGame - {}", gameRequest.getName());

        try {
            GameRequest gameRequestCreated = gameService.addNewGame(gameRequest);
            log.info("addNewGame - Created {}. Id = {}", gameRequest.getName(),
                    gameRequestCreated.getGameId());
            return ResponseEntity.status(HttpStatus.CREATED).body(gameRequestCreated);
        } catch (AlreadyExistsElementException ex) {
            log.error("addNewGame - AlreadyExistsGameException {}", gameRequest.getName());
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping(value = "/v1/games", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<GameRequest> addPlayerToGame(@RequestBody PlayerToGameRequest playerToGameRequest) {
        log.info("addPlayerToGame - {} - {}", playerToGameRequest.getGameId(), playerToGameRequest.getPlayerId());

        try {
            GameRequest gameRequest = gameService.addPlayerToGame(playerToGameRequest);
            return ResponseEntity.ok(gameRequest);

        } catch (NotFoundElementException | NoMorePlayersAdmitedException | ExistsPlayerInGameException ex) {
            throw new RuntimeException(ex);
        }
    }
}
