package com.example.mainproject.domain;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.mainproject.fragment.SignInFragment;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

public class Organization {
    private int id;
    private String name;
    private String type;
    private byte[] photoOrg;
    private String description;
    private String address;
    private String needs;
    private String linkToWebsite;

    public Organization(String name, String type,
                        String description, String address,
                        String needs, String linkToWebsite) {
        this.name = name;
        this.type = type;

        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        Log.e("IsPrefNull", (sharedPreferences == null) + "");
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("org_photo" + address, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            try {
                byteArray[i] = Byte.parseByte(stringArrayList.get(i));
            }catch(Exception e){
                byteArray[i] = 0;
            }
        }
        this.photoOrg = byteArray;


        this.description = description;
        this.address = address;
        this.needs = needs;
            this.linkToWebsite = linkToWebsite;
    }

    public Organization(String name, String type,
                        String description, String address, String needs) {
        this.name = name;
        this.type = type;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        Log.e("IsPrefNull", (sharedPreferences == null) + "");
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("org_photo" + address, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];
        for (int i = 0; i < stringArrayList.size(); i++) {
            try {
                byteArray[i] = Byte.parseByte(stringArrayList.get(i));
            }catch(Exception e){
                byteArray[i] = 0;
            }
        }
        this.photoOrg = byteArray;
        this.description = description;
        this.address = address;
        this.needs = needs;
    }

    public Organization(int id, String name, String type, String description,
                        String address, String needs, String linkToWebsite) {
        this.id = id;
        this.name = name;
        this.type = type;
        SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
        Log.e("IsPrefNull", (sharedPreferences == null) + "");
        List<String> stringArrayList = Arrays.asList(
                sharedPreferences.getString("org_photo" + address, "NOT FOUND PREF").split(" "));
        byte[] byteArray = new byte[stringArrayList.size()];

            for (int i = 0; i < stringArrayList.size(); i++) {
                try {
                    byteArray[i] = Byte.parseByte(stringArrayList.get(i));
                }catch(Exception e){
                    byteArray[i] = 0;
                }
            }

        this.photoOrg = byteArray;
        this.description = description;
        this.address = address;
        this.needs = needs;
        this.linkToWebsite = linkToWebsite;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public byte[] getPhotoOrg() {
        return photoOrg;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public String getNeeds() {
        return needs;
    }

    public String getLinkToWebsite() {
        return linkToWebsite;
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", needs='" + needs + '\'' +
                ", linkToWebsite='" + linkToWebsite + '\'' +
                '}';
    }
}
