package com.example.ktruong.todoapp.repositories;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ktruong.todoapp.entities.TodoAppConstants;
import com.example.ktruong.todoapp.entities.TodoItem;
import com.example.ktruong.todoapp.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TodoItemRepositoryImpl extends SQLiteOpenHelper implements TodoItemRepository, TodoAppConstants {

    public TodoItemRepositoryImpl(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(buildCreateTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void addItem(TodoItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            // Open database connection
            // Define values for each field
            ContentValues values = new ContentValues();
            values.put(KEY_BODY, item.getBody());
            values.put(KEY_PRIORITY, item.getPriority());
            db.insert(TODO_TABLENAME, null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeConnection(db);
        }
    }

    @Override
    public List<TodoItem> getAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            Cursor cursor = db.rawQuery(buildSelectAllSql(), null);

            List<TodoItem> todoItems = new ArrayList();
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    todoItems.add(convertTo(cursor));
                } while (cursor.moveToNext());
            }

            return todoItems;
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            CommonUtils.closeConnection(db);
        }

        return new ArrayList<>();
    }

    @Override
    public void save(List<TodoItem> items) {
        if (items != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            try {
                db.beginTransaction();
                for (TodoItem item : items) {
                    if (item.getId() != null) {
                        TodoItem fetched = findById(item.getId());
                        if (fetched != null) {
                            fetched.setBody(item.getBody());
                            fetched.setPriority(item.getPriority());
                            updateTodoItem(fetched, db);
                        } else {
                            // need to create
                            item.setId(null);
                            insert(item, db);
                        }
                    } else {
                        insert(item, db);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                db.endTransaction();
                CommonUtils.closeConnection(db);
            }
        }
    }

    @Override
    public void delete(TodoItem removeItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            deleteTodoItem(removeItem, db);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeConnection(db);
        }
    }

    @Override
    public TodoItem findById(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            System.out.println("finding " + itemId);
            Cursor cursor = db.query(TODO_TABLENAME, new String[]{KEY_ID, KEY_BODY, KEY_PRIORITY},
                    KEY_ID + "= ?", new String[]{String.valueOf(itemId)}, null, null, null, null); // GROUP BY, HAVING, ORDER BY
            if (cursor != null)
                cursor.moveToFirst();

            return convertTo(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeConnection(db);
        }

        return null;
    }

    @Override
    public void save(TodoItem todoItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            updateTodoItem(todoItem, db);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CommonUtils.closeConnection(db);
        }
    }

//    protected String buildSelectByIdSql(int id) {
//        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(KEY_ID).append(",")
//                .append(KEY_BODY).append(",").append(KEY_PRIORITY).append(" FROM ").append(TODO_TABLENAME)
//                .append(" WHERE id = ").append(id);
//
//        return sqlBuilder.toString();
//    }

    /**
     * convert db cursor to POJO expect the row in the following order
     * <p/>
     * row[0] == id field
     * row[1] == body field
     * row[2] == priority field
     *
     * @param cursor
     * @return
     */
    protected TodoItem convertTo(Cursor cursor) {
        int id = cursor.getInt(0);
        String body = cursor.getString(1);
        int priority = cursor.getInt(2);

        return new TodoItem(id, body, priority);
    }


    protected TodoItem insert(TodoItem item, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_PRIORITY, item.getPriority());
        db.insert(TODO_TABLENAME, null, values);
        return item;
    }

    protected int updateTodoItem(TodoItem item, SQLiteDatabase db) {

        // Setup fields to update
        ContentValues values = new ContentValues();
        values.put(KEY_BODY, item.getBody());
        values.put(KEY_PRIORITY, item.getPriority());
        // Updating row
        return db.update(TODO_TABLENAME, values, KEY_ID + " = ?", new String[]{String.valueOf(item.getId())});
    }

    protected void deleteTodoItem(TodoItem item, SQLiteDatabase db) {
        db.delete(TODO_TABLENAME, KEY_ID + " = ?", new String[]{String.valueOf(item.getId())});
    }

    protected String buildSelectAllSql() {
        StringBuilder sqlBuilder = new StringBuilder("SELECT ").append(KEY_ID).append(",")
                .append(KEY_BODY).append(",").append(KEY_PRIORITY).append(" FROM ").append(TODO_TABLENAME);
        return sqlBuilder.toString();
    }

    protected String buildCreateTableSql() {
        StringBuilder createTableSqlBuilder = new StringBuilder("CREATE TABLE ");
        createTableSqlBuilder.append(TODO_TABLENAME).append("(").append(KEY_ID);
        createTableSqlBuilder.append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
        createTableSqlBuilder.append(KEY_BODY).append(" TEXT,");
        createTableSqlBuilder.append(KEY_PRIORITY).append(" INTEGER )");

        return createTableSqlBuilder.toString();
    }

}
