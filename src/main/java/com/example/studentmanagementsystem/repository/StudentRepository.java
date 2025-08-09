package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByStudentId(Integer studentId);
    Student findByName(String name);
    Student findByRollNo(String rollNo);
    Student findByEmail(String email);
    List<Student> findByDept(String dept);

    boolean existsById(String Id);

    @Query("SELECT s FROM Student s WHERE s.name LIKE %:keyword% OR s.rollNo LIKE %:keyword%")
    List<Student> searchByKeyword(@Param("keyword") String keyword);
}
