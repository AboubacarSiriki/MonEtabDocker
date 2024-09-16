package ci.digitalacademy.monetab.services;

import ci.digitalacademy.monetab.services.dto.AbsenceDTO;
import ci.digitalacademy.monetab.services.dto.StudentDTO;

import java.util.List;
import java.util.Optional;

public interface AbsenceService {

    AbsenceDTO save(AbsenceDTO absenceDTO);

    AbsenceDTO update(AbsenceDTO absenceDTO);

    Optional<AbsenceDTO> findOne(Long id);

    List<AbsenceDTO> findAll();

    AbsenceDTO update(AbsenceDTO absenceDTO, Long id);

    void delecte(Long id);

    AbsenceDTO saveAbsence(AbsenceDTO absenceDTO);

    AbsenceDTO partialUpdate(AbsenceDTO absenceDTO, Long id);
}
