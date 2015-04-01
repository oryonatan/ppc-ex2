package il.ac.huji.todolist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


public class AddNewTodoItemActivity extends Activity {
    private final int NEW_ITEM_ADDED = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_new_todo_item);
        final Intent result = new Intent();
        Button btnOK = (Button)findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.putExtra("OK", true)
                        .putExtra("title",
                                ((TextView) findViewById(R.id.edtNewItem)).getText().toString())
                        .putExtra("dueDate",
                                getDateFromDatePicket((DatePicker) findViewById(R.id.datePicker)));
                setResult(NEW_ITEM_ADDED, result);
                finish();
            }
        });

        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.putExtra("OK",false);
                setResult(NEW_ITEM_ADDED,result);
                finish();
            }
        });

    }


    /**
     * Gets the date as long from the a picker
     * @param datePicker datePicker
     * @return long
     */
    public static long getDateFromDatePicket(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Date enteredDate =  calendar.getTime();
        return  enteredDate.getTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_new_todo_item, menu);
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
}
