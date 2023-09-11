package ffe.applications.guinyote.repository;

import ffe.applications.guinyote.model.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<CardModel, Long> {

}
