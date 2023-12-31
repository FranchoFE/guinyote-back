package ffe.applications.guinyote.repository;

import ffe.applications.guinyote.model.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerModel, Long> {
    Optional<PlayerModel> findByName(String name);
}
