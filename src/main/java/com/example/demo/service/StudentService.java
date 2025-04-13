package com.example.demo.service;


import com.example.demo.dto.StudentDTO;

public interface StudentService {
    //要实现的方法
    StudentDTO getStudentById(long id);//查询id

    Long addNewStudent(StudentDTO studentDTO);

    void deleteStudentById(long id);

    StudentDTO updateStudentById(long id, String name, String email);
}
