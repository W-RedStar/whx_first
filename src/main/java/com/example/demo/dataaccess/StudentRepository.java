package com.example.demo.dataaccess;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository//使用这个注解代表是数据层的代码，把下面的类当成一个springbean，放在spring容器中去管理它的依赖关系
public interface StudentRepository extends JpaRepository<Student,Long> {
    //extends JpaRepository代表继承父类接口，JpaRepository里的方法可以实现我们的业务逻辑
    //<Student,Long>是对应着同一个包下的student类，它的数据类型是long
    List<Student> findByEmail(String email);
}
