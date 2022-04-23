package com.example.mainproject.adapter;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.OpenHelper;
import com.example.mainproject.R;
import com.example.mainproject.fragment.ListFragment;
import com.example.mainproject.model.Chat;
import com.example.mainproject.model.Organization;
import com.example.mainproject.model.Person;

import java.util.List;

public class OrgArrayAdapter extends RecyclerView.Adapter<OrgArrayAdapter.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Organization> arrayOrg;

    private String nameOfPerson;
    private Fragment fragment;
    private View.OnTouchListener onTouchListener;

    public OrgArrayAdapter(Context context, List<Organization> arrayOrg, String nameOfPerson,
                           Fragment fragment) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.arrayOrg = arrayOrg;
        this.nameOfPerson = nameOfPerson;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.short_description, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Organization organization = arrayOrg.get(position);
        OpenHelper openHelper1 = new OpenHelper(context, "OpenHelder", null, OpenHelper.VERSION);
        Person person = openHelper1.findPersonByLogin(nameOfPerson);
        if(openHelper1.findFavOrgByLogin(nameOfPerson).contains(organization.getName()))
            holder.btIdenFav.setBackgroundResource(R.drawable.bt_fav_true);
        else holder.btIdenFav.setBackgroundResource(R.drawable.bt_fav_false);


        holder.nameOrg.setText(organization.getName());
        holder.typeOrg.setText("Тип: " + organization.getType());
        holder.needsOrg.setText("Потребности: " + organization.getNeeds());
        holder.ph.setImageBitmap(BitmapFactory.
                decodeByteArray(organization.getPhotoOrg(), 0, organization.getPhotoOrg().length));
        holder.btIdenFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                person.changeFavOrg(organization.getName());
                if(!openHelper1.findFavOrgByLogin(nameOfPerson).contains(organization.getName()))
                    holder.btIdenFav.setBackgroundResource(R.drawable.bt_fav_true);
                else holder.btIdenFav.setBackgroundResource(R.drawable.bt_fav_false);
                openHelper1.changeFavOrg(nameOfPerson, organization.getName());
            }
        });
        ArrayAdapter<String> adaptDet = new ArrayAdapter<String>(
                context,R.layout.for_spinner, context.getResources().getStringArray(R.array.fullInfo) );
        holder.fullInfo.setAdapter(adaptDet);
        holder.fullInfo.setBackgroundResource(R.drawable.bt_full_info);
        holder.fullInfo.setForeground(context.getDrawable(R.drawable.bt_full_info));
        holder.fullInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
        holder.btHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Chat chat = new Chat(
                        openHelper1.findPersonByLogin(nameOfPerson).getId(), organization.getId());
                try{
                    if (!openHelper1.findAllChats().contains(chat)) openHelper1.insertChat(chat);


                }catch (CursorIndexOutOfBoundsException e){
                    openHelper1.insertChat(chat); }
                Bundle bundleNameOfOrg = new Bundle();
                bundleNameOfOrg.putString("NameOrg", holder.nameOrg.getText().toString());
                bundleNameOfOrg.putString("LOG", nameOfPerson);
                if(fragment.getClass().equals(ListFragment.class)) {
                    holder.btHelp.setOnClickListener((view1) -> {
                        NavHostFragment.
                                findNavController(fragment).navigate(
                                R.id.action_listFragment_to_chatFragment, bundleNameOfOrg);
                    });
                }
                else {
                    holder.btHelp.setOnClickListener((view1) -> {
                        NavHostFragment.
                                findNavController(fragment).navigate(
                                R.id.action_favouritesFragment_to_chatFragment, bundleNameOfOrg);
                    });
                }
                holder.btHelp.performClick();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayOrg.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

         TextView nameOrg, typeOrg, needsOrg;
         ImageView ph;
         AppCompatButton btIdenFav;
         AppCompatButton btHelp;
         Spinner fullInfo;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            btIdenFav = itemView.findViewById(R.id.bt_fav_identifier);
             nameOrg = itemView.findViewById(R.id.shDes_name);
             typeOrg = itemView.findViewById(R.id.shDes_type);
             needsOrg = itemView.findViewById(R.id.shDes_needs);
             ph = itemView.findViewById(R.id.shDes_photoOrg);
             btHelp = itemView.findViewById(R.id.bt_shDes_help);
             fullInfo = itemView.findViewById(R.id.sp_shDes_fullInfo);

        }
    }

}
