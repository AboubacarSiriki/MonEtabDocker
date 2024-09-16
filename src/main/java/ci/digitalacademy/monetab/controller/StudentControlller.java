package ci.digitalacademy.monetab.controller;

import ci.digitalacademy.monetab.models.Student;
import ci.digitalacademy.monetab.models.Teacher;
import ci.digitalacademy.monetab.services.AppSettingService;
import ci.digitalacademy.monetab.services.SchoolService;
import ci.digitalacademy.monetab.services.StudentService;
import ci.digitalacademy.monetab.services.dto.AppSettingDTO;
import ci.digitalacademy.monetab.services.dto.SchoolDTO;
import ci.digitalacademy.monetab.services.dto.StudentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/Students")
public class StudentControlller {

    private final StudentService studentService;
    private final SchoolService schoolService;
    private final AppSettingService appSettingService;

        @GetMapping
        public String showStudentPage(Model model){
            List<AppSettingDTO> appSettings = appSettingService.findAll();
            model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
            List<SchoolDTO> schoolDTOS = schoolService.findAll();
            List<StudentDTO> students = studentService.findAll();
            model.addAttribute("schools",schoolDTOS);
            model.addAttribute("students",students);
            return "/student/list";
        }

        @GetMapping("/show_add_Student_form")
        public String showaddStudentform(Model model){

            log.debug("Request to show add teacher forms");
            List<AppSettingDTO> appSettings = appSettingService.findAll();
            model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
            List<SchoolDTO> schoolDTOS = schoolService.findAll();
            model.addAttribute("schools",schoolDTOS);
            model.addAttribute("students", new Student());
            return "/student/form";
        }

        //Ajout student
        @PostMapping
        public String saveStudent(StudentDTO studentDTO){

            log.debug("Request to save teacher : {}",studentDTO );
            studentService.save(studentDTO);

            return "redirect:/Students";
        }

        @GetMapping("/updateStudent/{id}")
        public String showModifierEleve(Model model, @PathVariable Long id){
            List<SchoolDTO> schoolDTOS = schoolService.findAll();
            log.debug("Request to show update teacher forms");
            Optional<StudentDTO> student = studentService.findOne(id);
            List<AppSettingDTO> appSettings = appSettingService.findAll();
            if (student.isPresent()){
                model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
                model.addAttribute("schools",schoolDTOS);
                model.addAttribute("students" , student.get());
                return "student/form";
            } else {
                return "redirect:/Students";
            }

        }

    @PostMapping("/deleteStudent/{id}")
    public String showDeleteStudent(@PathVariable Long id) {
        log.debug("Request to delete teacher with id: {}", id);
        studentService.delecte(id);
        return "redirect:/Students";
    }

    @GetMapping("/search")
    public String searchStudents(@RequestParam String query  ,@RequestParam String gender, Model model)
    {
        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        model.addAttribute("schools", schoolDTOS);
        List<StudentDTO> students = studentService.findByNomOrGenreOrMatricule(query , gender);
        model.addAttribute("students", students);
        model.addAttribute("query", query);
        model.addAttribute("gender", gender);

        return "student/list";
    }
}
