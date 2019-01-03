package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.events.EventDetailActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.Vote;
import com.example.olfakaroui.android.service.InteractionService;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsAdapter.EventViewHolder> {


    public List<Event> mEvents = new ArrayList<>();
    public List<Event> itemsFiltered = new ArrayList<>();
    private Context context;

    public MyEventsAdapter(List<Event> events, Context context){
        mEvents = events;
        this.context = context;
        itemsFiltered = events;
        //SessionManager sessionManager = new SessionManager(context);
        //sessionManager.getLogin(current);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.myevent_item, viewGroup, false);
        return new EventViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        Event event = mEvents.get(i);
        //eventViewHolder.bind(mEvents.get(i), context);
        Picasso.get().load(UrlConst.IMAGES+event.getPhotos().get(0).getPhoto()).resize(525, 559).centerCrop().into(eventViewHolder.mImageView);
        DateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        eventViewHolder.mDayTextView.setText(dateFormat.format(event.getStartingDate()));
        dateFormat = new SimpleDateFormat("MMM", Locale.getDefault());
        eventViewHolder.mMonthTextView.setText(dateFormat.format(event.getStartingDate()));
        eventViewHolder.mNameTextView.setText(event.getName());
        eventViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   Intent intent = new Intent(context, EventDetailActivity.class);
                    intent.putExtra(EventDetailActivity.EXTRA_EVENT_ID, event);
                    context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }


    static class EventViewHolder extends RecyclerView.ViewHolder{
        private TextView mDayTextView;
        private TextView mMonthTextView;
        private TextView mNameTextView;
        private ImageView mImageView;

        EventViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.my_image_view);
            mDayTextView = itemView.findViewById(R.id.my_event_day_text);
            mMonthTextView = itemView.findViewById(R.id.my_event_month_text);
            mNameTextView = itemView.findViewById(R.id.my_event_name_text);

        }

        void bind(final Event event, final Context context){


        }
    }
}

