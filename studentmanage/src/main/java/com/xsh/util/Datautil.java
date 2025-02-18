package com.xsh.util;

import com.xsh.entity.student;
import com.xsh.structure.BPlusTree;

import java.io.*;

public class Datautil {

    public static void saveData(BPlusTree<Integer, student> bPlusTree, String filePath) {
      File file = new File(filePath);
        // 确保父目录存在
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(bPlusTree);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BPlusTree<Integer, student> loadData(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (BPlusTree<Integer, student>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new BPlusTree<>(1000);
        }
    }
}