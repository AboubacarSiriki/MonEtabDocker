package ci.digitalacademy.monetab.models;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "absence")
public class Absence implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "absence_date")
    private Date absence_date;

    @Column(name = "absence_number")
    private int absence_number;

    @Column(name = "slug",unique = true)
    private String slug ;

    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinColumn(name ="student_id")
    private Student student;

}
