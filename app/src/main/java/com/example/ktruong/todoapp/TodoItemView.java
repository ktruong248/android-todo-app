package com.example.ktruong.todoapp;


import com.example.ktruong.todoapp.entities.TodoItem;

/**
 * simple to string view for the item
 */
public class TodoItemView {
    private final TodoItem todoItem;

    public TodoItemView(TodoItem todoItem) {
        this.todoItem = todoItem;
    }

    public TodoItem getTodoItem() {
        return todoItem;
    }

    @Override
    public String toString() {
       return todoItem.getBody();
    }
}
