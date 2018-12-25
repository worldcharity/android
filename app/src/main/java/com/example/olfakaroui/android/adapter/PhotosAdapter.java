package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Cause;
import com.example.olfakaroui.android.entity.Photo;
import com.example.olfakaroui.android.entity.Photo;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PhotosAdapter extends BaseAdapter {

    private List<Photo> listData;
    private LayoutInflater layoutInflater;
    private Context context;


    public PhotosAdapter(Context aContext, List<Photo> listData) {
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

        final PhotosAdapter.ViewHolder holder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.photo_gallery_item, null);
            convertView.setBackgroundResource(R.drawable.rounded_cell);
            holder = new PhotosAdapter.ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.photo_item);
            convertView.setTag(holder);
        } else {
            holder = (PhotosAdapter.ViewHolder) convertView.getTag();

        }

        Photo photo = this.listData.get(position);
        Picasso.get().load(UrlConst.IMAGES+photo.getPhoto()).resize(800, 800).centerCrop().into(holder.imageView);

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }


}
