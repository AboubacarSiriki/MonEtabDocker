package ci.digitalacademy.monetab.services.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JwtTokenDTO {

    @JsonProperty("id_token")
    private String id_token ;

}
