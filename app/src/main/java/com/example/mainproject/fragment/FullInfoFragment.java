package com.example.mainproject.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.mainproject.OpenHelper;
import com.example.mainproject.R;
import com.example.mainproject.model.Organization;

public class FullInfoFragment extends Fragment {

    private ImageView photoOrg;
    private TextView name, type, desc, address, needs, link;
    AppCompatButton bt_help;
    ImageView bt_prev;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_info_fragment, container, false);
    }
//В BUNDLE ДОЛЖНО ПРИЙТИ ЗНАЧЕНИЕ ИМЕНИ ОРГАНИЗАЦИИ ПОД КЛЮЧОМ "NameOrg", ФРАГМЕНТ С КЛЮЧОМ "PrevFragment"
// И ИМЯ ЧЕЛОВЕКА С КЛЮЧОМ "LOG"

    @Override
    public void onStart() {
        super.onStart();
        bt_prev = getActivity().findViewById(R.id.bt_fullInfo_arrowBack);
        bt_help = getActivity().findViewById(R.id.bt_fullInfo_help);
        photoOrg = getActivity().findViewById(R.id.iv_fullInfo_photoOrg);
        name = getActivity().findViewById(R.id.tv_fullInfo_nameOrg);
        type = getActivity().findViewById(R.id.tv_fullInfo_typeOrg);
        desc = getActivity().findViewById(R.id.tv_fullInfo_descOrg);
        address = getActivity().findViewById(R.id.tv_fullInfo_addressOrg);
        needs = getActivity().findViewById(R.id.tv_fullInfo_needsOrg);
        link = getActivity().findViewById(R.id.tv_fullInfo_linkToWeb);
        OpenHelper openHelper = new OpenHelper(getContext(), "OpenHelder", null, OpenHelper.VERSION);
        Organization organization = openHelper.findOrgByName(getArguments().getString("NameOrg"));
        Bitmap bitmap = BitmapFactory.
                decodeByteArray(organization.getPhotoOrg(), 0, organization.getPhotoOrg().length);
        RoundedBitmapDrawable roundDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        roundDrawable.setCircular(true);
        photoOrg.setImageDrawable(roundDrawable);
        name.setText(organization.getName());
        String tmp = getColoredSpanned(organization.getType(), "#000000");
        type.setText(Html.fromHtml("Тип:" + "\n" + tmp));
        tmp = getColoredSpanned(organization.getDescription(), "#000000");
        desc.setText(Html.fromHtml("Описание: \n \n" + tmp));
        tmp = getColoredSpanned(organization.getAddress(), "#000000");
        address.setText(Html.fromHtml("Адрес: \n \n" + tmp));
        tmp = getColoredSpanned(organization.getNeeds(), "#000000");
        needs.setText(Html.fromHtml("Потребности: \n \n" +tmp));
        if(organization.getLinkToWebsite() != null) {
            tmp = getColoredSpanned(organization.getLinkToWebsite(), "#000000");
            link.setText(Html.fromHtml("Официальный сайт: \n \n" +tmp));
        }
        else link.setText("Официальный сайт: \n \n" + "(не указан)");
        bt_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("LOG", getArguments().getString("LOG"));
                bundle.putString("NameOrg", getArguments().getString("NameOrg"));
                bt_help.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(FullInfoFragment.this).navigate(
                            R.id.action_fullInfoFragment_to_chatFragment, bundle);
                });
                bt_help.performClick();
            }
        });
        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments().getString("PrevFragment").equals("list")){
                    Bundle bundle = new Bundle();
                    bundle.putString("LOG", getArguments().getString("LOG"));
                    bundle.putString("NameOrg", getArguments().getString("NameOrg"));
                    bt_prev.setOnClickListener((view1) -> {
                        NavHostFragment.
                                findNavController(FullInfoFragment.this).navigate(
                                R.id.action_fullInfoFragment_to_listFragment, bundle);
                    });
                    bt_prev.performClick();
                }
                else if(getArguments().getString("PrevFragment").equals("fav")){
                    Bundle bundle = new Bundle();
                    bundle.putString("LOG", getArguments().getString("LOG"));
                    bundle.putString("NameOrg", getArguments().getString("NameOrg"));
                    bt_prev.setOnClickListener((view1) -> {
                        NavHostFragment.
                                findNavController(FullInfoFragment.this).navigate(
                                R.id.action_fullInfoFragment_to_favouritesFragment, bundle);
                    });
                    bt_prev.performClick();
                }
                else if(getArguments().getString("PrevFragment").equals("map")){
                    Bundle bundle = new Bundle();
                    bundle.putString("LOG", getArguments().getString("LOG"));
                    bundle.putString("NameOrg", getArguments().getString("NameOrg"));
                    bt_prev.setOnClickListener((view1) -> {
                        NavHostFragment.
                                findNavController(FullInfoFragment.this).navigate(
                                R.id.action_fullInfoFragment_to_mapFragment, bundle);
                    });
                    bt_prev.performClick();
                }
                else{
                    Bundle bundle = new Bundle();
                    bundle.putString("LOG", getArguments().getString("LOG"));
                    bundle.putString("NameOrg", getArguments().getString("NameOrg"));
                    bt_prev.setOnClickListener((view1) -> {
                        NavHostFragment.
                                findNavController(FullInfoFragment.this).navigate(
                                R.id.action_fullInfoFragment_to_chatFragment, bundle);
                    });
                    bt_prev.performClick();
                }
            }
        });
    }
    private String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }
}
