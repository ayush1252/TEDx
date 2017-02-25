package com.example.ayush.bottomnavigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

public class MainActivity extends AppCompatActivity {
    SliderLayout mDemoSlider;
    AHBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        DefaultSliderView defaultSliderView=new DefaultSliderView(this);
        defaultSliderView.image(R.drawable.bebe);
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
                  Intent intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                  startActivity(intent);
                  return true;
              }
              return true;
          }
      });

    }

}
