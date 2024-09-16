package ci.digitalacademy.monetab.web.ressources;

import ci.digitalacademy.monetab.services.AbsenceService;
import ci.digitalacademy.monetab.services.StudentService;
import ci.digitalacademy.monetab.services.dto.AbsenceDTO;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import ci.digitalacademy.monetab.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/absences")
@Slf4j
@RequiredArgsConstructor
public class AbsenceRessource {

    private final AbsenceService absenceService;
    private final StudentService studentService;

    @PostMapping("/{studentId}")
    public ResponseEntity<AbsenceDTO> save(@PathVariable Long studentId , @RequestBody AbsenceDTO absenceDTO){
        log.debug("REST request to save absence {}", absenceDTO);
        Optional<StudentDTO> student = studentService.findOne(studentId);
        if (student.isPresent()) {
            absenceDTO.setStudent(student.get()); // Associer le student au sujet
            String slug = SlugifyUtils.generated(String.valueOf(absenceDTO.getAbsence_number()));
            absenceDTO.setSlug(slug);
            AbsenceDTO sujet = absenceService.save(absenceDTO);
            return new ResponseEntity<>(sujet, HttpStatus.CREATED);
        } else {
            return null; // Ce bloc est correct ici
        }
    }

    @PutMapping("/{id}")
    public AbsenceDTO update(@RequestBody AbsenceDTO absenceDTO,@PathVariable Long id ){
        log.debug("REST request to update absence {} {}" , absenceDTO, id);
        return absenceService.update(absenceDTO, id);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id){
        log.debug("REST request to get one {}", id);
        Optional<AbsenceDTO> absenceDTO = absenceService.findOne(id);
        if (absenceDTO.isPresent()){
            return new ResponseEntity<>(absenceDTO.get(), HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Absence not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<AbsenceDTO> getAll(){
        log.debug("REST request to get all");
        return absenceService.findAll();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        log.debug("REST request to delete {}", id);
    }


    @PatchMapping("/{id}")
    public AbsenceDTO partialUpdate(@RequestBody AbsenceDTO absenceDTO, @PathVariable Long id){
        log.debug("REST request to partial update {} {}", absenceDTO, id);
        return absenceService.partialUpdate(absenceDTO, id);
    }



    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> getOneBySlug(@PathVariable String slug){
        log.debug("REST request to get one by slug {}", slug);
        return null;
   }


}