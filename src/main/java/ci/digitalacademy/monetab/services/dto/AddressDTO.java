package ci.digitalacademy.monetab.services.dto;

import ci.digitalacademy.monetab.models.User;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class AddressDTO {

    private Long id;

    private String country;

    private String city;

    private String street;


}
