package com.example.olfakaroui.android.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.example.olfakaroui.android.AppController;
import com.example.olfakaroui.android.entity.Comment;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.Vote;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

    private List<Comment> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    int newid = 0;
    Pair<Integer,Integer> paire;
    String url = UrlConst.addVote;
    String url2 = UrlConst.updateVote;
    HashMap<Integer,Pair<Integer,Integer>> infos = new HashMap<>();
    HashMap<Integer,Integer> votes = new HashMap<>();
    public CommentListAdapter(List<Comment> listData) {
        this.listData = listData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comment_item, parent, false);

        return new ViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        //User user = MainActivity.connectedUser;
        paire = new Pair<Integer, Integer>(0,-1);
        infos.put(position, paire);
        User user = new User();
        user.setId(6);
        user.setLastName("Karoui");
        user.setFirstName("Olfa");
        Comment comment = listData.get(position);
        holder.body.setText(comment.getBody());
        holder.username.setText(comment.getPosted_by().getFirstName() + " " + comment.getPosted_by().getLastName() );
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
                    holder.upvote.setImageResource(R.drawable.ic_upvote_selected_48dp);

                }
                else if(v.getType().equals("downvote"))
                {

                    paire = new Pair<Integer, Integer>(-1,i);
                    infos.put(position, paire);
                    holder.downvote.setImageResource(R.drawable.ic_downvote_selected_48dp);
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
                    vo.setId(addVote(vo));
                    votes.put(position, votes.get(position) -1);
                    holder.downvote.setImageResource(R.drawable.ic_downvote_selected_48dp);

                }
                else if(infos.get(position).first == 1)
                {
                    comment.getVotes().get(infos.get(position).second).setType("downvote");
                    updateVote(comment.getVotes().get(infos.get(position).second));
                    paire = new Pair<Integer, Integer>(-1,comment.getVotes().size() - 1);
                    votes.put(position, votes.get(position) -2);
                    infos.put(position, paire);
                    holder.upvote.setImageResource(R.drawable.ic_upvote_unselected_48dp);
                    holder.downvote.setImageResource(R.drawable.ic_downvote_selected_48dp);

                }
                else if(infos.get(position).first == -1)
                {
                    comment.getVotes().remove(infos.get(position).second);
                    removeVote(comment.getVotes().get(infos.get(position).second));
                    paire = new Pair<Integer, Integer>(0,-1);
                    infos.put(position, paire);
                    votes.put(position, votes.get(position) +1);
                    holder.downvote.setImageResource(R.drawable.ic_downvote_unselected_48dp);
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
                    vo.setId(addVote(vo));
                    votes.put(position, votes.get(position) +1);
                    holder.upvote.setImageResource(R.drawable.ic_upvote_selected_48dp);

                }
                else if(infos.get(position).first == -1)
                {
                    comment.getVotes().get(infos.get(position).second).setType("upvote");
                    updateVote(comment.getVotes().get(infos.get(position).second));
                    paire = new Pair<Integer, Integer>(1,comment.getVotes().size() - 1);
                    votes.put(position, votes.get(position) +2);
                    infos.put(position, paire);
                    holder.upvote.setImageResource(R.drawable.ic_upvote_selected_48dp);
                    holder.downvote.setImageResource(R.drawable.ic_downvote_unselected_48dp);

                }
                else if(infos.get(position).first == 1)
                {
                    comment.getVotes().remove(infos.get(position).second);
                    removeVote(comment.getVotes().get(infos.get(position).second));
                    paire = new Pair<Integer, Integer>(0,-1);
                    infos.put(position, paire);
                    votes.put(position, votes.get(position) -1);
                    holder.upvote.setImageResource(R.drawable.ic_upvote_unselected_48dp);
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

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView body, username, voteNbr, dateOfComment;
        RatingBar rating;
        ImageButton upvote, downvote;

        public ViewHolder(View convertView) {
            super(convertView);
            body = convertView.findViewById(R.id.commentaire);
            username = convertView.findViewById(R.id.username);
            voteNbr = convertView.findViewById(R.id.nbrOfVotes);
            upvote = convertView.findViewById(R.id.upvote);
            downvote = convertView.findViewById(R.id.downvote);
            dateOfComment = convertView.findViewById(R.id.dateOfComment);
        }

    }

    public int addVote(Vote v)
    {

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        GsonBuilder builder = new GsonBuilder();
                        Gson mGson = builder.create();
                        Vote newVote  = mGson.fromJson(response, Vote.class);
                        newid = newVote.getId();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String,String>();
                params.put("type", v.getType());
                params.put("id_comment", String.valueOf(v.getComment().getId()));
                params.put("id_user", String.valueOf(v.getVoted_by().getId()));
                params.put("state", String.valueOf(v.getState()));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
        return newid;
    }

    public void removeVote(Vote v)
    {
        StringRequest dr = new StringRequest(Request.Method.DELETE, url2+"/"+v.getId(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error.

                    }
                }
        );
        AppController.getInstance().addToRequestQueue(dr);
    }

    public void updateVote(Vote v)
    {
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url2+"/"+v.getId(),
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String,String>();
                params.put("type", v.getType());
                params.put("id", String.valueOf(v.getId()));
                params.put("id_comment", String.valueOf(v.getComment().getId()));
                params.put("id_user", String.valueOf(v.getVoted_by().getId()));
                params.put("state", String.valueOf(v.getState()));

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
    }
}
