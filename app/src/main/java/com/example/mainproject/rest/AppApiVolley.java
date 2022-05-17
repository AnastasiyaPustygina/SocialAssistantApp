package com.example.mainproject.rest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mainproject.MainActivity;
import com.example.mainproject.OpenHelper;
import com.example.mainproject.R;
import com.example.mainproject.domain.Organization;
import com.example.mainproject.domain.Person;
import com.example.mainproject.domain.mapper.OrganizationMapper;
import com.example.mainproject.fragment.ListFragment;
import com.google.protobuf.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class AppApiVolley implements  AppApi {


    public static final String API_TEST = "API_TEST";
    private final Context context;
    public static final String BASE_URL = "http://192.168.1.45:8081";
    private com.android.volley.Response.ErrorListener errorListener;


    public AppApiVolley(Context context) {
        this.context = context;
        errorListener = new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(API_TEST, error.toString());
                error.printStackTrace();
            }
        };
    }
    @Override
    public void fillOrganization() {
        String url = BASE_URL + "/organization";
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        OpenHelper openHelper = new OpenHelper(
                                context, "OpenHelder", null, OpenHelper.VERSION);
                        openHelper.deleteAllOrganization();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Organization organization = OrganizationMapper.
                                        organizationFromJson(jsonObject);
                                Bitmap bitmap;
                                if(organization.getPhotoOrg() == null){
                                    bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_channel);
                                }
                                 else {
                                    bitmap = BitmapFactory.decodeByteArray(
                                            organization.getPhotoOrg(), 0, organization.getPhotoOrg().length);
                                }
                                ArrayList<String> arrListName = new ArrayList<String>();
                                for (int j = 0; j < openHelper.findAllOrganizations().size(); j++) {
                                    arrListName.add(openHelper.findAllOrganizations().get(j).getName());
                                }
                                if(!arrListName.contains(organization.getName())) {
                                    openHelper.insertOrg(new Organization(organization.getName(),
                                            organization.getType(), bitmap,
                                            organization.getDescription(), organization.getAddress(),
                                            organization.getNeeds(), organization.getLinkToWebsite()));
                                    Log.e(API_TEST, organization.getName());
                                }
                                else{
                                    openHelper.changeDescByLog(organization.getName(),
                                            organization.getDescription());
                                    openHelper.changeNeedsByLog(organization.getName(),
                                            organization.getNeeds());
                                }
                            }
                            (new ListFragment()).updateAdapter();
                            Log.d(API_TEST, openHelper.findAllOrganizations().toString());
                        }catch (JSONException e) {
                            Log.e("API_TEST", e.getMessage());
                        }
                    }
                },
                errorListener);
        referenceQueue.add(jsonArrayRequest);
    }

    @Override
    public void addPerson(Person person) {
        String url = BASE_URL + "/person";

        JSONObject params = new JSONObject();
        try {
            params.put("id", person.getId());
            params.put("name", person.getName());
            params.put("telephone", person.getTelephone());
            params.put("email", person.getEmail());
            params.put("city", person.getCity());

            StringBuilder stringBuilder = new StringBuilder();
            Bitmap bitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeByteArray(
                    person.getPhotoPer(), 0, person.getPhotoPer().length),
                    150, 150, false);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            Bitmap.CompressFormat imFor = Bitmap.CompressFormat.PNG;
            bitmap.compress(imFor, 0, stream);

            for (int i = 0; i < stream.toByteArray().length - 1; i++) {
                stringBuilder.append(String.valueOf(stream.toByteArray()[i])).append(" ");
            }
            stringBuilder.append(String.valueOf(
                    stream.toByteArray()[stream.toByteArray().length - 1]));
            params.put("photo", stringBuilder.toString());
            params.put("date_of_birth", person.getDateOfBirth());
            params.put("age", person.getAge());
            bitmap.recycle();
        } catch (JSONException e) {
            Log.e("API_TASK", e.getMessage());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                OpenHelper openHelper = new OpenHelper(
                        context, "OpenHelder", null, OpenHelper.VERSION);
                Toast.makeText(context, person.toString(), Toast.LENGTH_SHORT).show();
                openHelper.insert(person);
                Log.d(API_TEST, response.toString());
            }
        }, errorListener
        );
        RequestQueue referenceQueue = Volley.newRequestQueue(context);
        referenceQueue.add(jsonObjectRequest);
    }
}
