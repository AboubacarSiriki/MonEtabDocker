package ci.digitalacademy.monetab.controller;

import ci.digitalacademy.monetab.services.*;
import ci.digitalacademy.monetab.services.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/AddSchool")
public class SchoolControlleur {

    private final SchoolService schoolService;
    private final AppSettingService appSettingService;

    private final FiltreStorageService filtreStorageService;

    private final RoleUserService roleUserService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserService userService;


    @GetMapping
    public String addSchool(Model model) {
        model.addAttribute("schools", new RegistrationSchoolDTO());
        return "School/addschool";
    }

    @PostMapping
    public String saveSchool(RegistrationSchoolDTO schoolDTO, Model model) {
        log.debug("Request to save school: {}", schoolDTO);

        try {
            // Téléversez le fichier vers Cloudinary et récupérez l'URL
            String logoUrl = filtreStorageService.upload(schoolDTO.getFile());

            RoleUserDTO role1 = new RoleUserDTO();
            role1.setRole("admin");
            RoleUserDTO role2 = new RoleUserDTO();
            role2.setRole("staff");
            RoleUserDTO role3 = new RoleUserDTO();
            role3.setRole("other");

            List<RoleUserDTO> roleUsersDTO = Arrays.asList(role1, role2, role3);
            roleUsersDTO = roleUserService.initRoles(roleUsersDTO);
            // Définissez l'URL du logo dans le DTO
            schoolDTO.setUrl_logo(logoUrl);

            // Associez l'AppSetting à l'école
            AppSettingDTO appSettingDTO = appSettingService.findAll().get(0);
            schoolDTO.setAppSettingDTO(appSettingDTO);

            // Sauvegardez l'école dans la base de données
            schoolService.save(schoolDTO);
            schoolService.initSchool(schoolDTO);
            Set<RoleUserDTO> roleUserAnge = new HashSet<>();
            roleUserAnge.add(roleUsersDTO.get(0));

            Set<RoleUserDTO> roleUserStaff = new HashSet<>();
            roleUserStaff.add(roleUsersDTO.get(1));

            Set<RoleUserDTO> roleUserOther = new HashSet<>();
            roleUserOther.add(roleUsersDTO.get(2));

            UserDTO ange = new UserDTO();
            ange.setSpeudo("admin");
            String password = passwordEncoder.encode("admin");
            ange.setCreationdate(Instant.now());
            ange.setPassword(password);
            ange.setActive(false);
            ange.setRoleUserDTOS(roleUserAnge);


            UserDTO staff = new UserDTO();
            staff.setSpeudo("user");
            String password1 = passwordEncoder.encode("user");
            staff.setPassword(password1);
            staff.setActive(true);
            staff.setCreationdate(Instant.now());
            staff.setRoleUserDTOS(roleUserStaff);
            List<UserDTO> users = List.of(ange,staff);
            userService.initUser(users);
        } catch (IOException e) {
            log.error("Error uploading file: ", e);
            model.addAttribute("errorMessage", "File upload failed. Please try again.");
            return "School/addschool";
        }

        return "redirect:/login";
    }

    @GetMapping("/schoolSetting")
    public String show(Model model){
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        List<AppSettingDTO> appSettings = appSettingService.findAll();

        model.addAttribute("schools", schoolDTOS);
        model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
        return "School/schoolSetting";
    }

    @GetMapping("/updateSchool/{id}")
    public String showModifierSchool(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        List<AppSettingDTO> appSettings = appSettingService.findAll();
        Optional<SchoolDTO> schoolDTO = schoolService.findOne(id);

        if (schoolDTO.isPresent()) {
            model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
            model.addAttribute("schools", schoolDTO.get());
            return "School/schoolSetting";
        } else {
            redirectAttributes.addFlashAttribute("message", "Paramètres non trouvés.");
            return "redirect:/Dashboard";
        }
    }


}
