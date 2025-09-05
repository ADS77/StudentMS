package com.example.studentmanagementsystem.repository;

import com.example.studentmanagementsystem.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findById(Long Id);
    Optional<Student> findByName(String name);
    Optional<Student> findByEmail(String email);
    List<Student> findByDept(String dept);
    List<Student> findByMajor(String major);

    boolean existsById(Long Id);

    @Query("SELECT s FROM Student s " +
            "WHERE s.name LIKE %:keyword% " +
            "OR s.email LIKE %:keyword% " +
            "OR s.dept LIKE %:keyword% " +
            "OR s.major LIKE %:keyword%")
    List<Student> searchByKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT * FROM Student WHERE cgpa = (SELECT MAX(cgpa) FROM Student)", nativeQuery = true)
    List<Student> findTopPerformers();
}
