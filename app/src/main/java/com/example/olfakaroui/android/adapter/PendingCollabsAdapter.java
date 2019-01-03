package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.DonationType;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PendingCollabsAdapter extends RecyclerView.Adapter<PendingCollabsAdapter.ViewHolder> {


    public List<Collab> collabs = new ArrayList<>();
    private Context context;
    public interface DonateCallBack{
        void onDonateBtnClickListener(DonationType id);
    }
    private DonateCallBack mCallBack;

    public void setCallBack(DonateCallBack callBack) {
        mCallBack = callBack;
    }


    public PendingCollabsAdapter(List<Collab> c, Context context){
        collabs = c;
        this.context = context;

        //SessionManager sessionManager = new SessionManager(context);
        //sessionManager.getLogin(current);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.collab_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder eventViewHolder, int i) {
        Collab collab = collabs.get(i);
        //eventViewHolder.bind(mEvents.get(i), context);

        eventViewHolder.amount.setText("Amount: "+ collab.getAmount());
        eventViewHolder.name.setText(collab.getCollab_by().getFirstName() + " "+ collab.getCollab_by().getLastName());
        eventViewHolder.nameofevent.setText(collab.getEvent().getName());
        eventViewHolder.type.setText(collab.getCollab_type().getName());
        if(collab.getCollab_by().getPhoto() == null)
        {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(collab.getCollab_by().getFirstName().substring(0,1).toUpperCase() + collab.getCollab_by().getLastName().substring(0,1).toUpperCase(), context.getResources().getColor(R.color.colorAccent));
            eventViewHolder.userImg.setImageDrawable(drawable);

        }
        else
        {
            Picasso.get().load(collab.getCollab_by().getPhoto()).noFade().resize(50, 50).centerCrop().into(eventViewHolder.userImg);

        }


        eventViewHolder.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collab.setState(1);
                collabs.remove(collab);
                notifyDataSetChanged();
                EventService.getInstance().confirmCollab(collab, new EventService.EventServiceConfirmOrRefuseCollabCallBack() {
                    @Override
                    public void onResponse(String events) {
                    }

                    @Override
                    public void onFailure(String error) {


                    }

                });

            }
        });
        eventViewHolder.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collab.setState(2);
                collabs.remove(collab);
                notifyDataSetChanged();
                EventService.getInstance().confirmCollab(collab, new EventService.EventServiceConfirmOrRefuseCollabCallBack() {
                    @Override
                    public void onResponse(String events) {
                    }

                    @Override
                    public void onFailure(String error) {


                    }

                });

            }


        });



    }

    @Override
    public int getItemCount() {
        return collabs.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView type;
        private TextView name;
        private TextView amount;
        private TextView nameofevent;
        private ImageView confirm, deny,userImg;

        ViewHolder(View itemView){
            super(itemView);
            confirm = itemView.findViewById(R.id.confirm_collab);
            deny = itemView.findViewById(R.id.deny_collab);
            nameofevent = itemView.findViewById(R.id.event_collab);
            name = itemView.findViewById(R.id.collab_by_text);
            userImg = itemView.findViewById(R.id.collab_by_photo);
            type = itemView.findViewById(R.id.type_collab);
            amount = itemView.findViewById(R.id.amount_collab);

        }

    }




}

