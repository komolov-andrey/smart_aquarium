package ru.mtuci.smart_aquarium.network;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.google.gson.annotations.SerializedName;
import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.Date;
import java.util.List;

import ru.mtuci.smart_aquarium.R;

public class Event extends AbstractItem<Event, Event.ViewHolder> {
    @SerializedName("dt")
    String datetime;
    String event;


    public String getDatatime() {
        Date date = new Date(Long.parseLong(datetime) * 1000);
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        return df.format("MM.dd HH:mm:ss", date).toString();
    }

    public String getEvent() {
        return event;
    }

    @Override
    public int getType() {
        return R.id.log_id;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_log;
    }

    @Override
    public ViewHolder getViewHolder(@NonNull View v) {
        return new ViewHolder(v);
    }

    protected static class ViewHolder extends FastAdapter.ViewHolder<Event> {

        TextView date;
        TextView description;

        public ViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.datetime);
            description = view.findViewById(R.id.event);
        }

        @Override
        public void bindView(Event item, List<Object> payloads) {
            date.setText(item.getDatatime());
            description.setText(item.getEvent());
        }

        @Override
        public void unbindView(Event item) {
            date.setText(null);
            description.setText(null);
        }
    }
}
