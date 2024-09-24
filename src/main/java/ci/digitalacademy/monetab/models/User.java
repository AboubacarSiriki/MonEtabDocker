package ci.digitalacademy.monetab.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",unique = true,nullable = false)
    private String speudo;

    @Column(name ="password",nullable = false)
    private String password;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "create_date",nullable = false)
    private Instant creationdate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy ="user")
    private Set<RoleUser> roleUsers;

    @Column(name = "slug",unique = true)
    private String slug ;

    @ManyToOne(fetch = FetchType.EAGER,  cascade = CascadeType.ALL)
    @JoinColumn(name ="school_id")
    private School school;

}
