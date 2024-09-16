package ci.digitalacademy.monetab.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
//Generation d'une seule table
@DiscriminatorValue(value = "student")
public class Student extends Person {


    @Column(name = "matricule",unique = true,nullable = false)
    private String matricule;

    @Column(name = "clasee")
    private String classe;

    @Column(name = "datenaiss")
    private LocalDate datenaiss;

    @Column(name = "age")
    private Integer age;

    @OneToMany(fetch = FetchType.EAGER, mappedBy ="student")
    private Set<Absence> absences;

    @Column(name = "slug",unique = true)
    private String slug ;

    @OneToOne
    private StudentCards studentCards;

    @OneToOne
    private Person person;

    public LocalDate getDatenaiss() {
        return datenaiss;
    }

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
