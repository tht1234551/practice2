package com.tourbest.erp.chat;

import org.springframework.stereotype.Component;

import java.util.Vector;

@Component
public class ChatServer {
    Vector vector_user_list = new Vector();

    public void addUserList(String id) {
        vector_user_list.add(id);
    }

    public Vector getUserList() {
        return vector_user_list;
    }


}
