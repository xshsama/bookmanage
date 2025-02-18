package com.xsh.util;

import com.xsh.entity.student;
import com.xsh.structure.BPlusTree;
import java.util.Scanner;

public class delete {
  static Scanner scanner = new Scanner(System.in);
    public static void deleteStudent(BPlusTree<Integer, student> bPlusTree) {
        System.out.println("请输入要删除的学生学号:");
                    int number = scanner.nextInt();
                    bPlusTree.delete(number);
                    System.out.println("学生信息已删除");
    }
}
