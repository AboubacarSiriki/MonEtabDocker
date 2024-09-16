package ci.digitalacademy.monetab.controller;


import ci.digitalacademy.monetab.services.SchoolService;
import ci.digitalacademy.monetab.services.dto.SchoolDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class AuthController {

    private final SchoolService schoolService;

    @GetMapping
    public String login(Authentication authentication, Model model) {
        // Si l'utilisateur est déjà authentifié, redirigez-le vers le tableau de bord
        if (authentication != null && authentication.isAuthenticated()) {
            List<SchoolDTO> schoolDTOS = schoolService.findAll();
            model.addAttribute("schools",schoolDTOS);
            return "redirect:/Dashboard";
        }
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        model.addAttribute("schools",schoolDTOS);
        return "Auth/index";
    }
}
