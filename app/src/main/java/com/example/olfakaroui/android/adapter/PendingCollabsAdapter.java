package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.DonationType;
import com.example.olfakaroui.android.entity.Item;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.service.EventService;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PendingCollabsAdapter extends RecyclerView.Adapter<PendingCollabsAdapter.ViewHolder> {


    public List<Collab> collabs;
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
    public void onBindViewHolder(@NonNull PendingCollabsAdapter.ViewHolder eventViewHolder, int i) {
        Collab collab = collabs.get(i);
        //eventViewHolder.bind(mEvents.get(i), context);
        if(collab.getState() == 0)

        {
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




        }
    }
    public void removeItem(int position) {
        Collab collab = collabs.get(position);
        collab.setState(1);
        EventService.getInstance().confirmCollab(collab, new EventService.EventServiceConfirmOrRefuseCollabCallBack() {
            @Override
            public void onResponse(String events) {
            }

            @Override
            public void onFailure(String error) {


            }

        });
        collabs.remove(position);

        notifyItemRemoved(position);
    }
    public void denyItem(int position) {
        Collab collab = collabs.get(position);
        collab.setState(2);
        EventService.getInstance().confirmCollab(collab, new EventService.EventServiceConfirmOrRefuseCollabCallBack() {
            @Override
            public void onResponse(String events) {
            }

            @Override
            public void onFailure(String error) {


            }

        });
        collabs.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        Log.d("collabs", " " + collabs.size());
        return collabs.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView type;
        private TextView name;
        private TextView amount;
        private TextView nameofevent;
        private ImageView userImg;
        public RelativeLayout denyView, confirmView;
        public CardView card;

        ViewHolder(View itemView){
            super(itemView);
            nameofevent = itemView.findViewById(R.id.event_collab);
            name = itemView.findViewById(R.id.collab_by_text);
            userImg = itemView.findViewById(R.id.collab_by_photo);
            type = itemView.findViewById(R.id.type_collab);
            amount = itemView.findViewById(R.id.amount_collab);
            denyView = itemView.findViewById(R.id.deny_view);
            confirmView = itemView.findViewById(R.id.confirm_view);
            card = itemView.findViewById(R.id.view_card);

        }

    }




}

