package ci.digitalacademy.monetab.services.impl;

import ci.digitalacademy.monetab.models.Student;
import ci.digitalacademy.monetab.repositories.StudentRepository;
import ci.digitalacademy.monetab.services.StudentService;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import ci.digitalacademy.monetab.services.mapper.StudentMapper;
import ci.digitalacademy.monetab.services.mapping.StudentMapping;
import ci.digitalacademy.monetab.utils.SlugifyUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private  final StudentMapper studentMapper;


    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        log.debug("Resqurst to save : {}",studentDTO);
        Student student = studentMapper.toEntity(studentDTO);
        String slug = SlugifyUtils.generated(student.getNom().toString());
        student.setSlug(slug);
        student= studentRepository.save(student);

        return  studentMapper.toDto(student);
    }

    @Override
    public StudentDTO update(StudentDTO studentDTO) {
        Student student = studentMapper.toEntity(studentDTO);
        student = studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Override
    public Optional<StudentDTO> findOne(Long id) {
        return studentRepository.findById(id).map(student -> {
            return studentMapper.toDto(student);
        });
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream().map(student -> {
            return studentMapper.toDto(student);
        }).toList();
    }

    @Override
    public StudentDTO update(StudentDTO studentDTO, Long id) {
        studentDTO.setId(id);
        return update(studentDTO);
    }

    @Override
    public void delecte(Long id) {

        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentDTO> findByNomOrGenreOrMatricule(String query, String genre) {
        List<Student> students = studentRepository.findByNomIgnoreCaseOrMatriculeIgnoreCaseAndGenre(query  , query ,genre);
        return students.stream().map(student -> studentMapper.toDto(student)).toList();
    }

    @Override
    public StudentDTO partialUpdate(StudentDTO studentDTO, Long id) {
        return studentRepository.findById(id).map(student -> {
            StudentMapping.partialUpdate(student, studentDTO);
            return student;
        }).map(studentRepository::save).map(studentMapper::toDto).orElse(null);
    }

}

