package com.example.olfakaroui.android.adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.UI.users.UserProfileActivity;
import com.example.olfakaroui.android.entity.Comment;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.UserInfos;
import com.example.olfakaroui.android.entity.Vote;
import com.example.olfakaroui.android.service.InteractionService;
import com.example.olfakaroui.android.service.UserService;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;

public class CommentListForCharityAdapter extends RecyclerView.Adapter<CommentListForCharityAdapter.ViewHolder> {

    public List<Comment> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    int newid = 0;
    User user = new User();
    Pair<Integer,Integer> paire;
    HashMap<Integer,Pair<Integer,Integer>> infos = new HashMap<>();
    HashMap<Integer,Integer> votes = new HashMap<>();
    public CommentListForCharityAdapter(List<Comment> listData, Context context) {
        this.listData = listData;
        this.context = context;
        SessionManager sessionManager = new SessionManager(context);
        sessionManager.getLogin(user);
        //SessionManager sessionManager = new SessionManager(context);
        //sessionManager.getLogin(user);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_for_charity_item, parent, false);

        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){

        paire = new Pair<Integer, Integer>(0,-1);
        infos.put(position, paire);


        Comment comment = listData.get(position);
        holder.body.setText(comment.getBody());
        holder.username.setText(comment.getPosted_by().getFirstName() + " " + comment.getPosted_by().getLastName() );

        UserService.getInstance().getInfos(comment.getPosted_by().getId(), new UserService.UserServiceGetInfosCallBack() {
            @Override
            public void onResponse(UserInfos u) {
                holder.rating.setRating((u.getRating()/100));
            }

            @Override
            public void onFailure(String error) {

            }

        });
        int vote = 0;
//        holder.rating.setRating(comment.getPosted_by().getRating());
        int i = 0;
        for(Vote v : comment.getVotes())
        {
            if(v.getType().equals("upvote"))
            {
                vote++;
            }
            else if(v.getType().equals("downvote"))
            {
                vote--;

            }
        }
        Log.d("trah", comment.getVotes().size() + " ");
        Log.d("trah user", user.getId() + " ");
        votes.put(position, vote);
        while ((i < comment.getVotes().size()) && ( infos.get(position).first == 0))
        {
            Vote v = comment.getVotes().get(i);
            if(v.getVoted_by().getId() == user.getId())
            {
                if(v.getType().equals("upvote"))
                {
                    paire = new Pair<Integer, Integer>(1,i);
                    infos.put(position, paire);
                    holder.upvote.setChecked(true);
                    //holder.upvote.setImageResource(R.drawable.ic_upvote_selected_48dp);

                }
                else if(v.getType().equals("downvote"))
                {

                    paire = new Pair<Integer, Integer>(-1,i);
                    infos.put(position, paire);
                    holder.downvote.setChecked(true);
                    //holder.downvote.setImageResource(R.drawable.ic_downvote_selected_48dp);
                }
                else
                {
                    paire = new Pair<Integer, Integer>(0,-1);
                    infos.put(position, paire);
                }
            }
            i++;

        }
        holder.voteNbr.setText(String.valueOf(votes.get(position)));
        holder.dateOfComment.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM).format(comment.getPosting_date()));
        holder.downvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infos.get(position).first == 0)
                {
                    Vote vo = new Vote();
                    vo.setType("downvote");
                    vo.setComment(comment);
                    vo.setVoted_by(user);
                    vo.setState(0);
                    comment.getVotes().add(vo);
                    paire = new Pair<Integer, Integer>(-1,comment.getVotes().size() - 1);
                    infos.put(position, paire);
                    addVote(vo);
                    votes.put(position, votes.get(position) -1);
                    holder.downvote.setChecked(true);
                    //holder.downvote.setImageResource(R.drawable.ic_downvote_selected_48dp);

                }
                else if(infos.get(position).first == 1)
                {
                    comment.getVotes().get(infos.get(position).second).setType("downvote");
                    updateVote(comment.getVotes().get(infos.get(position).second));
                    paire = new Pair<Integer, Integer>(-1,comment.getVotes().size() - 1);
                    votes.put(position, votes.get(position) -2);
                    infos.put(position, paire);
                    holder.upvote.setChecked(false);
                    holder.downvote.setChecked(true);
                   // holder.upvote.setImageResource(R.drawable.ic_upvote_unselected_48dp);
                   // holder.downvote.setImageResource(R.drawable.ic_downvote_selected_48dp);

                }
                else if(infos.get(position).first == -1)
                {
                    comment.getVotes().remove(infos.get(position).second);
                    removeVote(comment.getVotes().get(infos.get(position).second));
                    paire = new Pair<Integer, Integer>(0,-1);
                    infos.put(position, paire);
                    votes.put(position, votes.get(position) +1);
                    holder.downvote.setChecked(false);
                    //holder.downvote.setImageResource(R.drawable.ic_downvote_unselected_48dp);
                }
                holder.voteNbr.setText(String.valueOf(votes.get(position)));

            }
        });

        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infos.get(position).first == 0)
                {
                    Vote vo = new Vote();
                    vo.setType("upvote");
                    vo.setComment(comment);
                    vo.setVoted_by(user);
                    vo.setState(0);
                    comment.getVotes().add(vo);
                    paire = new Pair<Integer, Integer>(1,comment.getVotes().size() - 1);
                    infos.put(position, paire);
                    addVote(vo);
                    votes.put(position, votes.get(position) +1);
                    holder.upvote.setChecked(true);
                   // holder.upvote.setImageResource(R.drawable.ic_upvote_selected_48dp);

                }
                else if(infos.get(position).first == -1)
                {
                    comment.getVotes().get(infos.get(position).second).setType("upvote");
                    updateVote(comment.getVotes().get(infos.get(position).second));
                    paire = new Pair<Integer, Integer>(1,comment.getVotes().size() - 1);
                    votes.put(position, votes.get(position) +2);
                    infos.put(position, paire);
                    holder.upvote.setChecked(true);
                    holder.downvote.setChecked(false);
                    //holder.upvote.setImageResource(R.drawable.ic_upvote_selected_48dp);
                    //holder.downvote.setImageResource(R.drawable.ic_downvote_unselected_48dp);

                }
                else if(infos.get(position).first == 1)
                {
                    comment.getVotes().remove(infos.get(position).second);
                    removeVote(comment.getVotes().get(infos.get(position).second));
                    paire = new Pair<Integer, Integer>(0,-1);
                    infos.put(position, paire);
                    votes.put(position, votes.get(position) -1);
                    holder.upvote.setChecked(false);
                    //holder.upvote.setImageResource(R.drawable.ic_upvote_unselected_48dp);
                }
                holder.voteNbr.setText(String.valueOf(votes.get(position)));


            }
        });
    //        String url = Comment.getPhotos().get(0).getPhoto();
    //      Picasso.get().load(url).resize(holder.image.getWidth(), holder.image.getHeight()).centerCrop().into(holder.image);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView body, username, voteNbr, dateOfComment;
        RatingBar rating;
        CheckBox upvote, downvote;
        public CardView card;

        public ViewHolder(View convertView) {
            super(convertView);
            body = convertView.findViewById(R.id.commentaire);
            username = convertView.findViewById(R.id.username);
            voteNbr = convertView.findViewById(R.id.nbrOfVotes);
            upvote = convertView.findViewById(R.id.upvote);
            downvote = convertView.findViewById(R.id.downvote);
            dateOfComment = convertView.findViewById(R.id.dateOfComment);
            rating = convertView.findViewById(R.id.ratingBar);
            card = convertView.findViewById(R.id.card_view_comment);
        }

    }

    public void addVote(Vote v)
    {

        InteractionService.getInstance().voteComment(v, new InteractionService.InteractionServiceAddVoteCallBack() {
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

    public void updateVote(Vote v) {
        InteractionService.getInstance().updateVoteComment(v, new InteractionService.InteractionServiceUpdateVoteCallBack() {
            @Override
            public void onResponse() {
            }

            @Override
            public void onFailure(String error) {
            }

        });
    }
    public void removeItem(int position) {
        Comment comment = listData.get(position);
        comment.setState(1);
        InteractionService.getInstance().hideComment(comment, new InteractionService.InteractionServiceUpdateVoteCallBack() {
            @Override
            public void onResponse() {
            }

            @Override
            public void onFailure(String error) {


            }

        });
        listData.remove(position);
        notifyItemRemoved(position);
    }
}
