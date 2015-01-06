package com.example.ktruong.todoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.ktruong.todoapp.dto.IntentEditTodoItemDTO;
import com.example.ktruong.todoapp.entities.TodoItem;
import com.example.ktruong.todoapp.repositories.TodoItemRepository;
import com.example.ktruong.todoapp.repositories.TodoItemRepositoryImpl;

import static com.example.ktruong.todoapp.utils.StringUtils.isNotEmpty;


public class EditItemActivity extends Activity {

    private IntentEditTodoItemDTO intentEditTodoItemDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        Intent intent = getIntent();
        intentEditTodoItemDTO = (IntentEditTodoItemDTO) intent.getSerializableExtra(CommonConstants.INTENT_EDIT_ITEM_KEY);

        EditText editText = (EditText) findViewById(R.id.editItemText);
        String bodyText = intentEditTodoItemDTO.getBody();
        editText.setText(bodyText);
        editText.setSelection(bodyText.length());
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
            long todoItemId = intentEditTodoItemDTO.getItemId();
            String beforeText = intentEditTodoItemDTO.getBody();
            Log.i(getClass().getSimpleName(), "onEditItem id " + todoItemId + " before text " + beforeText + " edited " + updatedTextInput);

            TodoItemRepository todoItemRepository = new TodoItemRepositoryImpl(this);
            TodoItem todoItem = todoItemRepository.findById(todoItemId);
            if(todoItem != null) {
                todoItem.setBody(updatedTextInput);
                todoItemRepository.save(todoItem);
            }else {
                Log.e(getClass().getSimpleName(), "not found the item id " + todoItemId);
            }

            Intent editIntentData = new Intent();
            editIntentData.putExtra(CommonConstants.INTENT_EDIT_ITEM_KEY, new IntentEditTodoItemDTO(todoItemId, updatedTextInput));

            setResult(CommonConstants.EDIT_INTENT_RETURN_CODE, editIntentData); // set result code and bundle data for response
            finish(); // closes the activity, pass data to parent
        }
    }
}
