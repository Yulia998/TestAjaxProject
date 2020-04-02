package com.mykhailenko.dao;

import com.mykhailenko.entities.Student;

import java.util.List;

public interface DaoConnection {
    void connect();
    void disconnect();
    List<Student> selectStudentsStart(String name);
}
