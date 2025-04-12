package com.example.demo.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {//对应Java的student，数据类型是long。且继承父类接口

    List<Student> findByEmail(String email);
}
