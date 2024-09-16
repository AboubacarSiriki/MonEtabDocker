package ci.digitalacademy.monetab.services.dto;

import ci.digitalacademy.monetab.models.Absence;
import ci.digitalacademy.monetab.models.Person;
import ci.digitalacademy.monetab.models.StudentCards;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Getter
@Setter
public class StudentDTO extends PersonnDTO {

    private String matricule;

    private String classe;

    private LocalDate datenaiss;

    private Integer age;

    private String slug ;

    public void setDatenaiss(LocalDate datenaiss) {
        this.datenaiss = datenaiss;
        // Mettre à jour l'âge lorsque la date de naissance est définie
        this.age = calculateAge(datenaiss);
    }

    public Integer getAge() {
        return age;
    }

    private Integer calculateAge(LocalDate datenaiss) {
        if (datenaiss == null) {
            return null;
        }
        return Period.between(datenaiss, LocalDate.now()).getYears();
    }
}
