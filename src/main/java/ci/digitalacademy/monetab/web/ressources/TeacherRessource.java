package ci.digitalacademy.monetab.web.ressources;


import ci.digitalacademy.monetab.services.TeacherService;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import ci.digitalacademy.monetab.services.dto.TeacherDTO;
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
@RequestMapping("/api/teachers")
public class TeacherRessource {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherDTO> saveTeacher( @RequestBody TeacherDTO teacherDTO){
        log.debug("Rest Request to save {}", teacherDTO);
        return new ResponseEntity<>(teacherService.save(teacherDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public TeacherDTO updateStudent( @RequestBody TeacherDTO teacherDTO, @PathVariable Long id){
        log.debug(" REST Request to update  {}", teacherDTO);
        return teacherService.update(teacherDTO, id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacher(@PathVariable Long id){
        log.debug(" REST Request to get one teacher ");
        Optional<TeacherDTO> teacherDTO = teacherService.findOne(id);
        if (teacherDTO.isPresent()) {
            return new ResponseEntity<>(teacherDTO.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("teacher not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<TeacherDTO> getAllTeacher(){
        log.debug("REST Request to all Teacher ");
        return teacherService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteteacher(@PathVariable Long id){
        log.debug("REST Request to delete teacher  ");
        teacherService.delecte(id);
    }

    @PatchMapping("/{id}")
    public TeacherDTO partialUpdate(@RequestBody TeacherDTO teacherDTO, @PathVariable Long id){
        log.debug("REST request to partial update {} {}", teacherDTO, id);
        return teacherService.partialUpdate(teacherDTO, id);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
        log.debug("REST request to get one by slug {}", slug);
        return null;
    }

}
