package ci.digitalacademy.monetab.services.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class SchoolDTO {

    private Long id;

    private String name;

    private String url_logo;

    // Champ pour stocker le fichier téléversé
    private MultipartFile file;

    private AppSettingDTO appSettingDTO;
}
