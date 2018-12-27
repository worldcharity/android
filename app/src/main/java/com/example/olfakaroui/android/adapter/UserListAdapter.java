package com.example.olfakaroui.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.users.CharityProfileActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.Vote;
import com.example.olfakaroui.android.service.UserService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private List<User> listData;
    private LayoutInflater layoutInflater;
    private Context context;
    User current = new User();
    Pair<Integer,Integer> paire;
    HashMap<Integer,Pair<Integer,Integer>> infos = new HashMap<>();


    public UserListAdapter(Context aContext, List<User> listData) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
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
        paire = new Pair<Integer, Integer>(0,-1);
        infos.put(position, paire);
        current.setId(6);

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.charity_item, null);
            holder = new ViewHolder();
            holder.charityName = (TextView) convertView.findViewById(R.id.charity_name);
            holder.charityImage = (ImageView) convertView.findViewById(R.id.charity_photo);
            holder.share = (ImageView) convertView.findViewById(R.id.share_charity);
            holder.follow = (ImageView) convertView.findViewById(R.id.follow_charity);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }
        User user = this.listData.get(position);
        holder.charityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CharityProfileActivity.class);
                intent.putExtra("charity", user.getId());
                ((Activity) context).startActivityForResult(intent, 2);

            }
        });


        Picasso.get().load(user.getPhoto()).resize(525, 559).centerCrop().into(holder.charityImage);
        if(user.getPhoto() == null)
        {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(user.getFirstName().substring(0,1).toUpperCase(), context.getResources().getColor(R.color.colorAccent));
            holder.charityImage.setImageDrawable(drawable);
            //avatarView.setImageDrawable(new LetterAvatar(charityProfileActivity.this, getResources().getColor(R.color.colorAccent), charity.getFirstName().substring(0,1).toUpperCase() + charity.getLastName().substring(0,1).toUpperCase(), 20));
        }
        holder.charityName.setText(user.getFirstName());

        int index = 0;
        holder.follow.setImageResource(R.drawable.ic_sub);
        holder.follow.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));

        while ((index < user.getFollowers().size()) && ( infos.get(position).first == 0))
        {
            User v = user.getFollowers().get(index);
            if(v.getId() == current.getId())
            {
                paire = new Pair<Integer, Integer>(1,index);
                infos.put(position, paire);
                holder.follow.setImageResource(R.drawable.ic_unsub);
                holder.follow.setColorFilter(context.getResources().getColor(R.color.colorPrimaryDark));
            }
            index++;

        }
        if(current.getId() == user.getId())
        {
            holder.follow.setVisibility(View.INVISIBLE);
            holder.share.setVisibility(View.INVISIBLE);
        }
        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(infos.get(position).first == 1)
                {
                    UserService.getInstance().unfollow(user.getId(), current.getId(), new UserService.UserServiceUnfollowCallBack() {
                        @Override
                        public void onResponse() {
                        }
                        @Override
                        public void onFailure(String error) {
                        }
                    });
                    holder.follow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_sub));
                    user.getFollowers().remove(current);
                    paire = new Pair<Integer, Integer>(0,-1);
                    infos.put(position, paire);
                }
                else
                {
                    UserService.getInstance().follow(user.getId(), current.getId(), new UserService.UserServiceFollowCallBack() {
                        @Override
                        public void onResponse() {
                        }

                        @Override
                        public void onFailure(String error) {
                        }
                    });
                    holder.follow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_unsub));
                    user.getFollowers().add(current);
                    paire = new Pair<Integer, Integer>(1,user.getFollowers().size() - 1);
                    infos.put(position, paire);

                }
            }
        });
        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView charityName;
        ImageView charityImage, follow, share;
    }
}
