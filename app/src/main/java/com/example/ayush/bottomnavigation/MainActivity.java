package com.example.ayush.bottomnavigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
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
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.ayush.bottomnavigation.NetworkServices.VolleySingleton;
import com.example.ayush.bottomnavigation.NetworkServices.WebViewC;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.crash.FirebaseCrash;

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

        DefaultSliderView defaultSliderView0=new DefaultSliderView(this);
        defaultSliderView0.image(R.drawable.thatsfinal);
        mDemoSlider.addSlider(defaultSliderView0);
//
//        DefaultSliderView defaultSliderView1=new DefaultSliderView(this);
//        defaultSliderView1.image(R.drawable.thats1);
//        mDemoSlider.addSlider(defaultSliderView1);

        DefaultSliderView defaultSliderView2=new DefaultSliderView(this);
        defaultSliderView2.image(R.drawable.thats2);
        mDemoSlider.addSlider(defaultSliderView2);

        DefaultSliderView defaultSliderView3=new DefaultSliderView(this);
        defaultSliderView3.image(R.drawable.thats3);
        mDemoSlider.addSlider(defaultSliderView3);

        DefaultSliderView defaultSliderView4=new DefaultSliderView(this);
        defaultSliderView4.image(R.drawable.thats4);
        mDemoSlider.addSlider(defaultSliderView4);

        DefaultSliderView defaultSliderView6=new DefaultSliderView(this);
        defaultSliderView6.image(R.drawable.thats6);
        mDemoSlider.addSlider(defaultSliderView6);

        DefaultSliderView defaultSliderView5=new DefaultSliderView(this);
        defaultSliderView5.image(R.drawable.thats5);
        mDemoSlider.addSlider(defaultSliderView5);

        DefaultSliderView defaultSliderView7=new DefaultSliderView(this);
        defaultSliderView7.image(R.drawable.thats7);
        mDemoSlider.addSlider(defaultSliderView7);

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        //mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Invisible);



        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Speakers", R.drawable.speaker, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Home", R.drawable.home, R.color.colorAccent);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Profile", R.drawable.profile, R.color.colorAccent);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#000000"));
        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setCurrentItem(1);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        //bottomNavigation.setForceTint(true);
        bottomNavigation.setColored(true);
        bottomNavigation.setTranslucentNavigationEnabled(false);
        bottomNavigation.setInactiveColor(Color.parseColor("#FFFFFF"));
        bottomNavigation.setAccentColor(Color.parseColor("#f1c40f"));

//        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
//        bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorBottomNavigationActiveColored));
//
//        bottomNavigation.setForceTint(true);
//        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
//        bottomNavigation.setCurrentItem(1);
//        bottomNavigation.setColored(true);

      bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
          @Override
          public boolean onTabSelected(int position, boolean wasSelected) {

              if(position==0)
              {
                  Intent intent = new Intent(getApplicationContext(), SpeakerRecyclerView.class);
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
        } catch (Exception e) {
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
                    //Toast.makeText(MainActivity.this, "ABAB"+response.toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
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
                                //Toast.makeText(MainActivity.this, "LALALAL\n"+response.toString(), Toast.LENGTH_SHORT).show();
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
                                //Toast.makeText(MainActivity.this, "LALALAL\n"+error.toString(), Toast.LENGTH_SHORT).show();
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

                //Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        SharedPreferences sharedprefrence=getSharedPreferences(getString(R.string.MainInfo),MODE_PRIVATE);
        String state=sharedprefrence.getString(getString(R.string.Status),getString(R.string.unregistered));
        String token=sharedprefrence.getString(getString(R.string.Token),"");
        Log.d("TAG",state+token);
        if(state.equals(getString(R.string.unregistered)))
        {
            final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_register, null);
            builder.setView(dialogView);
            builder.setPositiveButton("Take me there", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivity(new Intent(MainActivity.this,Profile.class));
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert= builder.create();
            alert.show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomNavigation.setCurrentItem(1);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.about_theme,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        final AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
//        LayoutInflater inflater = getLayoutInflater();
//        View dialogView = inflater.inflate(R.layout.alert_connect, null);
//        RelativeLayout Web,Face,Twit,Insta,Link,Snap;
//        Web= (RelativeLayout) dialogView.findViewById(R.id.Web);
//        Face= (RelativeLayout) dialogView.findViewById(R.id.Fac);
//        Twit= (RelativeLayout) dialogView.findViewById(R.id.Twit);
//        Insta= (RelativeLayout) dialogView.findViewById(R.id.Inst);
//        Link= (RelativeLayout) dialogView.findViewById(R.id.Link);
//        Snap= (RelativeLayout) dialogView.findViewById(R.id.Snap);
//        Web.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setClass(MainActivity.this, WebViewC.class);
//                intent.putExtra("url","http://tedxdtu.in/");
//                startActivity(intent);
//            }
//        });
//
//        Face.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setClass(MainActivity.this, WebViewC.class);
//                intent.putExtra("url","https://www.facebook.com/tedxdtu");
//                startActivity(intent);
//            }
//        });
//
//        Twit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setClass(MainActivity.this, WebViewC.class);
//                intent.putExtra("url","https://twitter.com/tedxdtuofficial");
//                startActivity(intent);
//            }
//        });
//
//        Insta.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setClass(MainActivity.this, WebViewC.class);
//                intent.putExtra("url","https://www.instagram.com/tedxdtu/");
//                startActivity(intent);
//            }
//        });
//
//        Link.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setClass(MainActivity.this, WebViewC.class);
//                intent.putExtra("url","https://www.linkedin.com/company/tedxdtu");
//                startActivity(intent);
//            }
//        });
//
//        Snap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("*/*");
//                intent.setPackage("com.snapchat.android");
//                startActivity(Intent.createChooser(intent, "Open Snapchat"));
//            }
//        });
//        builder.setView(dialogView);
//        AlertDialog alert= builder.create();
//        alert.show();
        startActivity(new Intent(MainActivity.this,AboutTheTheme.class));
      return  true;



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
