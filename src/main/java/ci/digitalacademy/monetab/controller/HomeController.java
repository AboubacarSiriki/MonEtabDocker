package ci.digitalacademy.monetab.controller;

import ci.digitalacademy.monetab.models.AppSetting;
import ci.digitalacademy.monetab.services.AppSettingService;
import ci.digitalacademy.monetab.services.SchoolService;
import ci.digitalacademy.monetab.services.dto.AppSettingDTO;
import ci.digitalacademy.monetab.services.dto.SchoolDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Dashboard")
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final SchoolService schoolService;
    private final AppSettingService appSettingService;

    // Tableau de bord
    @GetMapping
    public String showDashboard(Model model) {
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        List<AppSettingDTO> appSettings = appSettingService.findAll();

        model.addAttribute("schools", schoolDTOS);
        model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));

        return "/Home/dashboard";
    }

    // Affichage des paramètres de l'application
    @GetMapping("/smtpSetting")
    public String showAppSettingPage(Model model) {
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        AppSettingDTO appSettingDTO = appSettingService.findAll().stream().findFirst().orElse(new AppSettingDTO());

        model.addAttribute("schools", schoolDTOS);
        model.addAttribute("appsetting", appSettingDTO);
        return "AppSetting/appsting";
    }

    // Formulaire de modification des paramètres de l'application
    @GetMapping("/updateAppSetting/{id}")
    public String showModifierAppSetting(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        List<AppSettingDTO> appSettings = appSettingService.findAll();
        Optional<AppSettingDTO> appSettingDTO = appSettingService.findOne(id);

        if (appSettingDTO.isPresent()) {
            model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
            model.addAttribute("schools", schoolDTOS);
            model.addAttribute("appsetting", appSettingDTO.get());
            return "AppSetting/appsting";
        } else {
            redirectAttributes.addFlashAttribute("message", "Paramètres non trouvés.");
            return "redirect:/Dashboard";
        }
    }

    @PostMapping
    public String saveAppSetting(AppSettingDTO appSettingDTO, Model model){
        log.debug("Request to save teacher : {}",appSettingDTO );
        appSettingService.save(appSettingDTO);

        return "redirect:/Dashboard";
    }
}
