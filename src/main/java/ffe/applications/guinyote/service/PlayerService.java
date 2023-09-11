package ffe.applications.guinyote.service;

import ffe.applications.guinyote.apiModel.PlayerRequest;
import ffe.applications.guinyote.converter.PlayerConverter;
import ffe.applications.guinyote.exception.AlreadyExistsElementException;
import ffe.applications.guinyote.exception.NotFoundElementException;
import ffe.applications.guinyote.model.PlayerModel;
import ffe.applications.guinyote.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository repo;


    public PlayerRequest getPlayer(Long id) throws NotFoundElementException {
        PlayerModel playerModel = repo.findById(id).orElseThrow(() -> new NotFoundElementException("Player", id));
        PlayerRequest playerRequest = new PlayerRequest();
        PlayerConverter.convertFromModelToRequest(playerModel, playerRequest);
        return playerRequest;
    }

    public PlayerRequest addNewPlayer(PlayerRequest playerRequest) throws AlreadyExistsElementException {
        Optional<PlayerModel> byName = repo.findByName(playerRequest.getName());
        if (byName.isEmpty()) {
            PlayerModel playerModel = new PlayerModel();
            PlayerConverter.convertFromRequestToModel(playerRequest, playerModel);
            playerModel.setCardsInHand(new ArrayList<>());

            playerModel = repo.save(playerModel);
            PlayerRequest playerRequestToResponse = new PlayerRequest();
            PlayerConverter.convertFromModelToRequest(playerModel, playerRequestToResponse);
            return playerRequestToResponse;
        } else {
            throw new AlreadyExistsElementException("Player", playerRequest.getName());
        }
    }

    public PlayerRequest getPlayerByName(String name) {
        Optional<PlayerModel> byName = repo.findByName(name);
        if (byName.isPresent()) {
            PlayerRequest playerRequest = new PlayerRequest();
            PlayerConverter.convertFromModelToRequest(byName.get(), playerRequest);
            return playerRequest;
        }
        return null;
    }
}
