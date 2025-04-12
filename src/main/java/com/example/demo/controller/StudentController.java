package com.example.demo.controller;

import com.example.demo.Response;

import com.example.demo.dataaccess.Student;
import com.example.demo.dto.StudentDTO;
import com.example.demo.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;//把service层的数据导进来

    @GetMapping("/student/{id}")//查询接口
    public Response<StudentDTO> getStudentById(@PathVariable long id){
        return Response.newSuccess(studentService.getStudentById(id));
    }

    @PostMapping("/student")//增加
    public Response<Long> addNewStudent(@RequestBody StudentDTO studentDTO){
        return Response.newSuccess(studentService.addNewStudent(studentDTO));
    }

    @DeleteMapping("/student/{id}")//删除
    public void deleteStudentById(@PathVariable long id){
        studentService.deleteStudentById(id);
    }

    @PutMapping("/student/{id}")
    public Response<StudentDTO>updateStudentById(@PathVariable long id,@RequestParam(required = false)String name,@RequestParam(required = false)String email){
        return Response.newSuccess(studentService.updateStudentById(id,name,email));
    }
}
