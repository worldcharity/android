package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.util.Pair;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UI.interfaces_for_charity.DonationConfirmationActivity;
import com.example.olfakaroui.android.entity.DonationEvent;
import com.example.olfakaroui.android.entity.DonationType;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DonationListAdapter extends RecyclerView.Adapter<DonationListAdapter.ViewHolder> {
    private List<DonationType> listData;
   public static List<DonationType> lc = new ArrayList<>();
   public static int compteurCause =0;
    private LayoutInflater layoutInflater;
    private Context context;
    int eventId;

    private MultiSelector mMultiSelector=new MultiSelector();
    List<String> goalwritten;
    List<String> selected;
    List<Integer> name;
    HashMap<Integer,String> nameandgoal;

    private ModalMultiSelectorCallback mActionModeCallback = new ModalMultiSelectorCallback(mMultiSelector) {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {

            menu.add("Confirm");
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {


                mode.finish();
                for (int i = listData.size(); i >= 0; i--) {

                    if (mMultiSelector.isSelected(i, 0)) {
                    }
                }
                for(int i=0;i<nameandgoal.size();i++)
                {
                }

                mMultiSelector.clearSelections();// (2)
                Intent i = new Intent(context.getApplicationContext(), DonationConfirmationActivity.class);
                i.putExtra("goals",nameandgoal);
                i.putIntegerArrayListExtra("ids",(ArrayList<Integer>)name);
                i.putStringArrayListExtra("don",(ArrayList<String >)selected);
                i.putExtra("event", eventId);
                if(name.size()>0)
                {
                    context.startActivity(i);

                }
                else
                {
                    Toast.makeText(context, "Please make sure to entre goal in your selections",
                            Toast.LENGTH_LONG).show();
                }
                return true;



        }
    };


    public DonationListAdapter(Context aContext, List<DonationType> listData, int eventId) {
        this.context = aContext;
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
        name = new ArrayList<>();
        selected = new ArrayList<>();
        goalwritten= new ArrayList<>();
        nameandgoal = new HashMap<>();
        this.eventId = eventId;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.donation_add_event_item, parent, false);

        return new ViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonationType donation = this.listData.get(position);
        holder.nomView.setText(donation.getName());
        holder.id = donation.getId();

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return this.listData.size();
    }

    public class ViewHolder extends SwappingHolder implements View.OnClickListener {
        TextView nomView;
        CardView card;
        LinearLayout lin;
        int id;
        int click = 0;
        ColorStateList oldColors;
        ColorStateList oldcolrs2;
        TextInputEditText goal;

        public ViewHolder(View itemView) {
            super(itemView,mMultiSelector);
            nomView = (TextView) itemView.findViewById(R.id.typeofdonation);
            card = (CardView) itemView.findViewById(R.id.carddonation);
            itemView.setOnClickListener(this);
             oldColors =  nomView.getTextColors();
             oldcolrs2 = card.getCardBackgroundColor();
             goal = new TextInputEditText(context);
             lin=(LinearLayout) itemView.findViewById(R.id.linearGoal);
        }

        @Override
        public void onClick(View view) {

            ((AppCompatActivity)view.getContext()).startSupportActionMode(mActionModeCallback);
                if (click==0)
                {

                    mMultiSelector.setSelectable(true); // enter selection mode
                    mMultiSelector.setSelected(ViewHolder.this, true); // set myViewHolder to selected
                    nomView.setTextColor(Color.WHITE);
                    card.setCardBackgroundColor(context.getResources().getColor(R.color.colorAccent));
                    click=1;
                    goal.setHint("enter your goal");
                    goal.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    goal.setTextColor(Color.WHITE);
                    goal.setInputType(InputType.TYPE_CLASS_PHONE);
                    lin.addView(goal);
                    goal.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                                    nameandgoal.put(id,goal.getText().toString());

                        }
                    });

                   name.add(id);
                   selected.add(nomView.getText().toString());


                }
                else if (click ==1)
                {
                    mMultiSelector.setSelected(ViewHolder.this, false);
                    nomView.setTextColor(oldColors);
                    click=0;
                    card.setCardBackgroundColor(oldcolrs2);

                        name.remove(id);
                        selected.remove(nomView.getText().toString());
                    if(!goal.getText().toString().trim().isEmpty()) {
                        nameandgoal.remove(id);
                    }

                    lin.removeView(goal);


                }


       //     }
        }
    }
}
