package com.test.candidates.view.activities.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.test.candidates.R;
import com.test.candidates.adapters.FragmentAdapter;
import com.test.candidates.model.Model;
import com.test.candidates.presenter.Presenter;
import com.test.candidates.view.fragments.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Presenter.View,HomeFragment.CallBackListener {

    ViewPager2 viewPager;
    TabLayout tabLayout;
    ArrayList<Fragment> fragments;
    private final String[] titles = new String[]{"Candidates", "Selected"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getSupportActionBar() != null)   //null check
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        viewPager = (ViewPager2) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tablayout);

        fragments =new ArrayList<>();

        fragments.add(new HomeFragment());
        fragments.add(new HomeFragment());

        FragmentAdapter pagerAdapter = new FragmentAdapter(MainActivity.this, getApplicationContext(), fragments);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> tab.setText(titles[position])).attach();

    }

    @Override
    public void addData(List<Model> data) {

    }

    @Override
    public void showDetails(Model model) {

    }

    @Override
    public void onCallBack(Model model) {

        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra("selected_data", model);
        startActivity(intent);
    }
}