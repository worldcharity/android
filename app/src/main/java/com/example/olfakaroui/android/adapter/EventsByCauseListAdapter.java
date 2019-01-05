package com.example.olfakaroui.android.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.events.EventDetailActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.Vote;
import com.example.olfakaroui.android.service.InteractionService;
import com.example.olfakaroui.android.utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class EventsByCauseListAdapter extends RecyclerView.Adapter<EventsByCauseListAdapter .ViewHolder>{

        public List<Event> listData;
        private LayoutInflater layoutInflater;
        private Context context;
        User current = new User();
        Pair<Integer,Integer> paire;
        HashMap<Integer,Pair<Integer,Integer>> infos = new HashMap<>();
        Pair<Integer,Integer> fav;
        HashMap<Integer,Pair<Integer,Integer>> favorites = new HashMap<>();

        public EventsByCauseListAdapter(Context context, List<Event> listData) {

            this.listData = listData;
            this.context = context;
            //SessionManager sessionManager = new SessionManager(context);
            //sessionManager.getLogin(current);
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

            current.setId(6);
            current.setLastName("Karoui");
            current.setFirstName("Olfa");

            paire = new Pair<Integer, Integer>(0,-1);
            infos.put(position, paire);
            fav = new Pair<Integer, Integer>(0,-1);
            favorites.put(position, fav);
            holder.eventImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EventDetailActivity.class);
                    intent.putExtra("event", event);
                    intent.putExtra("index", position);
                    ((Activity) context).startActivityForResult(intent, 1);
                    //context.startActivity(intent);
                }
            });

            holder.eventName.setText(event.getName());
            Picasso.get().load(UrlConst.IMAGES+event.getPhotos().get(0).getPhoto()).resize(525, 559).centerCrop().into(holder.eventImage);
            holder.eventMonth.setText(new SimpleDateFormat("MMM").format(event.getStartingDate()));
            holder.eventDate.setText(new SimpleDateFormat("dd-yyyy").format(event.getStartingDate()));
            int index = 0;
            holder.like.setChecked(false);
            //holder.like.setImageResource(R.drawable.ic_like_unselected_24dp);
            //holder.like.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
            while ((index < event.getVotes().size()) && ( infos.get(position).first == 0))
            {
                Vote v = event.getVotes().get(index);
                if(v.getVoted_by().getId() == current.getId())
                {
                        paire = new Pair<Integer, Integer>(1,index);
                        infos.put(position, paire);
                        holder.like.setChecked(true);
                        //holder.like.setImageResource(R.drawable.ic_like_selected_24dp);
                        //holder.like.setColorFilter(context.getResources().getColor(R.color.colorAccent));

                }
                index++;

            }
            index = 0;
            holder.bookmark.setChecked(false);
            //holder.bookmark.setImageResource(R.drawable.ic_bookmark_24dp);
            //holder.bookmark.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
            while ((index < event.getFavBy().size()) && ( favorites.get(position).first == 0))
            {
                User u = event.getFavBy().get(index);
                if(u.getId() == current.getId())
                {
                    fav = new Pair<Integer, Integer>(1,index);
                    favorites.put(position, fav);
                    holder.bookmark.setChecked(true);
                    //holder.bookmark.setImageResource(R.drawable.ic_bookmark_saved_24dp);
                    //holder.bookmark.setColorFilter(context.getResources().getColor(R.color.colorAccent));
                }
                index++;

            }
            holder.like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(infos.get(position).first == 0)
                    {
                        Vote vo = new Vote();
                        vo.setType("upvote");
                        vo.setEvent(event);
                        vo.setVoted_by(current);
                        vo.setState(0);
                        event.getVotes().add(vo);
                        paire = new Pair<Integer, Integer>(1,event.getVotes().size() - 1);
                        infos.put(position, paire);
                        addVote(vo);
                        //holder.like.setChecked(true);
                        //holder.like.setImageResource(R.drawable.ic_like_selected_24dp);
                        //holder.like.setColorFilter(context.getResources().getColor(R.color.colorAccent));

                    }
                    else if(infos.get(position).first == 1)
                    {
                        event.getVotes().remove(infos.get(position).second);
                        removeVote(event.getVotes().get(infos.get(position).second));
                        paire = new Pair<Integer, Integer>(0,-1);
                        infos.put(position, paire);
                        //holder.like.setImageResource(R.drawable.ic_like_unselected_24dp);
                        //holder.like.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
                    }
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
                    if(favorites.get(position).first == 0)
                    {
                        event.getFavBy().add(current);
                        fav = new Pair<Integer, Integer>(1,event.getFavBy().size() - 1);
                        favorites.put(position, fav);
                        addFav(event,current);
                        //holder.bookmark.setImageResource(R.drawable.ic_bookmark_saved_24dp);
                        //holder.bookmark.setColorFilter(context.getResources().getColor(R.color.colorAccent));

                    }
                    else if(favorites.get(position).first == 1)
                    {
                        event.getFavBy().remove(infos.get(position).second);
                        unfav(event,current);
                        fav = new Pair<Integer, Integer>(0,-1);
                        favorites.put(position, fav);
                        //holder.bookmark.setImageResource(R.drawable.ic_bookmark_24dp);
                        //holder.bookmark.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
                    }

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
        CheckBox like,bookmark;
        ImageView eventImage, share;

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
