package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.Collab;
import com.example.olfakaroui.android.entity.DonationType;

import java.util.ArrayList;
import java.util.List;


public class UserCollabsMyEventAdapter extends RecyclerView.Adapter<UserCollabsMyEventAdapter.ViewHolder> {


    public List<Collab> collabs = new ArrayList<>();
    private Context context;
    public interface DonateCallBack{
        void onDonateBtnClickListener(DonationType id);
    }
    private DonateCallBack mCallBack;

    public void setCallBack(DonateCallBack callBack) {
        mCallBack = callBack;
    }


    public UserCollabsMyEventAdapter(List<Collab> c, Context context){
        collabs = c;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.mycollabs_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder eventViewHolder, int i) {
        Collab collab = collabs.get(i);
        //eventViewHolder.bind(mEvents.get(i), context);

        eventViewHolder.amount.setText("Amount: "+ collab.getAmount());
        eventViewHolder.nameofevent.setText(collab.getCollab_type().getName());
        eventViewHolder.type.setText(collab.getCollab_by().getFirstName()+ " "+ collab.getCollab_by().getLastName());


    }

    @Override
    public int getItemCount() {
        return collabs.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView type;
        private TextView amount;
        private TextView nameofevent;

        ViewHolder(View itemView){
            super(itemView);
            nameofevent = itemView.findViewById(R.id.event_collab);
            type = itemView.findViewById(R.id.type_collab);
            amount = itemView.findViewById(R.id.amount_collab);

        }

    }




}

