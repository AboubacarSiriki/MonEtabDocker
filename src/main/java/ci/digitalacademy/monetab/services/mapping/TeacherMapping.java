package ci.digitalacademy.monetab.services.mapping;

import ci.digitalacademy.monetab.models.Student;
import ci.digitalacademy.monetab.models.Teacher;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import ci.digitalacademy.monetab.services.dto.TeacherDTO;

public final class TeacherMapping {

    private TeacherMapping(){

    }

    public static void partialUpdate(Teacher teacher, TeacherDTO teacherDTO){
        if (teacherDTO.getNom() != null){
            teacher.setNom(teacherDTO.getNom());
        }
        if (teacherDTO.getPrenom()!=null){
            teacher.setPrenom(teacherDTO.getPrenom());
        }

    }
}
