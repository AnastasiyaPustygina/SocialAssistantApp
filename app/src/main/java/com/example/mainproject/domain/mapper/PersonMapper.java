package com.example.mainproject.domain.mapper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.mainproject.OpenHelper;
import com.example.mainproject.R;
import com.example.mainproject.domain.Person;
import com.example.mainproject.fragment.ListOfChatsFragment;
import com.example.mainproject.fragment.MainFragment;
import com.example.mainproject.fragment.RegFragment;
import com.example.mainproject.fragment.SignInFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonMapper {
    public static Person personFromJson(JSONObject jsonObject, Context context) {
        Person person = null;
        try {
            OpenHelper openHelper = new OpenHelper(context, "OpenHelder", null, OpenHelper.VERSION);
            String data = jsonObject.getString("telephone").isEmpty() ?
                    jsonObject.getString("email") : jsonObject.getString("telephone");
//            List<String> stringArrayList = Arrays.asList(
//                    jsonObject.getString("photo").split(" "));
//            byte[] byteArray = new byte[stringArrayList.size()];
//            try {
//                for (int i = 0; i < stringArrayList.size(); i++) {
//                    byteArray[i] = Byte.valueOf(stringArrayList.get(i));
//                }
//            }catch (Exception e){
//                Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(),
//                        R.drawable.ava);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                Bitmap.CompressFormat imFor = Bitmap.CompressFormat.JPEG;
//                bitmap1.compress(imFor, 0, stream);
//                byteArray = stream.toByteArray();
//                bitmap1.recycle();
//            }
            SharedPreferences sharedPreferences = SignInFragment.sharedPreferences;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("per_photo" + jsonObject.getString("name"), data);
            editor.commit();
            person = new Person(jsonObject.getInt("id"), data,
                    jsonObject.getString("name"), jsonObject.getInt("age"),
                    jsonObject.getString("date_of_birth"),
                    jsonObject.getString("city"),
                    openHelper.findPassByLogin(jsonObject.getString("name")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }

    public static Person personFromChatJson(JSONObject jsonObject, Context context) {
        Person person = null;
        try {
            person = personFromJson(jsonObject.getJSONObject("personDto"), context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }
}
