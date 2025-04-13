package com.example.demo.service;

import com.example.demo.converter.StudentConverter;
import com.example.demo.dataaccess.Student;
import com.example.demo.dataaccess.StudentRepository;
import com.example.demo.dto.StudentDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;


@Service//方便这个对象被其他对象来注入
public class StudentServiceImpl implements StudentService{//implements StudentService代表对StudentService接口的实现

    @Autowired//表示把StudentRepository注入进来
    private StudentRepository studentRepository;

    @Override
    public StudentDTO getStudentById(long id) {
       Student student=studentRepository.findById(id).orElseThrow(RuntimeException::new);//返回结果或者抛出异常
        //把student对象转换成studentdto对象,使用converter
        return StudentConverter.convertStudent(student);

    }

    @Override
    public Long addNewStudent(StudentDTO studentDTO) {
        List<Student>studentList=studentRepository.findByEmail(studentDTO.getEmail());//添加之前要确保email唯一
        if (!CollectionUtils.isEmpty(studentList)){//如果不为空，说明被占用了，需要抛出异常
            throw  new IllegalStateException("email:"+studentDTO.getEmail()+"has been taken");

        }
        //新增的话需要把StudentDTO转换为数据库的student，在converter添加方法
        Student student=studentRepository.save(StudentConverter.convertStudent(studentDTO));
        return student.getId();
    }

    @Override
    public void deleteStudentById(long id) {
        //首先要判断用户id是否存在
        studentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id:"+id+"不存在"));
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional//是 Spring 框架 中用于管理 数据库事务 的重要注解，它可以确保一组数据库操作要么全部成功执行，要么全部回滚（Rollback），从而保证数据的一致性和完整性。
    public StudentDTO updateStudentById(long id, String name, String email) {
        //首先要判断用户id是否存在
        Student studentInDB=studentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id:"+id+"不存在"));
        if (StringUtils.hasLength(name)&&!studentInDB.getName().equals(name)){
            studentInDB.setName(name);
        }
        if (StringUtils.hasLength(email)&&!studentInDB.getEmail().equals(email)){
            studentInDB.setName(email);
        }
        Student student=studentRepository.save(studentInDB);
        return StudentConverter.convertStudent(student);
    }
}
