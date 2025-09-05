package com.example.studentmanagementsystem.controller;

import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<Student> register(@RequestBody Student student){
        Student response = new Student();
        if(student != null){
            response = studentService.createStudent(student);
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/register-all")
    public ResponseEntity<List<Student>> registerAll(@RequestBody List<Student> students){
        logger.debug("Registering all students");
        List<Student> listResposne = new ArrayList<>();
        if(students.size() > 0){
            for(Student student : students){
                Student response = new Student();
                response = studentService.createStudent(student);
                listResposne.add(response);
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok().body(listResposne);
    }


    @GetMapping("/get-all")
    public List<Student> getAllStudents(){
        List<Student> response = studentService.getAllStudents();
        return  response.size() > 0 ? response : new ArrayList<>();
    }

    @GetMapping("/get-by-dept")
    public ResponseEntity<List<Student> >getStudentsByDept(@RequestParam String dept) throws Exception {
        List<Student> response;
        if(dept != null){
            response = studentService.getStudentByDept(dept);
        }
        else{
            throw new Exception("Dept can't be null");
        }
        return new ResponseEntity<>(response,HttpStatus.OK);
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


    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        List<Student> students = studentService.getStudentsByMajor(major);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/top-performers")
    public ResponseEntity<List<Student>> getTopPerformser() {
        List<Student> students = studentService.getTopPerformers();
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

}
