package com.example.studentmanagementsystem.model;

import jakarta.persistence.*;
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
    private Long Id;
    @Column(name = "first_name", nullable = false)
    private String name;
    @Column(name = "dept")
    private String dept;
    @Column(name = "cgpa")
    private Double cgpa;
    @Column(name = "section")
    private String section;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "major")
    private String major;


    public Student (String name, String dept, String major){
        this.name = name;
        this.dept = dept;
        this.major = major;
    }

    @Override
    public String toString() {
        return "Student{" +
                "Id=" + Id +
                ", name='" + name + '\'' +
                ", dept='" + dept + '\'' +
                ", cgpa=" + cgpa +
                ", section='" + section + '\'' +
                ", email='" + email + '\'' +
                ", major='" + major + '\'' +
                '}';
    }
}
