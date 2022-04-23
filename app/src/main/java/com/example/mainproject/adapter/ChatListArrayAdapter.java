package com.example.mainproject.adapter;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.OpenHelper;
import com.example.mainproject.R;
import com.example.mainproject.fragment.ListOfChatsFragment;
import com.example.mainproject.model.Organization;

import java.util.ArrayList;

public class ChatListArrayAdapter extends RecyclerView.Adapter<ChatListArrayAdapter.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private ListOfChatsFragment fragment;
    private String name;

    public ChatListArrayAdapter(Context context, ListOfChatsFragment fragment, String name) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragment = fragment;
        this.name = name;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.small_chat_window, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OpenHelper openHelper = new OpenHelper(context, "OpenHelder", null, OpenHelper.VERSION);
        ArrayList<String> arrListLastMsg = openHelper.findLastMsgValuesByPerName(name);
        ArrayList<Integer> arrListChatId = openHelper.findLastChatId();
        ArrayList<Organization> arrayListLastOrg = new ArrayList<>();
        for (int i = 0; i < arrListChatId.size(); i++) {
            try {
                arrayListLastOrg.add(openHelper.findOrgByChatId(arrListChatId.get(i)));
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        try {
            holder.ivOrgAva.setImageBitmap(BitmapFactory.decodeByteArray(arrayListLastOrg.get
                            (arrayListLastOrg.size() - position - 1).getPhotoOrg(), 0,
                    arrayListLastOrg.get(arrayListLastOrg.size() - position - 1).getPhotoOrg().length));
            holder.lastMsg.setText(arrListLastMsg.get(arrListLastMsg.size() - position - 1));
            holder.tvNameOrg.setText(arrayListLastOrg.get(arrayListLastOrg.size() - position - 1).getName());
            Bundle bundle = new Bundle();
            bundle.putString("LOG", name);
            bundle.putString("NameOrg", arrayListLastOrg.get(arrayListLastOrg.size() - position - 1).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.itemView.setOnClickListener((view1) -> {
                        NavHostFragment.
                                findNavController(fragment).navigate(
                                R.id.action_listOfChatsFragment_to_chatFragment, bundle);
                    });
                    holder.itemView.performClick();
                }
            });
        } catch (Exception e){
            Toast.makeText(context, arrListChatId.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        OpenHelper openHelper = new OpenHelper(context, "OpenHelder", null, OpenHelper.VERSION);
        try{
            return openHelper.findLastMsgValuesByPerName(name).size();
        }catch (CursorIndexOutOfBoundsException e){return 0;}
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivOrgAva;
        TextView tvNameOrg, lastMsg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivOrgAva = itemView.findViewById(R.id.iv_SmallChatWin_avaOrg);
            tvNameOrg = itemView.findViewById(R.id.tv_SmallChatWin_nameOfOrg);
            lastMsg = itemView.findViewById(R.id.tv_SmallChatWin_lastMsg);
        }
    }
}
