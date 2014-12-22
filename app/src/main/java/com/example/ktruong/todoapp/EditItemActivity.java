package com.example.ktruong.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.ktruong.todoapp.entities.TodoItem;
import com.example.ktruong.todoapp.repositories.TodoItemRepository;
import com.example.ktruong.todoapp.repositories.TodoItemRepositoryImpl;

import static com.example.ktruong.todoapp.utils.StringUtils.isNotEmpty;


public class EditItemActivity extends Activity {

    private int todoItemId;
    private String beforeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // call from MainActivity to detect the intent
        Intent intent = getIntent();
        todoItemId = intent.getIntExtra("todo_item_id", -1);
        beforeText = intent.getStringExtra("todo_item_body");
        System.out.println("onCreate EditItemActivity intent id " + todoItemId + " before text " + beforeText);

        EditText editText = (EditText) findViewById(R.id.editItemText);
        editText.setText(beforeText);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onEditItem(View v) {
        EditText editText = (EditText) findViewById(R.id.editItemText);
        String updatedTextInput = editText.getText().toString();
        if (isNotEmpty(updatedTextInput)) {
            System.out.println("onEditItem id " + todoItemId + " before text " + beforeText + " edited " + updatedTextInput);

            TodoItemRepository todoItemRepository = new TodoItemRepositoryImpl(this);
            TodoItem todoItem = todoItemRepository.findById(todoItemId);
            if(todoItem != null) {
                todoItem.setBody(updatedTextInput);
                todoItemRepository.save(todoItem);
            }else {
                System.out.println("error not found");
            }

            Intent editIntentData = new Intent();
            editIntentData.putExtra("todo_item_id", todoItemId);
            editIntentData.putExtra("todo_item_body_modified", updatedTextInput);

            setResult(MainActivity.EDIT_INTENT_RETURN_CODE, editIntentData); // set result code and bundle data for response
            finish(); // closes the activity, pass data to parent
        }
    }
}
