package ci.digitalacademy.monetab.services.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class RegistrationSchoolDTO  extends SchoolDTO{

     private Long id;
     private String name;

     // Champ pour stocker le fichier téléversé
     private MultipartFile file;

     // Champ pour stocker l'URL du logo après téléversement
     private String url_logo;

     // Champ pour télécharger un nouveau logo
     private MultipartFile file1;

}
