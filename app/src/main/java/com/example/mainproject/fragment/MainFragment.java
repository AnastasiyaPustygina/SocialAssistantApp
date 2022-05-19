package com.example.mainproject.fragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mainproject.OpenHelper;
import com.example.mainproject.R;
import com.example.mainproject.domain.Person;
import com.example.mainproject.rest.AppApiVolley;

public class MainFragment extends Fragment {

    private TextView tv_age, tv_city, tv_dateOfBirth, tv_data, tv_name, tv_forData;
    private AppCompatButton bt_fav, bt_list, bt_chat, bt_map;
    private ImageView iv_ava;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        bt_map = getActivity().findViewById(R.id.bt_main_map);
        bt_list = getActivity().findViewById(R.id.bt_main_list);
        bt_fav = getActivity().findViewById(R.id.bt_main_fav);
        iv_ava = getActivity().findViewById(R.id.iv_avaForFirstTime);
        tv_name = getActivity().findViewById(R.id.tv_main_name);
        tv_data = getActivity().findViewById(R.id.tv_main_data);
        tv_dateOfBirth = getActivity().findViewById(R.id.tv_main_dateOfBirth);
        tv_age = getActivity().findViewById(R.id.tv_main_age);
        tv_city = getActivity().findViewById(R.id.tv_main_city);
        tv_forData = getActivity().findViewById(R.id.tv_main_forData);
        String nameVal = getArguments().getString("LOG");
        tv_name.setText(nameVal);
        bt_chat = getActivity().findViewById(R.id.bt_main_chat);


        OpenHelper openHelper = new OpenHelper(getContext(), "OpenHelder", null, OpenHelper.VERSION);

        Person client = openHelper.findPersonByLogin(nameVal);
        iv_ava.setImageBitmap(BitmapFactory.decodeByteArray(
                client.getPhotoPer(), 0, client.getPhotoPer().length));
        String dataValue;
        if(client.getTelephone() == null){
            tv_forData.setText("Адрес электронной почты: ");
            dataValue = client.getEmail();
        } else{
            tv_forData.setText("Номер телефона : ");
            dataValue = client.getTelephone();
        }
        tv_data.setText(dataValue);
        tv_dateOfBirth.setText(client.getDateOfBirth());
        tv_age.setText(String.valueOf(client.getAge()));
        tv_city.setText(client.getCity());
        Bundle bundleLog = new Bundle();
        bundleLog.putString("LOG", getArguments().getString("LOG"));
        bt_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_fav.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(MainFragment.this).navigate(
                            R.id.action_mainFragment_to_favouritesFragment, bundleLog);
                });
                bt_fav.performClick();
            }
        });
                bt_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_list.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(MainFragment.this).navigate(
                            R.id.action_mainFragment_to_listFragment, bundleLog);
                });
                bt_list.performClick();
            }
        });
        bt_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_chat.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(MainFragment.this).navigate(
                            R.id.action_mainFragment_to_listOfChatsFragment, bundleLog);
                });
                bt_chat.performClick();
            }
        });
        bt_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_map.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(MainFragment.this).navigate(
                            R.id.action_mainFragment_to_mapFragment, bundleLog);
                });
                bt_map.performClick();
            }
        });

    }
}


