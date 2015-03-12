package il.ac.huji.todolist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RedBlueAdapter extends ArrayAdapter<String>{

    private int textViewResourceId;
    private Context mContext;
    private List<String> items;
    public RedBlueAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
        items = objects;
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
        TextView text = (TextView)mView.findViewById(R.id.textView);
        if(items.get(position) != null ) {
            if (position % 2 == 1) {
                text.setTextColor(Color.RED);
            } else {
                text.setTextColor(Color.BLUE);
            }
            text.setText(items.get(position));
        }
        return mView;
    }

}
