package ffe.applications.guinyote.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "player_tb")
@Getter
@Setter
public class PlayerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id", updatable = false, nullable = false)
    private long playerId;

    @Column(name = "name", updatable = false, nullable = false, unique = true)
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private PlayerTypeEnumModel type;

    @OneToMany(targetEntity = CardModel.class, fetch = FetchType.EAGER)
    private List<CardModel> cardsInHand;
}
