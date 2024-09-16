package ci.digitalacademy.monetab.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue(value = "teacher")
public class Teacher extends Person {

    @Column(name = "matiere", nullable = false)
    private String matiere;

    @Column(name = "vacant", nullable = false)
    private Boolean vacant;

    @Column(name = "slug",unique = true)
    private String slug ;

    @OneToOne
    private Person person;


//    @OneToMany
//    private Set<FicheNote> ficheNote;

    @OneToMany(fetch = FetchType.EAGER, mappedBy ="teacher")
    private Set<FicheNote> ficheNotes;

}
