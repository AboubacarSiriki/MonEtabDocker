package ci.digitalacademy.monetab.controller;


import ci.digitalacademy.monetab.services.*;
import ci.digitalacademy.monetab.services.dto.*;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/Rapport")
@RequiredArgsConstructor
public class RapportController {

    private final SchoolService schoolService;

    private final StudentService studentService;
    private  final TeacherService teacherService;
    private final UserService userService;

    private final AppSettingService appSettingService;

    @GetMapping
    public String showRapport(Model model){

        List<SchoolDTO> schoolDTOS = schoolService.findAll();
        List<AppSettingDTO> appSettings = appSettingService.findAll();

        model.addAttribute("schools", schoolDTOS);
        model.addAttribute("appsettings", appSettings.isEmpty() ? null : appSettings.get(0));
        return "/Rapport/rapport";
    }


    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadRapport(String option, String format) throws Exception {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//        if ("pdf".equalsIgnoreCase(format)) {
//            generatePdf(option, outputStream);
//        } else if ("xlsx".equalsIgnoreCase(format)) {
//            generateExcel(option, outputStream);
//        }
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=rapport." + format);
//        headers.setContentType("pdf".equalsIgnoreCase(format) ? MediaType.APPLICATION_PDF : MediaType.APPLICATION_OCTET_STREAM);
//
//        return ResponseEntity.ok().headers(headers).body(outputStream.toByteArray());
        return null;
    }

    private void generatePdf(String option, ByteArrayOutputStream outputStream) throws Exception {
//        PdfWriter writer = new PdfWriter(outputStream);
//        com.itextpdf.kernel.pdf.PdfDocument pdfDoc = new com.itextpdf.kernel.pdf.PdfDocument(writer);
//        Document document = new Document(pdfDoc);
//
//        if ("students".equalsIgnoreCase(option)) {
//            List<StudentDTO> studentDTOS = studentService.findAll();
//            document.add(new Paragraph("Listes des élèves"));
//            for (StudentDTO student : studentDTOS) {
//                document.add(new Paragraph(student.getNom() + " - " + student.getPrenom() + " - " + student.getTelephone() + " - " + student.getEmail()));
//            }
//        } else if ("teachers".equalsIgnoreCase(option)) {
//            List<TeacherDTO> teacherDTOS = teacherService.findAll();
//            document.add(new Paragraph("Listes des professeurs"));
//            for (TeacherDTO teacher : teacherDTOS) {
//                document.add(new Paragraph(teacher.getNom() + " - " + teacher.getPrenom() + " - " + teacher.getTelephone() + " - " + teacher.getEmail()));
//            }
//        } else if ("users".equalsIgnoreCase(option)) {
//            List<UserDTO> userDTOS = userService.findAll();
//            document.add(new Paragraph("Listes des utilisateurs"));
//            for (UserDTO user : userDTOS) {
//                document.add(new Paragraph(user.getSpeudo() + " - " + user.getCreationdate()));
//            }
//        }
//
//        document.close();
    }


    private void generateExcel(String option, ByteArrayOutputStream outputStream) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(option);

        Row headerRow = sheet.createRow(0);
        if ("students".equalsIgnoreCase(option)) {
            List<StudentDTO> studentDTOS = studentService.findAll();
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Email");

            int rowIdx = 1;
            for (StudentDTO student : studentDTOS) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(student.getNom());
                row.createCell(1).setCellValue(student.getEmail());
            }
        } else if ("teachers".equalsIgnoreCase(option)) {
            List<TeacherDTO> teacherDTOS = teacherService.findAll();
            headerRow.createCell(0).setCellValue("Name");
            headerRow.createCell(1).setCellValue("Email");

            int rowIdx = 1;
            for (TeacherDTO teacher : teacherDTOS) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(teacher.getNom());
                row.createCell(1).setCellValue(teacher.getEmail());
            }
        } else if ("users".equalsIgnoreCase(option)) {
            List<UserDTO> userDTOS = userService.findAll();
            headerRow.createCell(0).setCellValue("Username");
            headerRow.createCell(1).setCellValue("Email");

            int rowIdx = 1;
            for (UserDTO user : userDTOS) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(user.getSpeudo());
                row.createCell(1).setCellValue(Date.from(user.getCreationdate()));
            }
        }

        workbook.write(outputStream);
        workbook.close();
    }
}
