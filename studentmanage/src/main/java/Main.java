import java.util.Scanner;

import com.xsh.entity.student;
import com.xsh.structure.BPlusTree;
import com.xsh.util.insert;
import com.xsh.util.search;
import com.xsh.util.delete;
import com.xsh.util.Datautil;

public class Main {
    static Scanner scanner = new Scanner(System.in, "UTF-8");
    private static final String
    DATA_FILE_PATH = "../resources/data/students.dat";
    private static BPlusTree<Integer, student> bPlusTree = new BPlusTree<>(1000);

    public static void main(String[] args) {
        bPlusTree = Datautil.loadData(DATA_FILE_PATH);
        while (true) {
            System.out.println("欢迎使用学生信息管理系统");
            System.out.println("请输入序号选择功能");
            System.out.println("1.添加学生信息");
            System.out.println("2.删除学生信息");
            System.out.println("3.修改学生信息");
            System.out.println("4.查询学生信息");
            System.out.println("5.退出");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    insert.insertStudent(bPlusTree);

                }
                case 2 -> {
                    delete.deleteStudent(bPlusTree);

                }
                case 3 -> {
                    System.out.println("请输入要修改的学生学号:");
                    int number = scanner.nextInt();
                    student s = bPlusTree.search(number);
                    if (s != null) {
                        System.out.println("请输入新的学生姓名:");
                        String name = scanner.next();
                        System.out.println("请输入新的学生手机号:");
                        int phonenumber = scanner.nextInt();
                        System.out.println("请输入新的学生性别:");
                        String sex = scanner.next();
                        System.out.println("请输入新的学生邮箱:");
                        String mail = scanner.next();
                        s.setName(name);
                        s.setPhoneNumber(phonenumber);//修改学生年龄
                        s.setSex(sex);
                        s.setMail(mail);
                        bPlusTree.insert(number, s);
                        System.out.println("学生信息已修改");
                    } else {
                        System.out.println("未找到该学生信息");
                    }
                }
                case 4 -> {
                    search.searchStudent(bPlusTree);

                }
                case 5 -> {
                    Datautil.saveData(bPlusTree, DATA_FILE_PATH);
                    System.out.println("已保存");
                    System.out.println("已退出");
                    return;
                }
                default -> {
                    System.out.println("输入错误");
                    continue;
                }
            }
        }
    }
}