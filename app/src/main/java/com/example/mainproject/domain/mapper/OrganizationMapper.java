package com.example.mainproject.domain.mapper;

import android.content.SharedPreferences;
import com.example.mainproject.domain.Organization;
import com.example.mainproject.fragment.SignInFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class OrganizationMapper {
    public static Organization organizationFromJson(JSONObject jsonObject) {
        Organization organization = null;
        try {

                SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("org_photo" + jsonObject.getString("address"),
                        jsonObject.getString("organizationPhoto"));
                editor.commit();

            organization = new Organization(jsonObject.getInt("id"),
                    jsonObject.getString("name"), jsonObject.getString("type"),
                    jsonObject.getString("description"), jsonObject.getString("address"),
                    jsonObject.getString("needs"), jsonObject.getString("linkToWebsite")) ;
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        return organization;
    }
    public static Organization organizationFromChatJson(JSONObject jsonObject) {
        Organization organization = null;
        try {
            organization = organizationFromJson(jsonObject.getJSONObject("organizationDto"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return organization;
    }

}
