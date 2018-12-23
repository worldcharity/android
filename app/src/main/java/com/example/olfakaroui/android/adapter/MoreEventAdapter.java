package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Event;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class MoreEventAdapter extends RecyclerView.Adapter<MoreEventAdapter.EventViewHolder> {


    public List<Event> mEvents;
    private Context mContext;

    public MoreEventAdapter(List<Event> events, Context context){
        mEvents = events;
        mContext = context;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.more_event_item, viewGroup, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder eventViewHolder, int i) {
        eventViewHolder.bind(mEvents.get(i), mContext);
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
            mImageView = itemView.findViewById(R.id.list_item_more_image_view);
            mDayTextView = itemView.findViewById(R.id.list_item_more_event_day_text);
            mMonthTextView = itemView.findViewById(R.id.list_item_more_event_month_text);
            mNameTextView = itemView.findViewById(R.id.list_item_more_event_name_text);
        }

        void bind(final Event event, final Context context){
            Picasso.get().load(UrlConst.IMAGES+event.getPhotos().get(0).getPhoto()).resize(525, 559).centerCrop().into(mImageView);
            DateFormat dateFormat = new SimpleDateFormat("dd", Locale.getDefault());
            mDayTextView.setText(dateFormat.format(event.getStartingDate()));
            dateFormat = new SimpleDateFormat("MMM", Locale.getDefault());
            mMonthTextView.setText(dateFormat.format(event.getStartingDate()));
            mNameTextView.setText(event.getName());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Intent intent = new Intent(context, EventDetailsActivity.class);
                    intent.putExtra(EventDetailsActivity.EXTRA_EVENT_ID, event.getId());
                    context.startActivity(intent);*/
                }
            });
        }
    }
}
