package ci.digitalacademy.monetab.web.ressources;

import ci.digitalacademy.monetab.services.AdresseService;
import ci.digitalacademy.monetab.services.dto.AddressDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/addresses")
public class AddressRessource {

    private final AdresseService adresseService;

    @PostMapping
    public ResponseEntity<AddressDTO> saveAddress(@RequestBody AddressDTO addressDTO){
        log.debug("REST Request to save  {}", addressDTO);
        return new ResponseEntity<>(adresseService.save(addressDTO), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Long id){
        log.debug(" REST Request to get one  ");
        Optional<AddressDTO> addressDTO = adresseService.findOne(id);
        if (addressDTO.isPresent()) {
            return new ResponseEntity<>(addressDTO.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Address not found", HttpStatus.NOT_FOUND);
        }
    }

}
