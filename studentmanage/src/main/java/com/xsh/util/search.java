package com.xsh.util;

import java.util.Scanner;
import com.xsh.structure. BPlusTree;
import com.xsh.entity.student;

public class search {
  static Scanner scanner = new Scanner(System.in);

   public static void searchStudent(BPlusTree<Integer, student> bPlusTree) {
    System.out.println("请输入学生学号:");
    int number = scanner.nextInt();
    student s = bPlusTree.search(number);
    if (s != null) {
        System.out.println(s.toString());
    } else {
        System.out.println("未找到该学生信息");
    }
}
}