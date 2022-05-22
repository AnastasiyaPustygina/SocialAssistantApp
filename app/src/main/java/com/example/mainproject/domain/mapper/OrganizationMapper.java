package com.example.mainproject.domain.mapper;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mainproject.R;
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
//            List<String> stringArrayList = Arrays.asList(
//                    jsonObject.getString("organizationPhoto").split(" "));
//            byte[] byteArray = new byte[stringArrayList.size()];
//            Bitmap bitmap = null;
//
//            for (int i = 0; i < stringArrayList.size(); i++) {
//                try {
//                    byteArray[i] = Byte.valueOf(stringArrayList.get(i));
//                }catch (Exception e){
//                    bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ava);
//                }
//            }
//            if(bitmap == null) bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

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
