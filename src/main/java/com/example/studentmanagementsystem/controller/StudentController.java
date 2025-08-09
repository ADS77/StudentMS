package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public String register(@RequestBody Student student){
        String response = "";
        if(student != null){
            response = studentService.register(student);
        }
        return response;
    }


    @GetMapping("/get-all")
    public List<Student> getAllStudents(){
        List<Student> response = studentService.getAllStudents();
        return  response.size() > 0 ? response : new ArrayList<>();
    }

    @GetMapping("/get-by-dept")
    public List<Student> getStudentsByDept(@RequestParam String dept) throws Exception {
        List<Student> response;
        if(dept != null){
            response = studentService.getStudentByDept(dept);
        }
        else{
            throw new Exception("Dept can't be null");
        }
        return response;
    }

    @GetMapping("/get-by-email")
    public Student getMyEmail(@RequestParam String email){
        return studentService.getByEmail(email);
    }

    @GetMapping("/search")
    public List<Student> searchStudent(@RequestParam String keyWord){
        return studentService.searchByKeyWord(keyWord);
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable String id){
        return studentService.deleteStudent(id);
    }



}
