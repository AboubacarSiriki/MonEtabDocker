package ci.digitalacademy.monetab.controller;

import ci.digitalacademy.monetab.models.Teacher;
import ci.digitalacademy.monetab.repositories.TeacherRepository;
import ci.digitalacademy.monetab.services.AppSettingService;
import ci.digitalacademy.monetab.services.SchoolService;
import ci.digitalacademy.monetab.services.TeacherService;
import ci.digitalacademy.monetab.services.dto.AppSettingDTO;
import ci.digitalacademy.monetab.services.dto.SchoolDTO;
import ci.digitalacademy.monetab.services.dto.TeacherDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Teachers")
@RequiredArgsConstructor
@Slf4j
public class TeacherControlleur {

    private final TeacherService teacherService;
    private final SchoolService schoolService;
    private final TeacherRepository teacherRepository;
    private final AppSettingService appSettingService;

    @GetMapping
    public String showTeacher(Model model){
        List<AppSettingDTO> appSettings = appSettingService.findAll();
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        List<TeacherDTO> teachers = teacherService.findAll();
        model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
        model.addAttribute("schools",schoolDTOS);
        model.addAttribute("teachers",teachers);

        return "Teacher/list";
    }

    @GetMapping("/show_add_Teacher_form")
    public String showAjouterEleve(Model model){

        log.debug("Request to show add teacher forms");
        List<AppSettingDTO> appSettings = appSettingService.findAll();
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
        model.addAttribute("schools",schoolDTOS);
        model.addAttribute("teachers", new Teacher());
        return "/Teacher/form";
    }

    @PostMapping
    public String saveTeacher(TeacherDTO teacherDTO){

        log.debug("Request to save teacher : {}",teacherDTO );
        teacherService.save(teacherDTO);

        return "redirect:/Teachers";
    }

    @GetMapping("/updateTeacher/{id}")
    public String showModifierEleve(Model model, @PathVariable Long id){

        log.debug("Request to show update teacher forms");
        Optional<TeacherDTO> teacher = teacherService.findOne(id);
        List<AppSettingDTO> appSettings = appSettingService.findAll();
        if (teacher.isPresent()){
            model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
            List<SchoolDTO> schoolDTOS = schoolService.findAll();
            model.addAttribute("schools",schoolDTOS);
            model.addAttribute("teachers" , teacher.get());
            return "Teacher/form";
        } else {
            return "redirect:/Teachers";
        }

    }

    @PostMapping("/deleteTeacher/{id}")
    public String showDeleteTeacher(@PathVariable Long id) {
        log.debug("Request to delete teacher with id: {}", id);
        teacherService.delecte(id);
        return "redirect:/Teachers";
    }

    @GetMapping("/search")
    public String searchTeachers(@RequestParam String query  , @RequestParam String gender, Model model)
    {
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        model.addAttribute("schools", schoolDTOS);
        List<TeacherDTO> teachers = teacherService.findByNomOrMatiereAndGenre(query  , gender);
        model.addAttribute("teachers", teachers);
        model.addAttribute("query", query);
        model.addAttribute("gender", gender);

        return "Teacher/list";
    }

}
