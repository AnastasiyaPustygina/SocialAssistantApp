package com.example.mainproject.rest;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mainproject.domain.Chat;
import com.example.mainproject.domain.Message;
import com.example.mainproject.domain.Person;

public interface AppApi {

    void fillOrganization();

    void addPerson(Person person);

    void addChat(Chat chat);

    void fillChats();

    void fillMessageByChatID(int chat_id);

    void addMessage(Message message);

}




