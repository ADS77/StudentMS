package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final String UPLOAD_DIR = "uploads/students/";
    private final String REPORTS_DIR = "reports/";
    @Autowired
    private StudentRepository studentRepository;

    public FileService() {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            Files.createDirectories(Paths.get(REPORTS_DIR));
        } catch (IOException e) {
            System.err.println("Error creating directories: " + e.getMessage());
        }
    }

    public String exportStudentsToCSV() throws IOException {
        List<Student> students = studentRepository.findAll();
        String fileName = "students_export_" + System.currentTimeMillis() + ".csv";
        Path filePath = Paths.get(REPORTS_DIR + fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write("ID,Name,Department,CGPA,Section,Email,Major");
            writer.newLine();
            for (Student student : students) {
                String line = String.format("%d,%s,%s,%.2f,%s,%s,%s",
                        student.getId(),
                        student.getName(),
                        student.getDept() != null ? student.getDept() : "",
                        student.getCgpa() != null ? student.getCgpa() : 0.0,
                        student.getSection() != null ? student.getSection() : "",
                        student.getEmail(),
                        student.getMajor() != null ? student.getMajor() : ""
                );
                writer.write(line);
                writer.newLine();
            }
        }
        return fileName;
    }

    public List<Student> importStudentsFromCSV(MultipartFile file) throws IOException {
        List<Student> importedStudents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] fields = line.split(",");
                if (fields.length >= 7) {
                    Student student = new Student();
                    student.setName(fields[1].trim());
                    student.setDept(fields[2].trim());
                    student.setCgpa(!fields[3].trim().isEmpty() ? Double.parseDouble(fields[3].trim()) : null);
                    student.setSection(fields[4].trim());
                    student.setEmail(fields[5].trim());
                    student.setMajor(fields[6].trim());
                    importedStudents.add(student);
                }
            }
        }
        return importedStudents;
    }

    public String generateStudentReport(Long studentId) throws IOException {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            throw new IllegalArgumentException("Student not found");
        }

        Student student = studentOpt.get();
        String fileName = "student_report_" + studentId + "_" + System.currentTimeMillis() + ".txt";
        Path filePath = Paths.get(REPORTS_DIR + fileName);

        StringBuilder report = new StringBuilder();
        report.append("STUDENT REPORT\n");
        report.append("==============\n");
        report.append("ID: ").append(student.getId()).append("\n");
        report.append("Name: ").append(student.getName()).append("\n");
        report.append("Department: ").append(student.getDept() != null ? student.getDept() : "N/A").append("\n");
        report.append("Section: ").append(student.getSection() != null ? student.getSection() : "N/A").append("\n");
        report.append("Email: ").append(student.getEmail()).append("\n");
        report.append("Major: ").append(student.getMajor() != null ? student.getMajor() : "N/A").append("\n");
        report.append("CGPA: ").append(student.getCgpa() != null ? String.format("%.2f", student.getCgpa()) : "N/A").append("\n");
        report.append("Generated on: ").append(new Date()).append("\n");
        Files.write(filePath, report.toString().getBytes(), StandardOpenOption.CREATE);

        return fileName;
    }



}
