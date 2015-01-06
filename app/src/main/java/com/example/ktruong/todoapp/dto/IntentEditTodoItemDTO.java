package com.example.ktruong.todoapp.dto;

import java.io.Serializable;


public class IntentEditTodoItemDTO implements Serializable {
    private static final long serialVersionUID = 122222222222L;

    private long itemId;
    private String body;

    public IntentEditTodoItemDTO() {
    }

    public IntentEditTodoItemDTO(long itemId, String body) {
        this.itemId = itemId;
        this.body = body;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
