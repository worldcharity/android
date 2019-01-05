package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.Post;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.Vote;
import com.example.olfakaroui.android.service.InteractionService;
import com.example.olfakaroui.android.utils.SessionManager;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostsListAdapter extends BaseAdapter {

    private List<Post> listData;
   public static List<Post> lc = new ArrayList<>();

    private LayoutInflater layoutInflater;
    private Context context;
    Pair<Integer,Integer> paire;
    User current = new User();
    HashMap<Integer,Pair<Integer,Integer>> infos = new HashMap<>();

    public PostsListAdapter(Context aContext, List<Post> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        //SessionManager sessionManager = new SessionManager();
        //sessionManager.getLogin(current);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position,  View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.post_item, null);
           // convertView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT,200));

            holder = new ViewHolder();
            holder.titleView = (TextView) convertView.findViewById(R.id.postTitre);
            holder.bodyView = (TextView) convertView.findViewById(R.id.PostBody);
            holder.like = convertView.findViewById(R.id.like_post);
            holder.usr = convertView.findViewById(R.id.imgUser);
            holder.commentsView = (TextView) convertView.findViewById(R.id.postcomments);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        Post post = this.listData.get(position);

        holder.titleView.setText(post.getUser().getFirstName() + " " +post.getUser().getLastName());
        holder.bodyView.setText(post.getBody());
        holder.commentsView.setText("likes");
        if(post.getVotes().size()==0)
        {
            holder.commentsView.setText("no likes");
        }
        else if(post.getVotes().size()==1)
        {
            holder.commentsView.setText("1 like");
        }
        else {
            holder.commentsView.setText(post.getVotes().size()+ " likes");
        }
        if(post.getUser().getPhoto() == null)
        {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(post.getUser().getFirstName().substring(0,1).toUpperCase() + post.getUser().getLastName().substring(0,1).toUpperCase(), context.getResources().getColor(R.color.colorAccent));
            holder.usr.setImageDrawable(drawable);
            //avatarView.setImageDrawable(new LetterAvatar(UserProfileActivity.this, getResources().getColor(R.color.colorAccent), user.getFirstName().substring(0,1).toUpperCase() + user.getLastName().substring(0,1).toUpperCase(), 20));
        }
        else
        {
            Picasso.get().load(post.getUser().getPhoto()).noFade().resize(250, 250).centerCrop().into(holder.usr);

        }
        paire = new Pair<Integer, Integer>(0,-1);
        infos.put(position, paire);
        int index = 0;
        current.setId(6);
        holder.like.setImageResource(R.drawable.ic_like_unselected_24dp);
        holder.like.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
        while ((index < post.getVotes().size()) && ( infos.get(position).first == 0))
        {
            Vote v = post.getVotes().get(index);
            if(v.getVoted_by().getId() == current.getId())
            {
                paire = new Pair<Integer, Integer>(1,index);
                infos.put(position, paire);
                holder.like.setImageResource(R.drawable.ic_like_selected_24dp);
                holder.like.setColorFilter(context.getResources().getColor(R.color.colorAccent));

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
                    vo.setPost(post);
                    vo.setVoted_by(current);
                    vo.setState(0);
                    post.getVotes().add(vo);
                    paire = new Pair<Integer, Integer>(1,post.getVotes().size() - 1);
                    infos.put(position, paire);
                    addVote(vo);
                    holder.like.setImageResource(R.drawable.ic_like_selected_24dp);
                    holder.like.setColorFilter(context.getResources().getColor(R.color.colorAccent));
                    //notifyDataSetChanged();
                    if(post.getVotes().size()==0)
                    {
                        holder.commentsView.setText("no likes");
                    }
                    else if(post.getVotes().size()==1)
                    {
                        holder.commentsView.setText("1 like");
                    }
                    else {
                        holder.commentsView.setText(post.getVotes().size()+ " likes");
                    }

                }
                else if(infos.get(position).first == 1)
                {

                    holder.like.setImageResource(R.drawable.ic_like_unselected_24dp);
                    holder.like.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
                    removeVote(post.getVotes().get(infos.get(position).second));
                    post.getVotes().remove(post.getVotes().get(infos.get(position).second));
                    paire = new Pair<Integer, Integer>(0,-1);
                    infos.put(position, paire);
                    if(post.getVotes().size() == 0)
                    {
                        holder.commentsView.setText("no likes");
                    }
                    else if(post.getVotes().size() ==1)
                    {
                        holder.commentsView.setText("1 like");
                    }
                    else {
                        holder.commentsView.setText(post.getVotes().size() + " likes");
                    }
                    //notifyDataSetChanged();
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView titleView;
        ImageView like,usr;
        TextView bodyView;
        TextView commentsView;
    }
    public void addVote(Vote v)
    {

        InteractionService.getInstance().votePost(v, new InteractionService.InteractionServiceAddVoteCallBack() {
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
}
