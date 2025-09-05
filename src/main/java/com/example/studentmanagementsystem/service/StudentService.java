package com.example.studentmanagementsystem.service;

import com.example.studentmanagementsystem.model.Student;
import com.example.studentmanagementsystem.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public StudentService(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student){

        return studentRepository.save(student);
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }


    public List<Student> getStudentByDept(String dept) {
        return studentRepository.findByDept(dept);
    }

    public Student getByEmail(String email) {
        Optional<Student> response = null;
        if(email != null){
            response = studentRepository.findByEmail(email);
        }
        return response.isPresent() ? response.get() : null;
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
        if(id != null && studentRepository.existsById(Long.valueOf(id))){
            try {
                studentRepository.deleteById(Long.valueOf(id));
                success = "Student deleted successfully!";
            }catch (Exception e){
                e.printStackTrace();
                success = "Failed to delete Student";
            }
        }
        return success;
    }

    public Student updateStudent(Long id, Student updatedStudent) {
        return studentRepository.findById(id)
                .map(student -> {
                    student.setName(updatedStudent.getName());
                    student.setEmail(updatedStudent.getEmail());
                    student.setMajor(updatedStudent.getMajor());
                    student.setCgpa(updatedStudent.getCgpa());
                    return studentRepository.save(student);
                })
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public List<Student> getStudentsByMajor(String major) {
        return studentRepository.findByMajor(major);
    }

    public List<Student> getTopPerformers(){
        return studentRepository.findTopPerformers();
    }
}
