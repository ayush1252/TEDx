package com.example.ayush.bottomnavigation;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
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

        //Yeh wala part will go into when the user is successfully registered
        String content="ABX12DES234";
        QRCodeWriter writer = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, 800, 512);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            ((ImageView) findViewById(R.id.imageview)).setImageBitmap(bmp);

        } catch (WriterException e) {
            ((TextView) findViewById(R.id.transactiontext)).setText("TRANSACTION ID:-"+content);
            e.printStackTrace();
        }
        //Yahan se yahan tak
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
                //alert.getWindow().setLayout(500, 300);

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
                        Uri.parse("http://maps.google.com/maps?saddr=28.720844,77.107121&daddr=28.750075,77.117665"));
                startActivity(intent);
            }
        });
        SharedPreferences sharedPreferences1 = getSharedPreferences(getString(R.string.MainInfo),MODE_PRIVATE);
        status = sharedPreferences1.getString(getString(R.string.Status), getString(R.string.unregistered));
       // Toast.makeText(this, status, Toast.LENGTH_SHORT).show();
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
