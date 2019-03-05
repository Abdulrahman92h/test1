package com.example.ayatbahaa.waveapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

import java.util.List;

class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mcontext;
    private List<Upload> muploads;


    public ImageAdapter(Context context, List<Upload> uploads){
        this.mcontext=context;
        this.muploads=uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(mcontext).inflate(R.layout.image_item,parent,false);
        ImageViewHolder imageViewHolder=new ImageViewHolder(view);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadcurrent=muploads.get(position);
        holder.textViewname.setText(uploadcurrent.getmName1());
        Picasso.get().load(uploadcurrent.mImage1).into(holder.imageView);
        holder.expandableTextView.setText(uploadcurrent.getmName2());
    }



    @Override
    public int getItemCount() {
        return muploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewname;
        public ImageView imageView;
        public ExpandableTextView expandableTextView;

        public ImageViewHolder(View itemView) {
            super(itemView);
            textViewname=itemView.findViewById(R.id.textview_id);
            imageView=itemView.findViewById(R.id.Image_review);
            expandableTextView=itemView.findViewById(R.id.expand_text_view);
        }


    }
}
