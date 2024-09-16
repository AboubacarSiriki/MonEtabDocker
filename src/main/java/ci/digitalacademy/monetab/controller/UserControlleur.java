package ci.digitalacademy.monetab.controller;

import ci.digitalacademy.monetab.models.Teacher;
import ci.digitalacademy.monetab.models.User;
import ci.digitalacademy.monetab.repositories.UserRepository;
import ci.digitalacademy.monetab.services.AppSettingService;
import ci.digitalacademy.monetab.services.RoleUserService;
import ci.digitalacademy.monetab.services.SchoolService;
import ci.digitalacademy.monetab.services.UserService;
import ci.digitalacademy.monetab.services.dto.AppSettingDTO;
import ci.digitalacademy.monetab.services.dto.SchoolDTO;
import ci.digitalacademy.monetab.services.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Users")
@RequiredArgsConstructor
@Slf4j
public class UserControlleur {

    private final UserService userService;
    private final SchoolService schoolService;
    private final RoleUserService roleUserService;
    private final UserRepository userRepository;
    private final AppSettingService appSettingService;

    @GetMapping
    public String showUserPage(Model model){
        List<AppSettingDTO> appSettings = appSettingService.findAll();
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        List<UserDTO> users = userService.findAll();
        model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
        model.addAttribute("schools",schoolDTOS);
        model.addAttribute("users",users);

        return "/User/list";
    }

    @GetMapping("/show_add_user_form")
    public String showAjouterEleve(Model model){

        log.debug("Request to show add user forms");
        List<AppSettingDTO> appSettings = appSettingService.findAll();
        model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        model.addAttribute("schools",schoolDTOS);
        model.addAttribute("users", new User());
        return "/User/form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute UserDTO userDTO, @RequestParam("confirmPassword") String confirmPassword, Model model) {
        log.debug("Request to save users : {}", userDTO);

        // Vérifier la correspondance des mots de passe
        if (!userDTO.getPassword().equals(confirmPassword)) {
            model.addAttribute("passwordMismatch", true);
            return "/User/form";
        }
        // Définir la date de création
        userDTO.setCreationdate(Instant.now());

        userService.save(userDTO);

        return "redirect:/Users";
    }


    @GetMapping("/updateUser/{id}")
    public String showModifierEleve(Model model, @PathVariable Long id){

        log.debug("Request to show update users forms");
        Optional<UserDTO> user = userService.findOne(id);
        List<AppSettingDTO> appSettings = appSettingService.findAll();
        if (user.isPresent()){
            List<SchoolDTO> schoolDTOS = schoolService.findAll();
            model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
            model.addAttribute("schools",schoolDTOS);
            model.addAttribute("users" , user.get());
            return "User/form";
        } else {
            return "redirect:/Users";
        }

    }

    @PostMapping("/deleteUser/{id}")
    public String showDeleteTeacher(@PathVariable Long id) {
        log.debug("Request to delete user with id: {}", id);
        userService.delecte(id);
        return "redirect:/Users";
    }

    @GetMapping("/search")
    public String searchTeachers(@RequestParam LocalDate date, @RequestParam String role, Model model) {
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        model.addAttribute("schools", schoolDTOS);
        List<UserDTO> users = userService.findByCreationdateLessThanAndRoleUsers(Instant.from(date.atStartOfDay(ZoneOffset.systemDefault())), role);
        model.addAttribute("users", users);
        model.addAttribute("date", date);
        model.addAttribute("role", role);
        model.addAttribute("roles", roleUserService.findAll());

        return "User/list";
    }

    @PostMapping("/updateStatus/{id}")
    public String updateUserStatus(@PathVariable Long id, @RequestParam("status") boolean status, RedirectAttributes redirectAttributes) {
        // Récupérer l'utilisateur par son id
        Optional<UserDTO> userOptional = userService.findOne(id);

        if (userOptional.isPresent()) {
            UserDTO userDTO = userOptional.get();
            userDTO.setActive(status);  // Mettre à jour le statut

            // Sauvegarder les changements
            userService.save(userDTO);
            redirectAttributes.addFlashAttribute("message", "Le statut de l'utilisateur a été mis à jour.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Utilisateur non trouvé.");
        }

        return "redirect:/Users";
    }

    @GetMapping("/Users/deactivate/{id}")
    public String deactivateUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setActive(false);  // Désactiver l'utilisateur
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("message", "L'utilisateur a été désactivé avec succès.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Utilisateur non trouvé.");
        }

        return "redirect:/Users";
    }




}
