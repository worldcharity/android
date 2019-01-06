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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.SessionManager;
import com.example.olfakaroui.android.UI.events.DonateFragment;
import com.example.olfakaroui.android.UI.events.EventDetailActivity;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.DonationType;
import com.example.olfakaroui.android.entity.Event;
import com.example.olfakaroui.android.entity.User;
import com.example.olfakaroui.android.entity.Vote;
import com.example.olfakaroui.android.service.EventService;
import com.example.olfakaroui.android.service.InteractionService;
import com.pnikosis.materialishprogress.ProgressWheel;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class DonationTypesAdapter extends RecyclerView.Adapter<DonationTypesAdapter.ViewHolder> {


    public List<DonationType> mTypes = new ArrayList<>();
    public int eventId;
    private Context context;
    User current = new User();
    public boolean isClickable = true;

    public interface DonateCallBack{
        void onDonateBtnClickListener(DonationType id);
    }
    private DonateCallBack mCallBack;

    public void setCallBack(DonateCallBack callBack) {
        mCallBack = callBack;
    }


    public DonationTypesAdapter(List<DonationType> types, int eventId, Context context){
        mTypes = types;
        this.eventId = eventId;
        this.context = context;

        SessionManager sessionManager = new SessionManager(context);
        sessionManager.getLogin(current);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.donation_type_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder eventViewHolder, int i) {
        DonationType type = mTypes.get(i);
        //eventViewHolder.bind(mEvents.get(i), context);
        Log.d("goal", type.getDonationevent().getGoal() + " goal");
        eventViewHolder.goal.setText("Goal: "+ type.getDonationevent().getGoal());
        eventViewHolder.name.setText(type.getName());
        EventService.getInstance().getTotal(type.getId(), eventId, new EventService.EventServiceGetTotalCallBack() {
            @Override
            public void onResponse(double total) {

                eventViewHolder.total.setText("currents earns : "+ total);
                float percentage = (float) ((total/type.getDonationevent().getGoal()));
                eventViewHolder.progress.setProgress((float)percentage);

            }

            @Override
            public void onFailure(String error) {

            }
        });


        eventViewHolder.donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isClickable)
                    return;
                mCallBack.onDonateBtnClickListener(type);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mTypes.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView goal;
        private TextView name;
        private TextView total;
        private ProgressWheel progress;
        private ImageView donate;

        ViewHolder(View itemView){
            super(itemView);
            donate = itemView.findViewById(R.id.donate_button);
            name = itemView.findViewById(R.id.name_text);
            progress = itemView.findViewById(R.id.goal_percentage);
            goal = itemView.findViewById(R.id.goal_text);
            total = itemView.findViewById(R.id.current_text);

        }

    }




}

