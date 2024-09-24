package ci.digitalacademy.monetab.services;

import ci.digitalacademy.monetab.models.Address;
import ci.digitalacademy.monetab.repositories.AdresseRepository;
import ci.digitalacademy.monetab.services.dto.AddressDTO;
import ci.digitalacademy.monetab.services.impl.AdresseServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
public class AdressServiceImplTest {

    @MockBean
    private AdresseRepository adresseRepository;

    @Autowired
    private AdresseServiceImpl adresseService;

    @Test
    void whenFinById_thenReturnAddressNotEmpty(){

        when(adresseRepository.findById(1L)).thenReturn(Optional.of(new Address(1L,"CI","Abidjan","Rue 24")));
        Optional<AddressDTO> address = adresseService.findOne(1L);

        assertTrue("Address is not empty", address.isPresent());
    }

}
