package ffe.applications.guinyote.controller;

import jakarta.annotation.PostConstruct;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Log4j2
@Controller
public class GameWSController {

    private final SimpMessagingTemplate messagingTemplate;

    public GameWSController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void postConstruct() {
        log.info("postConstruct");
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        log.info("greeting");
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

    public void sendMessage(HelloMessage message) {
        log.info("Send message");
        messagingTemplate.convertAndSend("/topic/greetings", message);
        messagingTemplate.convertAndSend("/topic/game-messages", message);
    }

    public void sendNewTurnMessage(NewTurnMessage newTurnMessage) {
        log.info("sendNewTurnMessage");
        messagingTemplate.convertAndSend("/topic/new-turn-messages", newTurnMessage);
    }

    public void sendCardToPlayerMessage(long gameId, long playerId, long cardId) {
        log.info("sendCardToPlayerMessage");
        CardToPlayerMessage cardToPlayerMessage = new CardToPlayerMessage();
        cardToPlayerMessage.setPlayerId(playerId);
        cardToPlayerMessage.setGameId(gameId);
        cardToPlayerMessage.setCardId(cardId);
        messagingTemplate.convertAndSend("/topic/card-to-player-messages", cardToPlayerMessage);
    }

    public void sendStartGameMessage(GameStatusMessage gameStatusMessage) {
        log.info("sendStartGameMessage");
        messagingTemplate.convertAndSend("/topic/game-status-messages", gameStatusMessage);
    }
}
