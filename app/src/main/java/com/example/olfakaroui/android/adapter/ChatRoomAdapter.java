package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.Message;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.UserService;
import com.squareup.picasso.Picasso;

import java.util.List;


public class ChatRoomAdapter extends RecyclerView.Adapter<ChatRoomAdapter.MyViewHolder> {
    private List<Message> MessageList;
    public Context context;

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView message;
        public ImageView image;



        public MyViewHolder(View view) {
            super(view);

            username = (TextView) view.findViewById(R.id.nickname);
            message = (TextView) view.findViewById(R.id.message);
            image = view.findViewById(R.id.user_pic);

        }
    }

    public ChatRoomAdapter(List<Message>MessagesList, Context c) {

        this.MessageList = MessagesList;
        context = c;


    }

    @Override public int getItemCount() {
        return MessageList.size();
    }
    @Override public ChatRoomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message, parent, false);



        return new ChatRoomAdapter.MyViewHolder(itemView);
    }

    @Override public void onBindViewHolder(final ChatRoomAdapter.MyViewHolder holder, final int position) {

        Message m = MessageList.get(position);
        UserService.getInstance().getUser(Integer.parseInt(m.getNickname()), new UserService.UserServiceGetUserCallBack() {
            @Override
            public void onResponse(User user) {
                holder.username.setText(user.getFirstName() + " "+ user.getLastName());
                if(user.getPhoto() == null)
                {
                    TextDrawable drawable = TextDrawable.builder()
                            .buildRound(user.getFirstName().substring(0,1).toUpperCase() + user.getLastName().substring(0,1).toUpperCase(), context.getResources().getColor(R.color.colorAccent));
                    holder.image.setImageDrawable(drawable);
                    }
                else
                {
                    Picasso.get().load(user.getPhoto()).noFade().resize(50, 50).centerCrop().into(holder.image);

                }
            }

            @Override
            public void onFailure(String error) {

            }

        });

        holder.message.setText(m.getMessage() );


    }



}