package il.ac.huji.todolist;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;


public class TodoListManagerActivity extends ActionBarActivity {
    private final int NEW_ITEM_ADDED = 1;

    private TodoItemAdapter todoAdapter;
    //The list is a pair if titles and dates.
    private Pair<ArrayList<String>,ArrayList<Date>> itemsToDo =
            new Pair<>(new ArrayList<String>(),new ArrayList<Date>());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        ListView lstTodo = (ListView)findViewById(R.id.lstTodoItems);
        todoAdapter = new TodoItemAdapter(this,R.layout.todo_list, itemsToDo);
        lstTodo.setAdapter(todoAdapter);

        lstTodo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showLongClickDialog(position);
                return false;
            }
        });
    }

    public void showLongClickDialog(final int position) {
        final Dialog dialog = new Dialog(this);
        String title=  itemsToDo.first.get(position);
        dialog.setTitle(title);
        LayoutInflater inflater = this.getLayoutInflater();
        View bodyView = inflater.inflate(R.layout.longclick_dialog, null);
        registerDeletButton(position, dialog, bodyView);
        addCallButton(title, bodyView);
        dialog.setContentView(bodyView);
        dialog.show();
    }

    /**
     * Check if an item starts with "Call " and if it does - call the number that appears next to it
     * @param title  title
     * @param bodyView view containing the title
     */
    private void addCallButton(String title, View bodyView) {
        View callButton = bodyView.findViewById(R.id.menuItemCall);
        final String phoneNumber  = title.substring("Call ".length());
        if (title.startsWith("Call ")){
            callButton.setVisibility(View.VISIBLE);
            callButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent dial = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel:"+phoneNumber));
                    startActivity(dial);
                }
            });
        }
    }

    /**
     * Initializes the delete button
     * @param position position in the list of the delete button
     * @param dialog the dialog with the delete button
     * @param bodyView the bodyView with the delete button
     */
    private void registerDeletButton(final int position, final Dialog dialog, View bodyView) {
        bodyView.findViewById(R.id.menuItemDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsToDo.first.remove(position);
                itemsToDo.second.remove(position);
                todoAdapter.notifyDataSetChanged();
                dialog.hide();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list_manager, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menuItemAdd) {
            Intent itemAddIntent = new Intent(this,AddNewTodoItemActivity.class);
            startActivityForResult(itemAddIntent, NEW_ITEM_ADDED);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case NEW_ITEM_ADDED:
                if (data.getBooleanExtra("OK",false)){
                    addNewItemToListView(
                            data.getStringExtra("title"),
                            (Date)data.getSerializableExtra("dueDate"));
                    }
                break;
        }
    }

    /**
     * Adds a new item to a list view
     * @param title title
     * @param taskDate taskDate
     */
    public void addNewItemToListView(String title, Date taskDate){
        itemsToDo.first.add(title);
        itemsToDo.second.add(taskDate);
        todoAdapter.notifyDataSetChanged();
    }
}
