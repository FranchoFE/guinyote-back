package ffe.applications.guinyote.repository;

import ffe.applications.guinyote.model.GameModel;
import ffe.applications.guinyote.model.StatusEnumModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<GameModel, Long> {
    Optional<GameModel> findByName(String name);

    List<GameModel> findByStatus(StatusEnumModel statusEnumModel);

}
