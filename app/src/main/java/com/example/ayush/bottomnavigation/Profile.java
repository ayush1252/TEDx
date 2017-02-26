package com.example.ayush.bottomnavigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Rashi on 27-02-2017.
 */

public class Profile extends AppCompatActivity {
    ImageView phone;
    ImageView mail,location;
    SharedPreferences sharedPreferences;
    String email, name;
    FirebaseAuth firebaseAuth;
    TextView mailid,person,statustext;
    String status;
    Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        button = (Button)findViewById(R.id.button);
        statustext = (TextView)findViewById(R.id.status);
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();

        sharedPreferences= getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();

        mailid = (TextView)findViewById(R.id.mailid);
        person = (TextView)findViewById(R.id.personname);

        email = sharedPreferences.getString("username","abc");
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

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setType("plain/text");
                sendIntent.setData(Uri.parse("lookforayush@gmail.com"));
                sendIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
                sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[] { "lookforayush@gmail.com" });
                //sendIntent.putExtra(Intent.EXTRA_SUBJECT, "test");
                //sendIntent.putExtra(Intent.EXTRA_TEXT, "hello. this is a message sent from my demo app :-)");
                startActivity(sendIntent);
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
        SharedPreferences sharedPreferences1 = getSharedPreferences(String.valueOf(R.string.MainInfo),MODE_PRIVATE);
        status = sharedPreferences1.getString(String.valueOf(R.string.Status), String.valueOf(R.string.unregistered));


        if(status.equals("unregistered")){
            statustext.setText("Unregistered");
        }
        else if(status.equals("waiting")){
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
