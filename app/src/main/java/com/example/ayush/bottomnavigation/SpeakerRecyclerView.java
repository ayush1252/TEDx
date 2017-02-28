package com.example.ayush.bottomnavigation;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by Weirdo on 16-02-2017.
 */

public class SpeakerRecyclerView extends AppCompatActivity {

    List<SpeakersModel> speakerslist = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speaker_recycler_view);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        //For Ayush : Add code for hitting the api and adding to the list of speakerslist, where you wil store just the image url
        SpeakersAdapter sAdapter = new SpeakersAdapter(speakerslist);
        RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(sLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(sAdapter);

        //Create a Card
       // android.support.v7.widget.CardView cardView = (android.support.v7.widget.CardView) findViewById(R.id.card_view);
       // cardView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.web_banner));


    }
}
