package ffe.applications.guinyote.apiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;


@JsonTypeName("playerToGameRequest")
@Data
public class PlayerToGameRequest {
    @JsonProperty("gameId")
    private long gameId;

    @JsonProperty("playerId")
    private long playerId;

}
