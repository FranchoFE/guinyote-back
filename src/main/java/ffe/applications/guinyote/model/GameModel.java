package ffe.applications.guinyote.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "game_tb")
@Getter
@Setter
public class GameModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id", updatable = false, nullable = false)
    private long gameId;

    @Column(name = "name", updatable = false, nullable = false, unique = true)
    private String name;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private StatusEnumModel status;

    @OneToMany(targetEntity = PlayerModel.class, fetch = FetchType.EAGER)
    private List<PlayerModel> players;

    @OneToMany(targetEntity = CardModel.class, fetch = FetchType.EAGER)
    private List<CardModel> cardsToPlay;

    @OneToOne(targetEntity = CardModel.class)
    private CardModel cardOnTable;

    @OneToOne(targetEntity = PlayerModel.class)
    private PlayerModel playerInTurn;
}
