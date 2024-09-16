package ci.digitalacademy.monetab.web.ressources;

import ci.digitalacademy.monetab.services.RoleUserService;
import ci.digitalacademy.monetab.services.UserService;
import ci.digitalacademy.monetab.services.dto.AbsenceDTO;
import ci.digitalacademy.monetab.services.dto.RoleUserDTO;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import ci.digitalacademy.monetab.services.dto.UserDTO;
import ci.digitalacademy.monetab.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/roleusers")
public class RoleUserRessource {

    private final RoleUserService roleUserService;
    private final UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<RoleUserDTO> save(@PathVariable Long userId , @RequestBody RoleUserDTO roleUserDTO){
        log.debug("REST request to save roleuser {}", roleUserDTO);
        Optional<UserDTO> user = userService.findOne(userId);
        if (user.isPresent()) {
            roleUserDTO.setUser(user.get()); // Associer le student au sujet
            String slug = SlugifyUtils.generated(String.valueOf(roleUserDTO.getRole()));
            roleUserDTO.setSlug(slug);
            RoleUserDTO sujet = roleUserService.save(roleUserDTO);
            return new ResponseEntity<>(sujet, HttpStatus.CREATED);
        } else {
            return null; // Ce bloc est correct ici
        }
    }

    @PutMapping("/{id}")
    public RoleUserDTO update(@RequestBody RoleUserDTO roleUserDTO,@PathVariable Long id ){
        log.debug("REST request to update roleuser {} {}" , roleUserDTO, id);
        return roleUserService.update(roleUserDTO, id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        log.debug("REST request to get one {}", id);
        Optional<RoleUserDTO> roleUserDTO = roleUserService.findOne(id);
        if (roleUserDTO.isPresent()){
            return new ResponseEntity<>(roleUserDTO.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("roleUser not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<RoleUserDTO> getAll(){
        log.debug("REST request to get all");
        return roleUserService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        log.debug("REST request to delete {}", id);
    }


    @PatchMapping("/{id}")
    public RoleUserDTO partialUpdate(@RequestBody RoleUserDTO roleUserDTO, @PathVariable Long id){
        log.debug("REST request to partial update {} {}", roleUserDTO, id);
        return roleUserService.partialUpdate(roleUserDTO, id);
    }


    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
        log.debug("REST request to get one by slug {}", slug);
        return null;
    }

}
