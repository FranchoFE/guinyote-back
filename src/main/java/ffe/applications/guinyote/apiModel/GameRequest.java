package ffe.applications.guinyote.apiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

import java.util.List;


@JsonTypeName("gameRequest")
@Data
public class GameRequest {
    @JsonProperty("gameId")
    private long gameId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("status")
    private StatusEnumRequest status;

    @JsonProperty("players")
    private List<String> players;

}
