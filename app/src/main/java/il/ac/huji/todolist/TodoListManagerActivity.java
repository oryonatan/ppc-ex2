package il.ac.huji.todolist;

import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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
        Resources res = getResources();
        ListView lstTodo = (ListView)findViewById(R.id.lstTodoItems);
        todoAdapdter = new RedBlueAdapter(this,R.layout.red_blue_list,itemsInList);
        lstTodo.setAdapter(todoAdapdter);
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
