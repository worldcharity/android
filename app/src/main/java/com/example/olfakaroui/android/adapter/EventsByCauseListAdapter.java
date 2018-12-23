package com.example.olfakaroui.android.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

public class EventsByCauseListAdapter extends RecyclerView.Adapter<EventsByCauseListAdapter .ViewHolder>{

        public List<Event> listData;
        private LayoutInflater layoutInflater;
        private Context context;

        public EventsByCauseListAdapter(List<Event> listData) {
            this.listData = listData;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.event_item_homepage , parent, false);

            return new ViewHolder(itemView);
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Event event = listData.get(position);
            holder.eventName.setText(event.getName());
            Picasso.get().load(UrlConst.IMAGES+event.getPhotos().get(0).getPhoto()).resize(525, 559).centerCrop().into(holder.eventImage);
            holder.eventMonth.setText(new SimpleDateFormat("MMM").format(event.getStartingDate()));
            holder.eventDate.setText(new SimpleDateFormat("dd-yyyy").format(event.getStartingDate()));

            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

        @Override
        public int getItemCount() {
            return listData.size();
        }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView eventName;
        TextView eventMonth;
        TextView eventDate;
        ImageView eventImage, like, share, bookmark;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            eventImage = itemView.findViewById(R.id.event_photo);
            eventMonth = itemView.findViewById(R.id.event_month);
            eventDate = itemView.findViewById(R.id.event_day);
            share = itemView.findViewById(R.id.share);
            like = itemView.findViewById(R.id.like);
            bookmark = itemView.findViewById(R.id.bookmark);
        }
    }
    }
