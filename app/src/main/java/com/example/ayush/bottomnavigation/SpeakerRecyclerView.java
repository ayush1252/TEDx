package com.example.ayush.bottomnavigation;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.ayush.bottomnavigation.NetworkServices.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weirdo on 16-02-2017.
 */

public class SpeakerRecyclerView extends AppCompatActivity {

    ArrayList<SpeakerClass> speakerslist = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speaker_recycler_view);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Speakers");
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        speakerslist=new ArrayList<SpeakerClass>();
        String url= String.valueOf(URI.create("http://api.tedxdtu.in/api/speakers"));
        Log.d("LA12","YES");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(SpeakerRecyclerView.this, response.toString(), Toast.LENGTH_SHORT).show();
                try {
                    Log.d("LA12","REPONSE YES");
                    JSONArray jsonarray=response.getJSONArray("speakers");
                    Log.d("LA12", String.valueOf(jsonarray.length())+"1234");
                    for(int i=0; i<jsonarray.length(); i++)
                    {
                        JSONObject js1=jsonarray.getJSONObject(i);
                        SpeakerClass newspeaker=new SpeakerClass();
                        newspeaker.setId(js1.getString("id"));
                        newspeaker.setName(js1.getString("name"));
                        newspeaker.setDescription(js1.getString("description"));
                        newspeaker.setShort_desc(js1.getString("short_desc"));
                        newspeaker.setBg_img(js1.getString("bg_img"));
                        newspeaker.setCover_img(js1.getString("cover_img"));
                        newspeaker.setProfile_img(js1.getString("profile_img"));
                        newspeaker.setFacebook(js1.getString("facebook"));
                        newspeaker.setLinkedin(js1.getString("linkedin"));
                        newspeaker.setTwitter(js1.getString("twitter"));
                        speakerslist.add(newspeaker);

                    }
                } catch (JSONException e) {
                    Log.d("LA12","Exception YES");

                    e.printStackTrace();
                }

                Log.d("LA12", String.valueOf(speakerslist.size()));
                SpeakersAdapter sAdapter = new SpeakersAdapter(speakerslist,SpeakerRecyclerView.this);
                RecyclerView.LayoutManager sLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(sLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(sAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LA12","ERROR YES");
                Toast.makeText(SpeakerRecyclerView.this, "ABCDED", Toast.LENGTH_SHORT).show();
                Toast.makeText(SpeakerRecyclerView.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        VolleySingleton.getInstance(SpeakerRecyclerView.this).addToRequestQueue(jsonObjectRequest);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent=new Intent(SpeakerRecyclerView.this,SpeakerActivity.class);
                startActivity(intent);
            }
        }));



        //Create a Card
       // android.support.v7.widget.CardView cardView = (android.support.v7.widget.CardView) findViewById(R.id.card_view);
       // cardView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.web_banner));


    }
}
