package com.test.candidates.view.activities.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.test.candidates.R;
import com.test.candidates.adapters.FragmentAdapter;
import com.test.candidates.model.Model;
import com.test.candidates.presenter.Presenter;
import com.test.candidates.view.fragments.CandidateFragment;
import com.test.candidates.view.fragments.SelectedFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Presenter.View, CandidateFragment.CallBackListener,SelectedFragment.CallBackListener{

    private   ViewPager2 viewPager;
    TabLayout tabLayout;
    ArrayList<Fragment> fragments;
    private final String[] titles = new String[]{"Candidates", "Selected"};
    private Model global;
    public Presenter presenter;
    private  int indexClicked;
    private static final int ACTIVITY_REQUEST_CODE = 1;
    private CandidateFragment candidateFragment;
    private SelectedFragment selectedFragment;
    private int currentPosition;

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

        presenter = new Presenter(this,MainActivity.this);

        fragments =new ArrayList<>();

        candidateFragment = new CandidateFragment();
        selectedFragment = new SelectedFragment();

        fragments.add(candidateFragment);
        fragments.add(selectedFragment);

        FragmentAdapter pagerAdapter = new FragmentAdapter(MainActivity.this, getApplicationContext(), fragments);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager,(tab, position) -> tab.setText(titles[position])).attach();

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if(currentPosition < position) {
                } else if(currentPosition > position){
                }
                currentPosition = position; // Update current position
            }
        });

        candidateFragment.setPresenter(presenter);
        selectedFragment.setPresenter(presenter);

        Toast.makeText(MainActivity.this,"Please wait for a while...",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void updateCandidateList() {
       candidateFragment.refreshAdaptor();
    }

    @Override
    public void updateSelectedList() {
        selectedFragment.refreshAdaptor();
    }

    @Override
    public void showDetails(int indexClicked) {
        this.indexClicked = indexClicked;

        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra("send",presenter.getOrginalData().get(indexClicked));
        MainActivity.this.startActivityForResult(intent,ACTIVITY_REQUEST_CODE);
    }

  /*  @Override
    public void onCallBack(int indexClicked,Presenter presenter) {

     //   Model send = (Model) getIntent().getSerializableExtra("model");
        this.presenter = presenter;
        this.indexClicked = indexClicked;

        Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
        intent.putExtra("send",presenter.getOrginalData().get(indexClicked));
        MainActivity.this.startActivityForResult(intent,ACTIVITY_REQUEST_CODE);
    }*/

    @Override
    public void fragmentLoaded() {
        candidateFragment.initiateAdaptors();
        presenter.loadData();
    }

    @Override
    public void fragmentSelectLoaded() {
        selectedFragment.initiateAdaptors();
        presenter.loadSelectedData();
    }

    @Override
    public void deleteRow(int position) {
        presenter.modifyOriginalData(position);
        selectedFragment.notifyItemRemoved(position);
 //       presenter.loadSelectedData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                global = (Model)data.getSerializableExtra("result");
                if(global.isSelected()) presenter.getOrginalData().get(indexClicked).setSelected(true);
                else presenter.getOrginalData().get(indexClicked).setSelected(false);
                /*if (selectedFragment == null) {
                    selectedFragment = new SelectedFragment();
                }*/
                selectedFragment.setPresenter(presenter);


            }
            if(resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}