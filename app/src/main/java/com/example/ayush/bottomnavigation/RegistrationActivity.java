package com.example.ayush.bottomnavigation;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    public static CustomViewPager viewPager;
public static RegistrationForm registrationform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        registrationform=new RegistrationForm();
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar );
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.clearOnTabSelectedListeners();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0)
                    viewPager.setCurrentItem(0);
                else
                    try {
                        TabFragment.next.callOnClick();
                    } catch (Exception e) {
                        viewPager.setCurrentItem(1);
                    }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                return;
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                return;
            }
        });

    }

    private void setupViewPager(CustomViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TabFragment(), "Details");
        adapter.addFragment(new Tab2Fragment(), "Questionaire");
        viewPager.setAdapter(adapter);
        viewPager.setPagingEnabled(false);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();  return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);        }

        @Override
        public int getCount() {
            return mFragmentList.size();

        }
        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
