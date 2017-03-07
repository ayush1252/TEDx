package com.app.tedxdtu.tedxdtu17;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.tedxdtu.tedxdtu17.NetworkServices.VolleySingleton;
import com.app.tedxdtu.tedxdtu17.NetworkServices.WebViewC;
import com.app.tedxdtu.tedxdtu17.NetworkServices.WebViewCl;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonObject;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import de.cketti.mailto.EmailIntentBuilder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Rashi on 27-02-2017.
 */

public class Profile extends AppCompatActivity {
    ImageView phone;
    ImageView mail,location;
    String email, name;
    FirebaseAuth firebaseAuth;
    TextView mailid,person,statustext;
    String status;
    Button button;
    ImageView imageView;
    TextView textView;
    CircleImageView profileimage;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);

        myToolbar.setTitle("TEDxDTU");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.windowBackground));
        myToolbar.setSubtitleTextColor(getResources().getColor(R.color.windowBackground));
        setSupportActionBar(myToolbar);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/qi.otf");
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(myToolbar);
        button = (Button)findViewById(R.id.button);
        textView= (TextView) findViewById(R.id.textview);
        textView.setTypeface(typeface);
        imageView= (ImageView) findViewById(R.id.imageview);

        statustext = (TextView)findViewById(R.id.status);
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        profileimage= (CircleImageView) findViewById(R.id.profile_image);
       try {
           Picasso.with(this)
                   .load(String.valueOf(user.getPhotoUrl()))
                   .placeholder(R.drawable.ic_user)
                   .error(R.drawable.ic_user)
                   .into(profileimage);
       }
       catch (NullPointerException e)
       {

       }


        mailid = (TextView)findViewById(R.id.mailid);
        person = (TextView)findViewById(R.id.personname);
        try {
            email = user.getEmail();
            name = user.getDisplayName();
        }
        catch (NullPointerException e)
        {
            email="";
            name="";
        }

        mailid.setText(email);
        person.setText(name);

        phone= (ImageView)findViewById(R.id.telimage);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String[] phone=new String[2];
                phone[0]="+918373920868";
                phone[1]="+918586096079";
                final AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.alert_label_editor, null);
                builder.setView(dialogView);
                TextView text1= (TextView) dialogView.findViewById(R.id.text1);
                text1.setText(phone[0]);
                TextView text2=(TextView)dialogView.findViewById(R.id.text2);
                text2.setText(phone[1]);
                text1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+phone[0]));
                        startActivity(intent);

                    }
                });
                text2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:"+phone[1]));
                        startActivity(intent);
                    }
                });
              AlertDialog alert= builder.create();
                alert.show();

            }
        });

        mail = (ImageView)findViewById(R.id.mailimage);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailIntentBuilder.from(Profile.this)
                        .to("contact@tedxdtu.in")
                        .subject("Support TEDxDTU")
                        .start();

            }
        });
        findViewById(R.id.writ).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mail.performClick();
            }
        });

        findViewById(R.id.cont).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone.performClick();
            }
        });

        findViewById(R.id.loc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               location.performClick();
            }
        });

        location = (ImageView)findViewById(R.id.locimage);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?saddr=28.720844,77.107121&daddr=28.750075,77.117665"));
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences1 = getSharedPreferences(getString(R.string.MainInfo),MODE_PRIVATE);
        status = sharedPreferences1.getString(getString(R.string.Status), getString(R.string.unregistered));

        if(status.equals("unregistered")){
            statustext.setText("Unregistered");
            button.setText("Registration Form");
            imageView.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
        }
        else if(status.equals("registered")){
            statustext.setText("Awaiting Response");
            button.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            textView.setText("We are currently reviewing your application form and you will be notified once your form is accepted." +
                    "\n\nFor further queries , feel free to contact us.");

        }

        else if(status.equals("can_pay")){
            statustext.setText("Application Accepted");
            button.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Click on the Button below to go to the payment portal.\n\nFill the Email-Id column with the Email-Id with which you've registered .");
            imageView.setVisibility(View.INVISIBLE);
            button.setText("Make Payment");

            AlertDialog.Builder builder=new AlertDialog.Builder(Profile.this);
            builder.setTitle("Payment Not Reflecting");
            builder.setMessage("If while paying you entered an Email-ID which is d" +
                    "ifferent from the current session, it takes us 24-48 hours to reflect back changes into your account.\n\n" +
                    "Please contact us for any further queries.");
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();

        }

        else if(status.equals("paid")){
            statustext.setText("Successfully Registered");
            button.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);


            //Yeh jo content hai yeh fetch karna hai
            final SharedPreferences sharedPreferences=getSharedPreferences(getString(R.string.MainInfo),MODE_PRIVATE);
            final String token=sharedPreferences.getString(getString(R.string.Token),"0");
            //Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
            String url2=String.valueOf(URI.create("http://api.tedxdtu.in/api/ticket"));
            JsonObjectRequest jsonobjectrequest=new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject object=response.getJSONObject("ticket");
                        String[] content = {"A"};
                        content[0] =object.getString("ticketId");
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("TICKET",content[0]);
                        statustext.setText("Ticket No:- "+content[0]);
                        editor.commit();
                        String cont=sharedPreferences.getString("TICKET","00000");

                        QRCodeWriter writer = new QRCodeWriter();
                        try {
                            BitMatrix bitMatrix = writer.encode(cont, BarcodeFormat.QR_CODE, 800, 512);
                            int width = bitMatrix.getWidth();
                            int height = bitMatrix.getHeight();
                            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                            for (int x = 0; x < width; x++) {
                                for (int y = 0; y < height; y++) {
                                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                                }
                            }
                            imageView.setImageBitmap(bmp);

                        } catch (WriterException e) {
                            ((TextView) findViewById(R.id.transactiontext)).setText("TRANSACTION ID:-"+ cont);
                            e.printStackTrace();
                        }
                        //Toast.makeText(Profile.this,content[0], Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        //Toast.makeText(Profile.this, "EXCEPT", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(Profile.this, "There was some error fetching your ticket details. Please contact us for further queries.", Toast.LENGTH_SHORT).show();


                }
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Log.d("TAG", "HUA");
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Content-Type", "application/json");
                    params.put("tedxdtu-token", token);
                    Log.d("TAG", "HUA");
                    return params;
                }

                };

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonobjectrequest);



            String cont=sharedPreferences.getString("TICKET","00000");
            if (!cont.equals("00000"))
            statustext.setText("Ticket No:- "+cont);
            QRCodeWriter writer = new QRCodeWriter();
            try {
                BitMatrix bitMatrix = writer.encode(cont, BarcodeFormat.QR_CODE, 800, 512);
                int width = bitMatrix.getWidth();
                int height = bitMatrix.getHeight();
                Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                    }
                }
                imageView.setImageBitmap(bmp);

            } catch (WriterException e) {
                ((TextView) findViewById(R.id.transactiontext)).setText("TRANSACTION ID:-"+ cont);
                e.printStackTrace();
            }


        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("unregistered")){
                    Intent i = new Intent(getApplicationContext(),RegistrationActivity.class);
                    startActivity(i);
                    finish();
                }
                else if(status.equals("registered")){
                    //Registered pe koi button nahi hai

                }

                else if(status.equals("can_pay")){
                    Intent intent=new Intent();
                    intent.setClass(Profile.this, WebViewCl.class);
                    intent.putExtra("url","https://in.explara.com/e/tedxdtu-2017");
                    startActivity(intent);
                    finish();
                }
                else if(status.equals("paid")){

                    //Paid pe koi button nahi hoga

                }
            }
        });







    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.total) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.alert_connect, null);
            RelativeLayout Web, Face, Twit, Insta, Link, Snap;
            Web = (RelativeLayout) dialogView.findViewById(R.id.Web);
            Face = (RelativeLayout) dialogView.findViewById(R.id.Fac);
            Twit = (RelativeLayout) dialogView.findViewById(R.id.Twit);
            Insta = (RelativeLayout) dialogView.findViewById(R.id.Inst);
            Link = (RelativeLayout) dialogView.findViewById(R.id.Link);
            Snap = (RelativeLayout) dialogView.findViewById(R.id.Snap);
            Web.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(Profile.this, WebViewC.class);
                    intent.putExtra("url", "http://tedxdtu.in/");
                    startActivity(intent);
                }
            });

            Face.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(Profile.this, WebViewC.class);
                    intent.putExtra("url", "https://www.facebook.com/tedxdtu");
                    startActivity(intent);
                }
            });

            Twit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(Profile.this, WebViewC.class);
                    intent.putExtra("url", "https://twitter.com/tedxdtuofficial");
                    startActivity(intent);
                }
            });

            Insta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(Profile.this, WebViewC.class);
                    intent.putExtra("url", "https://www.instagram.com/tedxdtu/");
                    startActivity(intent);
                }
            });

            Link.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(Profile.this, WebViewC.class);
                    intent.putExtra("url", "https://www.linkedin.com/company/tedxdtu");
                    startActivity(intent);
                }
            });

            Snap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("*/*");
                    intent.setPackage("com.snapchat.android");
                    startActivity(Intent.createChooser(intent, "Open Snapchat"));
                }
            });
            builder.setView(dialogView);
            builder.setCancelable(true);
            AlertDialog alert = builder.create();
            alert.show();
            return true;
        }
        else if(item.getItemId()==R.id.partner)
        {
            Intent intent = new Intent();
            intent.setClass(Profile.this, WebViewC.class);
            intent.putExtra("url", "http://tedxdtu.in/#sponsors");
            startActivity(intent);
            return true;
        }

        return false;

    }
}
