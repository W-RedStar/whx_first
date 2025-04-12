package com.example.demo.dataaccess;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity//表示是映射到数据库表的一个对象
@Table(name = "student")
public class Student {
    //属性
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = IDENTITY)//表明id这个数据是自增数据，是由数据库自动生成的
    private long id;

    @Column(name = "name")//把数据库的东西映射到Java中
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}