package ci.digitalacademy.monetab.services.dto;

import ci.digitalacademy.monetab.models.Student;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class AbsenceDTO {

    private Long id;

    private Date absence_date;

    private int absence_number;

    private String slug;

    private StudentDTO student;
}
