package com.example.ayush.bottomnavigation;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Weirdo on 21-02-2017.
 */

public class SpeakersAdapter extends RecyclerView.Adapter<SpeakersAdapter.MyViewHolder> {


    List<SpeakersModel> speakersModels;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public CardView abc;
        public MyViewHolder(View view) {
            super(view);

//            title = (TextView) view.findViewById(R.id.title)
              abc = (CardView) view.findViewById(R.id.card_view);

        }
    }


    public SpeakersAdapter(List<SpeakersModel> speakersModels) {
        this.speakersModels = speakersModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rview_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SpeakersModel sm = speakersModels.get(position);
       // holder.title.setText(sm.getName());
        //For Ayush : Add code to set image via picasso

    }

    @Override
    public int getItemCount() {
        return speakersModels.size();
    }
}