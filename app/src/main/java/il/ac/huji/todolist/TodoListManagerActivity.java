package il.ac.huji.todolist;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class TodoListManagerActivity extends ActionBarActivity {
    private RedBlueAdapter todoAdapdter;
    private ArrayList<String> itemsInList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list_manager);
        ListView lstTodo = (ListView)findViewById(R.id.lstTodoItems);
        todoAdapdter = new RedBlueAdapter(this,R.layout.red_blue_list,itemsInList);
        lstTodo.setAdapter(todoAdapdter);

        lstTodo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(position);
                return false;
            }
        });
    }

    public void showDeleteDialog(final int position) {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle(itemsInList.get(position));
        LayoutInflater inflater = this.getLayoutInflater();
        View bodyView = inflater.inflate(R.layout.delete_dialog, null);
        bodyView.findViewById(R.id.menuItemDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsInList.remove(position);
                todoAdapdter.notifyDataSetChanged();
                dialog.hide();
            }
        });
        dialog.setContentView(bodyView);
        dialog.show();
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
            addNewItemToListView();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewItemToListView(){
        String newItem = ((EditText)findViewById(R.id.edtNewItem)).getText().toString();
        itemsInList.add(newItem);
        todoAdapdter.notifyDataSetChanged();
    }
}
