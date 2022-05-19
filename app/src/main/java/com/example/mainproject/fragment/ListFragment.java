package com.example.mainproject.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.OpenHelper;
import com.example.mainproject.adapter.OrgArrayAdapter;
import com.example.mainproject.R;
import com.example.mainproject.domain.Organization;
import com.example.mainproject.rest.AppApiVolley;

import java.util.ArrayList;

public class ListFragment extends Fragment {
    private AppCompatButton bt_prof, bt_fav;
    private OrgArrayAdapter orgArrayAdapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        bt_prof = getActivity().findViewById(R.id.bt_list_prof);
        bt_fav = getActivity().findViewById(R.id.bt_list_fav);
        OpenHelper openHelper = new OpenHelper(getContext(), "OpenHelder", null, OpenHelper.VERSION);
        ArrayList<Organization> arListOrg = openHelper.findAllOrganizations();
        recyclerView = getActivity().findViewById(R.id.rec_list);
        orgArrayAdapter =
                new OrgArrayAdapter(getContext(), arListOrg, getArguments().getString("LOG"),
                        this);
        recyclerView.setAdapter(orgArrayAdapter);
        Bundle bundleLog = new Bundle();
        bundleLog.putString("LOG", getArguments().getString("LOG"));
        bt_prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_prof.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(ListFragment.this).navigate(
                            R.id.action_listFragment_to_mainFragment, bundleLog);
                });
                bt_prof.performClick();
            }
        });
        bt_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_fav.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(ListFragment.this).navigate(
                            R.id.action_listFragment_to_favouritesFragment, bundleLog);
                });
                bt_fav.performClick();
            }
        });

        AppCompatButton btChat = getActivity().findViewById(R.id.bt_list_chat);
        btChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btChat.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(ListFragment.this).navigate(
                            R.id.action_listFragment_to_listOfChatsFragment, bundleLog);
                });
                btChat.performClick();
            }
        });
        AppCompatButton btMap = getActivity().findViewById(R.id.bt_list_map);
        btMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btMap.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(ListFragment.this).navigate(
                            R.id.action_listFragment_to_mapFragment, bundleLog);
                });
                btMap.performClick();
            }
        });

    }
}