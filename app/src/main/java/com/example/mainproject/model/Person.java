package com.example.mainproject.model;

import java.util.ArrayList;

public class Person {
    private int id;
    private String telephone;
    private String email;
    private String name;
    private int age;
    private String dateOfBirth;
    private String city;
    private String password;
    private ArrayList<String> arr_fav_org = new ArrayList<>();

    public Person(int id, String data, String name,
                  int age, String dateOfBirth, String city, String password) {
        this.id = id;
        if(data.contains("@")) this.email = data;
        else this.telephone = data;
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.password = password;
        this.arr_fav_org = arr_fav_org;
    }

    public Person(String data, String name, int age, String dateOfBirth,
                  String city, String password) {
        if(data.contains("@")) this.email = data;
        else this.telephone = data;
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.password = password;
    }

    public Person(String data, String name, int age, String dateOfBirth,
                  String city, String password, ArrayList<String> arr_fav_org) {
        this.telephone = telephone;
        this.email = email;
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.password = password;
        this.arr_fav_org = arr_fav_org;
    }

    public int getId() {
        return id;
    }
    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getCity() {
        return city;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getArr_fav_org() {
        return arr_fav_org;
    }
    public void changeFavOrg(String nameOfOrg){
        if(arr_fav_org.contains(nameOfOrg)) arr_fav_org.remove(nameOfOrg);
        else arr_fav_org.add(nameOfOrg);
    }


    @Override
    public String toString() {
        return "Person{" +
                "telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", city='" + city + '\'' +
                ", password='" + password + '\'' +
                ", arr_fav_org=" + arr_fav_org +
                '}';
    }
}
