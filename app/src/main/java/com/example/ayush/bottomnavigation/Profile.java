package com.example.ayush.bottomnavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Locale;

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
    CircleImageView profileimage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        button = (Button)findViewById(R.id.button);
        statustext = (TextView)findViewById(R.id.status);
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        profileimage= (CircleImageView) findViewById(R.id.profile_image);
        Picasso.with(this)
                .load(String.valueOf(user.getPhotoUrl()))
                .placeholder(R.drawable.ic_user)
                .error(R.drawable.ic_user)
                .into(profileimage);


        mailid = (TextView)findViewById(R.id.mailid);
        person = (TextView)findViewById(R.id.personname);
        Log.d("Profile", String.valueOf(user.getPhotoUrl()));

        email = user.getEmail();
        name = user.getDisplayName();

        mailid.setText(email);
        person.setText(name);

        phone= (ImageView)findViewById(R.id.telimage);
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });

        mail = (ImageView)findViewById(R.id.mailimage);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmailIntentBuilder.from(Profile.this)
                        .to("support@tedxdtu.in")
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
                        Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"));
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences1 = getSharedPreferences(getString(R.string.MainInfo),MODE_PRIVATE);
        status = sharedPreferences1.getString(getString(R.string.Status), getString(R.string.unregistered));
        Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
        if(status.equals("unregistered")){
            statustext.setText("Unregistered");
        }
        else if(status.equals("registered")){
            statustext.setText("Awaiting Response");


        }

        else if(status.equals("make_payment")){
            statustext.setText("Make Payment");


        }

        else if(status.equals("registered")){
            statustext.setText("Successfully Registered");


        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(status.equals("unregistered")){
                    Intent i = new Intent(getApplicationContext(),RegistrationActivity.class);
                    startActivity(i);
                }
                else if(status.equals("waiting")){
                    button.setText("Awaiting Response");
                    Intent i = new Intent(getApplicationContext(),Profile.class);
                    startActivity(i);

                }

                else if(status.equals("make_payment")){
                    button.setText("Payment Portal");
                    Intent i = new Intent(getApplicationContext(),Profile.class);
                    startActivity(i);

                }

                else if(status.equals("registered")){
                    button.setText("Successfully Registered");
                    Intent i = new Intent(getApplicationContext(),Profile.class);
                    startActivity(i);

                }
            }
        });







    }
}
