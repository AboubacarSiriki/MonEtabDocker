package ci.digitalacademy.monetab.services.dto;

import ci.digitalacademy.monetab.models.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleUserDTO {

    private Long id;

    private String role;

    private String slug ;

    private UserDTO user;
}
