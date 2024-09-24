package ci.digitalacademy.monetab.resources;

import ci.digitalacademy.monetab.services.AdresseService;
import ci.digitalacademy.monetab.services.dto.AddressDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddressResourceIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AdresseService adresseService;

    @Test
    void testCreation(){

        //Arrange
        AddressDTO address = new AddressDTO(1L,"CIV","Abidjan","Rue");

        //Act
        ResponseEntity<AddressDTO> response = restTemplate.postForEntity("/api/addresses",address, AddressDTO.class);

        //Asset
        assertEquals(HttpStatus.CREATED,response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("CIV", response.getBody().getCountry());

        //Veriy in Database
        AddressDTO savedUser =adresseService.findOne(response.getBody().getId()).orElse(null);
        assertNotNull(savedUser);
        assertEquals("CIV",savedUser.getCountry());

    }
}
