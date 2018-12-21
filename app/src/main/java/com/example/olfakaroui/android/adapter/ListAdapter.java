package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Cause;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private List<Cause> listData;
    public static List selectedPositions;
    private LayoutInflater layoutInflater;
    private Context context;


    public ListAdapter(Context aContext, List<Cause> listData) {
        this.context = aContext;
        this.listData = listData;
        selectedPositions = new ArrayList<>();
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

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.cause_item, null);
            convertView.setBackgroundResource(R.drawable.rounded_cell);
            holder = new ViewHolder();
            holder.nomView = (TextView) convertView.findViewById(R.id.causenom);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageCause);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        Cause cause = this.listData.get(position);
        Picasso.get().load(UrlConst.causesImages+cause.getPhoto()).resize(525, 559).centerCrop().into(holder.imageView);
        holder.nomView.setText(cause.getName());
        return convertView;
    }

    static class ViewHolder {
        TextView nomView;
        ImageView imageView;
    }
}
