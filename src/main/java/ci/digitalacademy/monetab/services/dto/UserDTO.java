package ci.digitalacademy.monetab.services.dto;


import ci.digitalacademy.monetab.models.RoleUser;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class UserDTO {

    private Long id;

    private String speudo;

    private String password;

    private boolean active;

    private Instant creationdate;

    private SchoolDTO schoolDTO;

    private String slug;

    private Set<RoleUserDTO> roleUserDTOS;


}
