package ci.digitalacademy.monetab.services.mapping;

import ci.digitalacademy.monetab.models.Absence;
import ci.digitalacademy.monetab.models.Student;
import ci.digitalacademy.monetab.services.dto.AbsenceDTO;
import ci.digitalacademy.monetab.services.dto.StudentDTO;

public final class StudentMapping {

    private StudentMapping(){
    }
    public static void partialUpdate(Student student, StudentDTO studentDTO){
        if (studentDTO.getNom() != null){
            student.setNom(studentDTO.getNom());
        }
        if (studentDTO.getPrenom()!=null){
            student.setPrenom(studentDTO.getPrenom());
        }

    }

}
