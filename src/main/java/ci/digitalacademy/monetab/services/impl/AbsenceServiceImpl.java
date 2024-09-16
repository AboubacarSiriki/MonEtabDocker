package ci.digitalacademy.monetab.services.impl;

import ci.digitalacademy.monetab.models.Absence;
import ci.digitalacademy.monetab.repositories.AbsenceRepository;
import ci.digitalacademy.monetab.services.AbsenceService;
import ci.digitalacademy.monetab.services.dto.AbsenceDTO;
import ci.digitalacademy.monetab.services.mapper.AbsenceMapper;
import ci.digitalacademy.monetab.services.mapping.AbsenceMapping;
import ci.digitalacademy.monetab.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AbsenceServiceImpl implements AbsenceService {

    private final AbsenceMapper absenceMapper;
    private final AbsenceRepository absenceRepository;

    @Override
    public AbsenceDTO save(AbsenceDTO absenceDTO) {
        log.debug("Resqurst to save : {}",absenceDTO);
        Absence absence = absenceMapper.toEntity(absenceDTO);
        absence= absenceRepository.save(absence);
        return absenceMapper.toDto(absence);
    }

    @Override
    public AbsenceDTO update(AbsenceDTO absenceDTO) {
        return null;
    }

    @Override
    public Optional<AbsenceDTO> findOne(Long id) {
        return absenceRepository.findById(id).map(absence -> {
            return absenceMapper.toDto(absence);
        });
    }

    @Override
    public List<AbsenceDTO> findAll() {
        return absenceRepository.findAll().stream().map(absence -> {
            return absenceMapper.toDto(absence);
        }).toList();
    }

    @Override
    public AbsenceDTO update(AbsenceDTO absenceDTO, Long id) {
        return null;
    }

    @Override
    public void delecte(Long id) {

    }

    @Override
    public AbsenceDTO saveAbsence(AbsenceDTO absenceDTO) {
        String slug = SlugifyUtils.generated(String.valueOf(absenceDTO.getAbsence_number()));
        absenceDTO.setSlug(slug);
        return save(absenceDTO);
    }

    @Override
    public AbsenceDTO partialUpdate(AbsenceDTO absenceDTO, Long id) {
        return absenceRepository.findById(id).map(absence -> {
            AbsenceMapping.partialUpdate(absence, absenceDTO);
            return absence;
        }).map(absenceRepository::save).map(absenceMapper::toDto).orElse(null);
    }

}
