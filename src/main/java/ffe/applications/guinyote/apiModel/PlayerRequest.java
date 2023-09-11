package ffe.applications.guinyote.apiModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;


@JsonTypeName("playerRequest")
@Data
public class PlayerRequest {
    @JsonProperty("playerId")
    private long playerId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("type")
    private PlayerTypeEnumRequest type;

}
