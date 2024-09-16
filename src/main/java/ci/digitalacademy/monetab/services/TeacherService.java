package ci.digitalacademy.monetab.services;

import ci.digitalacademy.monetab.models.Teacher;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import ci.digitalacademy.monetab.services.dto.TeacherDTO;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    TeacherDTO save(TeacherDTO teacherDTO);

    TeacherDTO update(TeacherDTO teacherDTO);

    TeacherDTO update(TeacherDTO teacherDTO, Long id);

    Optional<TeacherDTO> findOne(Long id);

    List<TeacherDTO> findAll();

    void delecte(Long id);

    List<TeacherDTO> findByNomOrMatiereAndGenre(String query , String genre);

    TeacherDTO partialUpdate(TeacherDTO teacherDTO, Long id);
}
