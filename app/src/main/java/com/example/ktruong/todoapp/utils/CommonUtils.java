package com.example.ktruong.todoapp.utils;


import android.database.sqlite.SQLiteDatabase;

public final class CommonUtils {
    public static void closeConnection(SQLiteDatabase db) {
       if(db != null) {
           try {
               db.close();
           }catch (Exception e) {
               e.printStackTrace();
           }
       }
    }
}
