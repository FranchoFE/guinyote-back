package ffe.applications.guinyote.service;

import ffe.applications.guinyote.model.CardModel;
import ffe.applications.guinyote.model.CardTypeEnumModel;
import ffe.applications.guinyote.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CardService {

    @Autowired
    CardRepository repo;

    public List<CardModel> generateCardsToNewGame() {
        List<CardModel> cards = new ArrayList<>();
        List<CardTypeEnumModel> types = List.of(CardTypeEnumModel.BASTOS, CardTypeEnumModel.COPAS,
                CardTypeEnumModel.OROS, CardTypeEnumModel.ESPADAS);

        types.forEach(type -> {
            for (int i = 1; i <= 12; i++) {
                if (i != 8 && i != 9) {
                    CardModel newCard = new CardModel();
                    newCard.setType(type);
                    newCard.setNumber(i);
                    cards.add(newCard);
                }
            }
        });

        Collections.shuffle(cards);

        return repo.saveAll(cards);
    }
}
