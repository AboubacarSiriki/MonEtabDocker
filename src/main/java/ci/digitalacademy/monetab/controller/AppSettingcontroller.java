package ci.digitalacademy.monetab.controller;

import ci.digitalacademy.monetab.models.AppSetting;
import ci.digitalacademy.monetab.services.AppSettingService;
import ci.digitalacademy.monetab.services.SchoolService;
import ci.digitalacademy.monetab.services.dto.AppSettingDTO;
import ci.digitalacademy.monetab.services.dto.SchoolDTO;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AppSettingcontroller {

    private final AppSettingService appSettingService;
    private final SchoolService schoolService;

    @GetMapping
    public String appSetting(Model model) {
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        List<AppSettingDTO> appSettingDTOS = appSettingService.findAll();

        if (schoolDTOS.isEmpty() || appSettingDTOS.isEmpty()) {
            if (appSettingDTOS.isEmpty()) {
                model.addAttribute("appsetting", new AppSetting());
                return "AppSetting/welcome";  // Rediriger vers l'initialisation de l'application
            } else {
                model.addAttribute("school", new SchoolDTO());
                return "School/addschool";  // Rediriger vers l'ajout de l'école
            }
        }

        // Sinon, tout est configuré, rediriger vers le tableau de bord
        return "redirect:/Dashboard";
    }


    @PostMapping
    public String saveAppSetting(AppSettingDTO appSettingDTO, Model model){
        log.debug("Request to save teacher : {}",appSettingDTO );
        appSettingService.initApp(appSettingDTO);

        return "redirect:/AddSchool";
    }


}
