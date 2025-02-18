package com.xsh.entity;


public class student implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    private int number;
    private String name;
    private String sex;
    private int phoneNumber;
    private String mail;

    public student(int number, String name, String sex, int phoneNumber, String mail) {
        this.number = number;
        this.name = name;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    @Override
    public String toString() {
        return "student{" +
                "number=" + number +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", mail='" + mail + '\'' +
                '}';
    }       
}
