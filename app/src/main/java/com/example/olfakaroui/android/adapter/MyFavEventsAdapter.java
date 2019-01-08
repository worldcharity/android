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
import android.widget.CheckBox;
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


public class MyFavEventsAdapter extends RecyclerView.Adapter<MyFavEventsAdapter.EventViewHolder> {


    public List<Event> mEvents;
    public List<Event> itemsFiltered;
    private Context context;
    User current = new User();
    Pair<Integer,Integer> paire;
    HashMap<Integer,Pair<Integer,Integer>> infos = new HashMap<>();
    Pair<Integer,Integer> fav;
    HashMap<Integer,Pair<Integer,Integer>> favorites = new HashMap<>();


    public MyFavEventsAdapter(List<Event> events, Context context){
        this.mEvents = events;
        this.itemsFiltered = events;
        this.context = context;
        //SessionManager sessionManager = new SessionManager(context);
        //sessionManager.getLogin(current);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.more_event_item, viewGroup, false);
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
        current.setId(6);

        paire = new Pair<Integer, Integer>(0,-1);
        infos.put(i, paire);
        fav = new Pair<Integer, Integer>(0,-1);
        favorites.put(i, fav);
        int index = 0;
        //eventViewHolder.like.setImageResource(R.drawable.ic_like_unselected_24dp);
        //eventViewHolder.like.setColorFilter(Color.WHITE);
        eventViewHolder.like.setChecked(false);
        while ((index < event.getVotes().size()) && ( infos.get(i).first == 0))
        {
            Vote v = event.getVotes().get(index);
            if(v.getVoted_by().getId() == current.getId())
            {
                paire = new Pair<Integer, Integer>(1,index);
                infos.put(i, paire);
                eventViewHolder.like.setChecked(true);
                //eventViewHolder.like.setImageResource(R.drawable.ic_like_selected_24dp);
                //eventViewHolder.like.setColorFilter(context.getResources().getColor(R.color.colorAccent));

            }
            index++;

        }


                //eventViewHolder.bookmark.setImageResource(R.drawable.ic_bookmark_saved_24dp);
                //eventViewHolder.bookmark.setColorFilter(context.getResources().getColor(R.color.colorAccent));
        eventViewHolder.bookmark.setChecked(true);

        eventViewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infos.get(i).first == 0)
                {
                    Vote vo = new Vote();
                    vo.setType("upvote");
                    vo.setEvent(event);
                    vo.setVoted_by(current);
                    vo.setState(0);
                    event.getVotes().add(vo);
                    paire = new Pair<Integer, Integer>(1,event.getVotes().size() - 1);
                    infos.put(i, paire);
                    addVote(vo);
                    //eventViewHolder.like.setImageResource(R.drawable.ic_like_selected_24dp);
                    //eventViewHolder.like.setColorFilter(context.getResources().getColor(R.color.colorAccent));

                }
                else if(infos.get(i).first == 1)
                {

                    removeVote(event.getVotes().get(infos.get(i).second));
                    event.getVotes().remove(event.getVotes().get(infos.get(i).second));
                    paire = new Pair<Integer, Integer>(0,-1);
                    infos.put(i, paire);
                    //eventViewHolder.like.setImageResource(R.drawable.ic_like_unselected_24dp);
                    //eventViewHolder.like.setColorFilter(Color.WHITE);
                }
            }
        });
        eventViewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                share.putExtra(Intent.EXTRA_SUBJECT, event.getName());
                share.putExtra(Intent.EXTRA_TEXT, UrlConst.IMAGES+event.getPhotos().get(0));
                context.startActivity(Intent.createChooser(share, "Share link!"));


            }
        });
        eventViewHolder.bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    event.getFavBy().remove(infos.get(i).second);
                    unfav(event,current);
                    mEvents.remove(i);
                    notifyItemRemoved(i);


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
        CheckBox like,bookmark;
        private ImageView mImageView, share;

        EventViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.list_item_more_image_view);
            mDayTextView = itemView.findViewById(R.id.list_item_more_event_day_text);
            mMonthTextView = itemView.findViewById(R.id.list_item_more_event_month_text);
            mNameTextView = itemView.findViewById(R.id.list_item_more_event_name_text);
            share = itemView.findViewById(R.id.share_more);
            like = itemView.findViewById(R.id.like_more);
            bookmark = itemView.findViewById(R.id.bookmark_more);
        }

        void bind(final Event event, final Context context){


        }
    }
    public void addVote(Vote v)
    {

        InteractionService.getInstance().voteEvent(v, new InteractionService.InteractionServiceAddVoteCallBack() {
            @Override
            public void onResponse(int vote) {
                v.setId(vote);
            }

            @Override
            public void onFailure(String error) {
            }

        });
    }

    public void removeVote(Vote v)
    {
        InteractionService.getInstance().unvote(v, new InteractionService.InteractionServiceUnVoteCallBack() {
            @Override
            public void onResponse() {
            }

            @Override
            public void onFailure(String error) {
            }

        });
    }

    public void addFav(Event event, User user)
    {

        InteractionService.getInstance().addToFav(user.getId(), event.getId(), new InteractionService.InteractionServiceFavEventCallBack() {
            @Override
            public void onResponse() {
            }

            @Override
            public void onFailure(String error) {
            }

        });
    }

    public void unfav(Event event, User user)
    {
        InteractionService.getInstance().unfav(user.getId(), event.getId(), new InteractionService.InteractionServiceUnFavEventCallBack() {
            @Override
            public void onResponse() {
            }

            @Override
            public void onFailure(String error) {
            }

        });
    }
}

