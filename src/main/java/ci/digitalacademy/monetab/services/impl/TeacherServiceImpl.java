package ci.digitalacademy.monetab.services.impl;

import ci.digitalacademy.monetab.models.Teacher;
import ci.digitalacademy.monetab.repositories.TeacherRepository;
import ci.digitalacademy.monetab.services.TeacherService;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import ci.digitalacademy.monetab.services.dto.TeacherDTO;
import ci.digitalacademy.monetab.services.mapper.TeacherMapper;
import ci.digitalacademy.monetab.services.mapping.StudentMapping;
import ci.digitalacademy.monetab.services.mapping.TeacherMapping;
import ci.digitalacademy.monetab.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final TeacherMapper teacherMapper;


    @Override
    public TeacherDTO save(TeacherDTO teacherDTO) {
        log.debug("Resquest to save : {}",teacherDTO);
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        String slug = SlugifyUtils.generated(teacher.getNom().toString());
        teacher.setSlug(slug);
        teacher= teacherRepository.save(teacher);

        return  teacherMapper.toDto(teacher);
    }

    @Override
    public TeacherDTO update(TeacherDTO teacherDTO) {
        Teacher teacher = teacherMapper.toEntity(teacherDTO);
        teacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(teacher);
    }

    @Override
    public TeacherDTO update(TeacherDTO teacherDTO, Long id) {
        teacherDTO.setId(id);
        return update(teacherDTO);
    }

    @Override
    public Optional<TeacherDTO> findOne(Long id) {
        return teacherRepository.findById(id).map(teacher -> {
            return teacherMapper.toDto(teacher);
        });
    }

    @Override
    public List<TeacherDTO> findAll() {
        return teacherRepository.findAll().stream().map(teacher -> {
            return teacherMapper.toDto(teacher);
        }).toList();
    }

    @Override
    public void delecte(Long id) {

        teacherRepository.deleteById(id);
    }

    @Override
    public List<TeacherDTO> findByNomOrMatiereAndGenre(String query, String genre) {
        List<Teacher> teachers = teacherRepository.findByNomOrMatiereAndGenre(query , query , genre);
        return teachers.stream().map(teacher -> teacherMapper.toDto(teacher)).toList();
    }

    @Override
    public TeacherDTO partialUpdate(TeacherDTO teacherDTO, Long id) {
        return teacherRepository.findById(id).map(teacher -> {
            TeacherMapping.partialUpdate(teacher, teacherDTO);
            return teacher;
        }).map(teacherRepository::save).map(teacherMapper::toDto).orElse(null);
    }

}
