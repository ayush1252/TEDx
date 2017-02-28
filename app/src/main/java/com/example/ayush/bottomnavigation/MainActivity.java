package com.example.ayush.bottomnavigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.ayush.bottomnavigation.NetworkServices.VolleySingleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    SliderLayout mDemoSlider;
    AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        DefaultSliderView defaultSliderView=new DefaultSliderView(this);
        defaultSliderView.image(R.drawable.pic2);
        mDemoSlider.addSlider(defaultSliderView);
        mDemoSlider.addSlider(defaultSliderView);
        mDemoSlider.addSlider(defaultSliderView);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        //mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(8000);
        mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Speakers", R.drawable.speaker, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Home", R.drawable.home, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Profile", R.drawable.profile, R.color.colorPrimaryDark);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorAccent));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorBottomNavigationActiveColored));

        bottomNavigation.setForceTint(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setCurrentItem(1);
        bottomNavigation.setColored(true);

      bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
          @Override
          public boolean onTabSelected(int position, boolean wasSelected) {

              if(position==0)
              {
                  Intent intent = new Intent(getApplicationContext(), SpeakerActivity.class);
                  startActivity(intent);
                  return true;
              }

              if(position==2) {
                  Intent intent = new Intent(getApplicationContext(), Profile.class);
                  startActivity(intent);
                  return true;
              }
              return true;
          }
      });

        JSONObject jsonObject=new JSONObject();
        String url= String.valueOf(URI.create("http://api.tedxdtu.in/api/token"));
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        try {
            jsonObject.put("username",firebaseUser.getEmail());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if(response.has("error"))
                {
                    //Yahan Exist nahi karta
                    SharedPreferences sharedprefrence=getSharedPreferences(getString(R.string.MainInfo),MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedprefrence.edit();
                    editor.putString(getString(R.string.Status),getString(R.string.unregistered));
                    editor.putString(getString(R.string.Token)," ");
                    editor.commit();
                    Toast.makeText(MainActivity.this, "ABAB"+response.toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    String token="";
                    try {
                       token=response.getString("token");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SharedPreferences sharedprefrence=getSharedPreferences(getString(R.string.MainInfo),MODE_PRIVATE);
                    final SharedPreferences.Editor editor=sharedprefrence.edit();
                    if(token.length()!=0)
                    {
                        editor.putString(getString(R.string.Token),token);
                        editor.commit();
                        String url2=String.valueOf(URI.create("http://api.tedxdtu.in/api/user/application"));
                        final String finalToken = token;
                        JsonObjectRequest newjsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(MainActivity.this, "LALALAL\n"+response.toString(), Toast.LENGTH_SHORT).show();
                                if(response.has("error"))
                                {

                                }
                                else
                                { String status=getString(R.string.unregistered);
                                    try {
                                       status=response.getString("status");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    editor.putString(getString(R.string.Status),status);
                                    editor.commit();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(MainActivity.this, "LALALAL\n"+error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                Log.d("TAG","HUA");
                                Map<String, String>  params = new HashMap<String, String>();
                                params.put("Content-Type", "application/json");
                                params.put("tedxdtu-token", finalToken);
                                Log.d("TAG","HUA");
                                return params;

                    }



                };
                        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(newjsonObjectRequest);

                    }
        }}}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        SharedPreferences sharedprefrence=getSharedPreferences(getString(R.string.MainInfo),MODE_PRIVATE);
        String state=sharedprefrence.getString(getString(R.string.Status),getString(R.string.unregistered));
        String token=sharedprefrence.getString(getString(R.string.Token),"");
        Log.d("TAG",state+token);

    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigation.setCurrentItem(1);

    }
}
