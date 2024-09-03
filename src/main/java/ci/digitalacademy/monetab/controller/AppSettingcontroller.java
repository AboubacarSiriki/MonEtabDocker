package ci.digitalacademy.monetab.controller;

import ci.digitalacademy.monetab.models.AppSetting;
import ci.digitalacademy.monetab.services.AppSettingService;
import ci.digitalacademy.monetab.services.dto.AppSettingDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/AppSetting")
@RequiredArgsConstructor
@Slf4j
public class AppSettingcontroller {

    private final AppSettingService appSettingService;

    @GetMapping
    public String appSetting(Model model){

        model.addAttribute("appsetting", new AppSetting());
        return "AppSetting/welcome";
    }

    @PostMapping
    public String saveAppSetting(AppSettingDTO appSettingDTO){

        log.debug("Request to save teacher : {}",appSettingDTO );
        appSettingService.initApp(appSettingDTO);

        return "redirect:/AddSchool";
    }
}
