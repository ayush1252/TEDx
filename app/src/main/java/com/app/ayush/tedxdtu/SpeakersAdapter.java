package com.app.ayush.tedxdtu;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Weirdo on 21-02-2017.
 */

public class SpeakersAdapter extends RecyclerView.Adapter<SpeakersAdapter.MyViewHolder> {


    List<SpeakerClass> speakersModels;
    Context context;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView abc;
        public MyViewHolder(View view) {
            super(view);
              abc = (ImageView) view.findViewById(R.id.card_view);

        }
    }


    public SpeakersAdapter(List<SpeakerClass> speakersModels,Context context) {
        this.speakersModels = speakersModels;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SpeakerClass sm = speakersModels.get(position);
       String url= sm.getBg_img();
        Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.progress_animation)
                .error(R.drawable.cantload)//Yahan Pe Image Not found ayega
                .into(holder.abc);

    }

    @Override
    public int getItemCount() {
        return speakersModels.size();
    }
}