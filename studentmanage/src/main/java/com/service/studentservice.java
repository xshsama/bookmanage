package com.service;

import com.xsh.entity.student;
import com.xsh.structure.BPlusTree;

public interface studentservice {

    void insertStudent(BPlusTree<Integer, student> bPlusTree);

    void deleteStudent(int number);

    void updateStudent(student student);

    void searchStudent(int number);

  
}
