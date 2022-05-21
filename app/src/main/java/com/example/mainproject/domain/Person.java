package com.example.mainproject.domain;

import android.app.Application;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.example.mainproject.MainActivity;
import com.example.mainproject.R;
import com.example.mainproject.fragment.MainFragment;
import com.example.mainproject.fragment.RegFragment;
import com.example.mainproject.fragment.SignInFragment;
import com.google.rpc.context.AttributeContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Person {
    private int id;
    private String telephone;
    private String email;
    private String name;
    private byte[] photoPer;
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

        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        Log.e("ArrayListOfString", (sharedPreferences == null) + "");
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("per_photo" + name, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            byteArray[i] = Byte.parseByte(stringArrayList.get(i));
        }
        this.photoPer = byteArray;


        Log.e("PERSON_PHOTO", (photoPer == null) + "");
            this.age = age;

        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.password = password;
    }

    public Person(String data, String name, int age, String dateOfBirth,
                  String city, String password) {
        if(data.contains("@")) this.email = data;
        else this.telephone = data;
        this.name = name;
        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        Log.e("ArrayListOfString", (sharedPreferences == null) + "");
        List<String> stringArrayList = Arrays.asList(
                    sharedPreferences.getString("per_photo" + name, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
            for (int i = 0; i < stringArrayList.size(); i++) {
                byteArray[i] = Byte.parseByte(stringArrayList.get(i));
            }
            this.photoPer = byteArray;

        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.password = password;
    }

    public Person(int id, String telephone, String email, String name,
                   int age, String dateOfBirth, String city, String password) {
        this.id = id;
        this.telephone = telephone;
        this.email = email;
        this.name = name;
        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        Log.e("ArrayListOfString", (sharedPreferences == null) + "");
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("per_photo" + name, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            byteArray[i] = Byte.parseByte(stringArrayList.get(i));
        }
        this.photoPer = byteArray;

        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.password = password;
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

    public byte[] getPhotoPer() {
        return photoPer;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", telephone='" + telephone + '\'' +
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
