package com.example.mainproject.fragment;

import android.database.CursorIndexOutOfBoundsException;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.OpenHelper;
import com.example.mainproject.R;
import com.example.mainproject.adapter.ChatArrayAdapter;
import com.example.mainproject.domain.Message;
import com.example.mainproject.domain.Organization;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ChatFragment extends Fragment {

    private ImageView imOrg, ivMicro, bt_arrow_back;
    private TextView nameOrg;
    private EditText et_msg;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chat_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        bt_arrow_back = getActivity().findViewById(R.id.bt_chat_arrowBack);
        et_msg = getActivity().findViewById(R.id.et_chat_msg);
        imOrg = getActivity().findViewById(R.id.iv_ch_imOrg);
        ivMicro = getActivity().findViewById(R.id.iv_chat_micro);
        nameOrg = getActivity().findViewById(R.id.tv_ch_nameOrg);
        OpenHelper openHelper = new OpenHelper(getContext(), "OpenHelder", null, OpenHelper.VERSION);

        int perId = openHelper.findPersonByLogin(
                getArguments().getString("LOG")).getId();
        Organization org = openHelper.findOrgByName(getArguments().getString("NameOrg"));
        ChatArrayAdapter recyclerAdapter;
        RecyclerView rec = getActivity().findViewById(R.id.rec_chat);
        try {

            recyclerAdapter = new ChatArrayAdapter(getContext(),
                    ChatFragment.this, openHelper.findChatIdByOrgIdAndPerId(org.getId(), perId));
            rec.setAdapter(recyclerAdapter);
            rec.scrollToPosition(openHelper.findMsgByChatId(
                    openHelper.findChatIdByOrgIdAndPerId(org.getId(),perId)).size() - 1);
        }catch (CursorIndexOutOfBoundsException ignored){}
        imOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleForFullDesc = new Bundle();
                bundleForFullDesc.putString("LOG", getArguments().getString("LOG"));
                bundleForFullDesc.putString("NameOrg", getArguments().getString("NameOrg"));
                bundleForFullDesc.putString("PrevFragment", "chat");
                imOrg.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(ChatFragment.this).navigate(
                            R.id.action_chatFragment_to_fullInfoFragment, bundleForFullDesc);
                });
                imOrg.performClick();

            }
        });
        bt_arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundleLog = new Bundle();
                bundleLog.putString("LOG", getArguments().getString("LOG"));
                bt_arrow_back.setOnClickListener((view1) -> {
                    NavHostFragment.
                            findNavController(ChatFragment.this).navigate(
                            R.id.action_chatFragment_to_listOfChatsFragment, bundleLog);
                });
                bt_arrow_back.performClick();
            }
        });

        imOrg.setImageBitmap(BitmapFactory.
                decodeByteArray(org.getPhotoOrg(), 0, org.getPhotoOrg().length));
        nameOrg.setText(org.getName());

        et_msg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ivMicro.setImageDrawable(getResources().getDrawable(R.drawable.bt_send_msg));
                ivMicro.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String curTime = new SimpleDateFormat(
                                "HH:mm", Locale.getDefault()).format(new Date());
                        Message myMsg = new Message("me",
                                openHelper.findChatIdByOrgIdAndPerId(org.getId(), perId), et_msg.getText().toString(),
                                curTime);
                        openHelper.insertMsg(myMsg);
                        ChatArrayAdapter recyclerAdapter1 = new ChatArrayAdapter(getContext(),
                                ChatFragment.this, openHelper.
                                findChatIdByOrgIdAndPerId(org.getId(), perId));
                        rec.setAdapter(recyclerAdapter1);
                        rec.scrollToPosition(openHelper.findMsgByChatId(
                                openHelper.findChatIdByOrgIdAndPerId(org.getId(), perId)).size() - 1);
                        et_msg.setText("");
                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

}
