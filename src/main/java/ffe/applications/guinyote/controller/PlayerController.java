package ffe.applications.guinyote.controller;

import ffe.applications.guinyote.apiModel.PlayerRequest;
import ffe.applications.guinyote.exception.AlreadyExistsElementException;
import ffe.applications.guinyote.exception.NotFoundElementException;
import ffe.applications.guinyote.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
public class PlayerController {

    @Autowired
    PlayerService playerService;

    @GetMapping(value = "/v1/players/{playerId}", produces = {"application/json"})
    public ResponseEntity<PlayerRequest> getPlayer(@PathVariable("playerId") Long id) {
        log.info("getPlayer - {}", id);

        try {
            PlayerRequest playerRequest = playerService.getPlayer(id);
            log.info("getPlayer - {} -> {}", id, playerRequest.getName());
            return ResponseEntity.ok(playerRequest);
        } catch (NotFoundElementException ex) {
            log.info("getPlayer - not found {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/v1/players", produces = {"application/json"})
    public ResponseEntity<PlayerRequest> getPlayerByName(@RequestParam("name") String name) {
        log.info("getPlayerByName - {}", name);

        PlayerRequest playerRequest = playerService.getPlayerByName(name);

        if (playerRequest != null) {
            return ResponseEntity.ok(playerRequest);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/v1/players", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<PlayerRequest> addNewPlayer(@RequestBody PlayerRequest playerRequest) {
        log.info("addNewPlayer - {}", playerRequest.getName());

        try {
            PlayerRequest playerRequestCreated = playerService.addNewPlayer(playerRequest);
            log.info("addNewPlayer - Created {}. Id = {}", playerRequest.getName(),
                    playerRequestCreated.getPlayerId());
            return ResponseEntity.status(HttpStatus.CREATED).body(playerRequestCreated);
        } catch (AlreadyExistsElementException ex) {
            log.error("addNewPlayer - AlreadyExistsElementException {}", playerRequest.getName());
            return ResponseEntity.badRequest().build();
        }
    }
}
