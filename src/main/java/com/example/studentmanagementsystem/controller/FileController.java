package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.service.FileService;
import com.example.studentmanagementsystem.service.StudentService;
import com.example.studentmanagementsystem.service.ThreadingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final StudentService studentService;
    private final ThreadingService threadingService;
    private final FileService fileService;

    public FileController(StudentService studentService, ThreadingService threadingService, FileService fileService) {
        this.studentService = studentService;
        this.threadingService = threadingService;
        this.fileService = fileService;
    }

    // Export students to CSV
    @GetMapping("/export/csv")
    public ResponseEntity<?> exportStudentsToCSV() {
        try {
            String fileName = fileService.exportStudentsToCSV();
            return ResponseEntity.ok("Students exported successfully: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to export: " + e.getMessage());
        }
    }

    // Import students from CSV
    @PostMapping("/import/csv")
    public ResponseEntity<?> importStudentsFromCSV(@RequestParam("file") MultipartFile file) {
        try {
            List<Student> students = fileService.importStudentsFromCSV(file);
            return ResponseEntity.ok("Imported " + students.size() + " students from CSV");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to import: " + e.getMessage());
        }
    }

    // Generate student report
    @PostMapping("/report/{studentId}")
    public ResponseEntity<?> generateStudentReport(@PathVariable Long studentId) {
        try {
            String fileName = fileService.generateStudentReport(studentId);
            return ResponseEntity.ok("Report generated: " + fileName);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate report: " + e.getMessage());
        }
    }

}
