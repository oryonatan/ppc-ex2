package il.ac.huji.todolist;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TodoItemAdapter extends ArrayAdapter<String>{

    private int textViewResourceId;
    private Context mContext;
    private Pair<ArrayList<String>,ArrayList<Date>> titleAndDateLists;
    public TodoItemAdapter(Context context, int resource,
                           Pair<ArrayList<String>, ArrayList<Date>> inTitleAndDateLists) {
        super(context, resource, inTitleAndDateLists.first);
        titleAndDateLists = inTitleAndDateLists;
        mContext = context;
        textViewResourceId = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View mView = convertView;
        if(mView == null)
        {
            LayoutInflater vi = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = vi.inflate(textViewResourceId,null);
        }
        TextView itemTitle = (TextView)mView.findViewById(R.id.txtTodoTitle);
        TextView dueDate = (TextView)mView.findViewById(R.id.txtTodoDueDate);
        itemTitle.setText(titleAndDateLists.first.get(position));
        Date itemDate = titleAndDateLists.second.get(position);
        dueDate.setText(
                new SimpleDateFormat("MM/dd/yyyy").format(itemDate));
        checkIfOutdated(itemTitle, dueDate, itemDate);
        return mView;
    }

    /**
     * Check if  a task is outdated , and change it's color if it is
     * @param itemTitle itemTitle
     * @param dueDate textview with dueDate
     * @param itemDate date
     */
    private void checkIfOutdated(TextView itemTitle, TextView dueDate, Date itemDate) {
        // Check if a date is before yesterday midnight (the start of today)
        Calendar yesterdayCal = Calendar.getInstance();
        yesterdayCal.add(Calendar.HOUR, -24);
        Date yesterday = yesterdayCal.getTime();
        if(itemDate.before(yesterday)){
            itemTitle.setTextColor(Color.RED);
            dueDate.setTextColor(Color.RED);
        }
    }

}
