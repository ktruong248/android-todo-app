package com.example.ktruong.todoapp.repositories;


import com.example.ktruong.todoapp.entities.TodoItem;

import java.util.List;

public interface TodoItemRepository {
    void addItem(TodoItem todoItem);
    List<TodoItem> getAll();
    void save(List<TodoItem> items);
}
