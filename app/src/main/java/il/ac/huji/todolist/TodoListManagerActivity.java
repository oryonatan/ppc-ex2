package il.ac.huji.todolist;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

        lstTodo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showDeleteDialog(parent, view, position, id);
                return false;
            };
        });
    }

    public void showDeleteDialog(AdapterView<?> parent, View view, final int position, long id) {

        Activity activity = (Activity)parent.getContext();
        final Dialog inviteBuilder = new Dialog(activity);
        LayoutInflater inflater = (LayoutInflater)
                activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View bodyView = inflater.inflate(R.layout.delete_dialog,null);
        ((TextView)bodyView.findViewById(R.id.menuItemDeleteTitle)).setText(itemsInList.get(position));
        bodyView.findViewById(R.id.menuItemDelete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsInList.remove(position);
                todoAdapdter.notifyDataSetChanged();
                inviteBuilder.hide();
            }
        });
        inviteBuilder.setContentView(bodyView);
        inviteBuilder.show();
    };

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
