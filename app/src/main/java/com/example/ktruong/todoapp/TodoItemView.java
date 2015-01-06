package com.example.ktruong.todoapp;


import com.example.ktruong.todoapp.entities.TodoItem;

/**
 * simple to string view for the item
 */
public class TodoItemView {
    private TodoItem todoItem;

    public TodoItemView(TodoItem todoItem) {
        this.todoItem = new TodoItem(todoItem.getId(), todoItem.getBody(), todoItem.getPriority());
    }

    public TodoItem getTodoItem() {
        return todoItem;
    }

    @Override
    public String toString() {
       return todoItem.getBody();
    }
}
