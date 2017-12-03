package amodi1.cmu.edu.queryevents;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.country;

/**
 * Created by ankur on 11/11/2017.
 */

public class CustomAdapter extends ArrayAdapter<Event> {

    private ArrayList<Event> eventList;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<Event> eventList) {
        super(context, textViewResourceId, eventList);
        this.eventList = new ArrayList<Event>();
        this.eventList.addAll(eventList);
    }

    private class ViewHolder {
        TextView title;
        TextView description;
        TextView address;
        TextView date;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        Log.v("ConvertView", String.valueOf(position));
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_view1, null);

            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.eventName);
            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.date = (TextView) convertView.findViewById(R.id.time);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Event entry = eventList.get(position);
        holder.title.setText(entry.title);
        holder.address.setText(entry.address);
        holder.date.setText(entry.time);

        return convertView;

    }

}
