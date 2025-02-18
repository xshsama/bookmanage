package com.xsh.util;

import java.util.Scanner;

import com.xsh.entity.student;
import com.xsh.structure.BPlusTree;


public class insert {

    private static Scanner scanner = new Scanner(System.in);
    public static void insertStudent(BPlusTree<Integer, student> bPlusTree) {
        System.out.println("请输入学生学号:");
        int number = scanner.nextInt();
        System.out.println("请输入学生姓名:");
        String name = scanner.next();
        System.out.println("请输入学生性别:");
        String sex = scanner.next();
        System.out.println("请输入学生手机号:");
        int  phoneNumber = scanner.nextInt();
        System.out.println("请输入学生邮箱:");
        String mail = scanner.next();
        // 使用学号作为键插入到B+树中
        bPlusTree.insert(number, new student(number,name, sex, phoneNumber, mail));
    }
}