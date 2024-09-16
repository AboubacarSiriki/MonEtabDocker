package ci.digitalacademy.monetab.web.ressources;

import ci.digitalacademy.monetab.models.User;
import ci.digitalacademy.monetab.repositories.UserRepository;
import ci.digitalacademy.monetab.services.UserService;
import ci.digitalacademy.monetab.services.dto.UserDTO;
import ci.digitalacademy.monetab.services.mapper.UserMapper;
import ci.digitalacademy.monetab.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users")
public class UserRessource {

    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;


    @PostMapping
    public UserDTO save(@RequestBody UserDTO userDTO){
        log.debug("Request to save : {}", userDTO);
        try {
            User user = userMapper.toEntity(userDTO);
            String slug = SlugifyUtils.generated(userDTO.getSpeudo().toString());
            user.setSlug(slug);
            user.setCreationdate(Instant.now()); // Insérer automatiquement la date
            user = userRepository.save(user);
            return userMapper.toDto(user);
        } catch (Exception e) {
            log.error("Error while saving forum: ", e);
            throw new RuntimeException("Failed to save forum", e);
        }
    }
    @PutMapping("/{id}")
    public UserDTO updateuser(@RequestBody UserDTO userDTO, @PathVariable Long id){
        log.debug(" REST Request to update  {}", userDTO);
        return userService.update(userDTO, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id){
        log.debug(" REST Request to get one user ");
        Optional<UserDTO> userDTO = userService.findOne(id);
        if (userDTO.isPresent()) {
            return new ResponseEntity<>(userDTO.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<UserDTO> getAllUser(){
        log.debug("REST Request to all User ");
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteUserr(@PathVariable Long id){
        log.debug("REST Request to delete user  ");
        userService.delecte(id);
    }

    @PatchMapping("/{id}")
    public UserDTO partialUpdate(@RequestBody UserDTO userDTO, @PathVariable Long id){
        log.debug("REST request to partial update {} {}", userDTO, id);
        return userService.partialUpdate(userDTO, id);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
        log.debug("REST request to get one by slug {}", slug);
        return null;
    }
}
