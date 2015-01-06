package com.example.ktruong.todoapp.dto;

import java.io.Serializable;


public class IntentEditTodoItemDTO implements Serializable {
    private int itemId;
    private String body;

    public IntentEditTodoItemDTO() {
    }

    public IntentEditTodoItemDTO(int itemId, String body) {
        this.itemId = itemId;
        this.body = body;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
