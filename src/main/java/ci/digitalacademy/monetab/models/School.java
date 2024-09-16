package ci.digitalacademy.monetab.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "school")
public class School implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "url_logo",nullable = false)
    private String url_logo;

    @OneToMany(fetch = FetchType.EAGER, mappedBy ="school")
    private Set<User> users;

    @OneToOne
    private AppSetting appSetting;
}
