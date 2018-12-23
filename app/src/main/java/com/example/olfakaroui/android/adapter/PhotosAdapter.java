package com.example.olfakaroui.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olfakaroui.android.R;
import com.example.olfakaroui.android.UrlConst;
import com.example.olfakaroui.android.entity.Photo;
import com.example.olfakaroui.android.entity.Photo;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {


    public List<Photo> photosList;
    private Context mContext;

    public PhotosAdapter(List<Photo> photos, Context context){
        photosList = photos;
        mContext = context;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.photo_gallery_item, viewGroup, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder eventViewHolder, int i) {
        eventViewHolder.bind(photosList.get(i), mContext);
    }

    @Override
    public int getItemCount() {
        return photosList.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;

        PhotoViewHolder(View itemView){
            super(itemView);
            mImageView = itemView.findViewById(R.id.photo_item);

        }

        void bind(final Photo photo, final Context context){

            Picasso.get().load(UrlConst.IMAGES+photo.getPhoto()).resize(525, 559).centerCrop().into(mImageView);

        }
    }
}
