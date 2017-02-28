package com.example.ayush.bottomnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
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

        if(!haveNetworkConnection())
        {
            AlertDialog.Builder builder= new AlertDialog.Builder(SpeakerRecyclerView.this);
            builder.setTitle("Internet Connection Failure");
            builder.setMessage("This part of application requires working internet connection.\n\nPlease " +
                    "enable internet and try again.");
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.create().show();

        }
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
            public void onItemClick(View view, int i) {
                Intent intent=new Intent(SpeakerRecyclerView.this,SpeakerActivity.class);
                intent.putExtra("cover",speakerslist.get(i).getCover_img());
                intent.putExtra("profile",speakerslist.get(i).getProfile_img());
                intent.putExtra("name",speakerslist.get(i).getName());
                intent.putExtra("desc",speakerslist.get(i).getDescription());
                intent.putExtra("short",speakerslist.get(i).getShort_desc());
                intent.putExtra("facebook",speakerslist.get(i).getFacebook());
                intent.putExtra("twitter",speakerslist.get(i).getTwitter());
                intent.putExtra("linked",speakerslist.get(i).getLinkedin());
                startActivity(intent);
            }
        }));



        //Create a Card
       // android.support.v7.widget.CardView cardView = (android.support.v7.widget.CardView) findViewById(R.id.card_view);
       // cardView.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.web_banner));


    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
}
