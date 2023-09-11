package ffe.applications.guinyote.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "card_tb")
@Getter
@Setter
public class CardModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id", updatable = false, nullable = false)
    private long cardId;

    @Column(name = "number")
    private long number;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private CardTypeEnumModel type;

}
