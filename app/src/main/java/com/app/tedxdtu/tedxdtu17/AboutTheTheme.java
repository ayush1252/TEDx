package com.app.tedxdtu.tedxdtu17;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

public class AboutTheTheme extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_theme);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("");
        myToolbar.setTitleTextColor(getResources().getColor(R.color.windowBackground));
        myToolbar.setSubtitleTextColor(getResources().getColor(R.color.windowBackground));
        setSupportActionBar(myToolbar);
        Typeface typeface=Typeface.createFromAsset(getAssets(), "fonts/qi.otf");


        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        setSupportActionBar(myToolbar);
        String abt="Referring to the region between the absolutes of black and white, " +
                "our theme <b>Because Grey Matters</b> represents the complexity of human lives, minds and situations. " +
                "Grey represents a multitude of hues within itself, each succinctly unique in its identity yet inherently similar to its accompanying shades. " +
                "Similarly, all of us are our own shade and hue but are, nonetheless, deeply connected to each other by the thread of humanity." +"<br>" +
                "<br>" +
                "<br>However, our inhibitions and inflexibility to accept those in the grey region leads numerous people to be ostracised and harassed. Most oppressed people, with issues that many don't lend an ear to, often find themselves battling a lone war and are oft left in the grey. A glimpse into the ongoing humanitarian conflicts prevailing the planet currently only attest to this harsh truth.<br>" +
                "<br>" +
                "<br>Grey Matter also alludes to the importance of broad minded thinking in a world dominated by dogmatic and propagandist views. Pushing the boundaries of innovation, creativity, art, music and innumerable other fields, <b>Because Grey Matters</b> encourages everyone to express themselves articulately and realise that humanity fundamentally lies in this overlap of colours and regions and not just in polarizing extremes.";

        textView= (TextView) findViewById(R.id.mytext);
        textView.setText(Html.fromHtml(abt));
        textView.setTypeface(typeface);
    }
}
