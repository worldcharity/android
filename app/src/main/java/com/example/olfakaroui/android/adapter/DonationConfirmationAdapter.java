package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.entity.DonationType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DonationConfirmationAdapter extends RecyclerView.Adapter<DonationConfirmationAdapter.ViewHolder> {

    public HashMap<Integer,String> nameandgoal;
   public  List<Integer> name ;
    public  List<String> don ;

    private LayoutInflater layoutInflater;
    private Context context;


    public DonationConfirmationAdapter(Context aContext, HashMap<Integer,String> nameandgoal,List<Integer> name, List<String> don) {
        this.context = aContext;
        this.nameandgoal = nameandgoal;
        layoutInflater = LayoutInflater.from(aContext);
        this.name = name ;
        this.don = don ;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.donation_confirmation_item, parent, false);

        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        String name1 = this.don.get(position);
        String nameandgoal1 = this.nameandgoal.get(name.get(position));
        holder.nomView.setText(name1);
        holder.goalView.setText(nameandgoal1);
        holder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameandgoal.remove(name.get(position));
                name.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.nameandgoal.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView nomView;
        TextView goalView;
        ImageButton b ;

        public ViewHolder(View itemView) {
            super(itemView);
            nomView = (TextView) itemView.findViewById(R.id.thetype);
            goalView = (TextView) itemView.findViewById(R.id.lenalgoal);
            b= itemView.findViewById(R.id.xfordonation);


        }

        }
    }

