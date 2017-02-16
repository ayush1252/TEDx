package com.example.ayush.bottomnavigation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this,SignInActivity.str.get(0), Toast.LENGTH_SHORT).show();

        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Favourite", R.drawable.ic_favorite_white_24dp, R.color.colorAccent);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("Local", R.drawable.ic_local_dining_white_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Location", R.drawable.ic_location_on_white_24dp, R.color.colorPrimaryDark);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("Profile", R.drawable.ic_local_dining_white_24dp, R.color.colorPrimary);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Payment", R.drawable.ic_local_dining_white_24dp, R.color.colorPrimary);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setDefaultBackgroundColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorAccent));
        bottomNavigation.setInactiveColor(getResources().getColor(R.color.colorBottomNavigationActiveColored));

        bottomNavigation.setForceTint(true);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE);
        bottomNavigation.setCurrentItem(2);
        bottomNavigation.setColored(true);

      bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
          @Override
          public boolean onTabSelected(int position, boolean wasSelected) {

              Intent intent=new Intent(getApplicationContext(),RegistrationActivity.class);
              startActivity(intent);
              return true;
          }
      });

    }

}
