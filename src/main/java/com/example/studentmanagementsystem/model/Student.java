package com.example.studentmanagementsystem.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Student implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer studentId;
    private String name;
    private String dept;
    private Double cgpa;
    private String section;
    private String rollNo;
    private String email;


    public Student (String rollNo, String name, String dept){
        this.rollNo = rollNo;
        this.name = name;
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId=" + studentId +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", cgpa=" + cgpa +
                ", section='" + section + '\'' +
                ", rollNo='" + rollNo + '\'' +
                '}';
    }
}
