package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.List;
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public String register(Student student) {
        try {
            studentRepository.save(student);
        } catch (Exception e) {
            e.printStackTrace();
            return "Registration Failed";
        }
        return "Registration Successful";
    }

    public List<Student> getStudentByDept(String dept) {
        return studentRepository.findByDept(dept);
    }

    public Student getByEmail(String email) {
        Student response = null;
        if(email != null){
            response = studentRepository.findByEmail(email);
        }
        return response;
    }

    public List<Student> searchByKeyWord(String keyWord) {
        List<Student>response = null;
        if(keyWord != null){
            response = studentRepository.searchByKeyword(keyWord);
        }
      return response;
    }

    public String deleteStudent(String id) {
        String success = "";
        if(id != null && studentRepository.existsById(id)){
            try {
                studentRepository.deleteById(Integer.valueOf(id));
                success = "Student deleted successfully!";
            }catch (Exception e){
                e.printStackTrace();
                success = "Failed to delete Student";
            }
        }
        return success;
    }
}
