package com.test.candidates.view.activities.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.candidates.R;
import com.test.candidates.model.Model;
import com.test.candidates.presenter.Presenter;

import java.util.List;

public class DetailsActivity extends AppCompatActivity{

    ImageView imageView;
    TextView name,address,tel,dob,email;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if(getSupportActionBar() != null)   //null check
        {
            getSupportActionBar().setDisplayShowTitleEnabled(false);//show no app name
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button
        }

        initUI();
   }

    private void initUI() {

        model = (Model) getIntent().getSerializableExtra("selected_data");

        imageView = findViewById(R.id.imageView);
        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        tel = findViewById(R.id.tel);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);

        Glide.with(this)
                .load(model.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        String nametmp = model.getTitle() + " " + model.getFirstName() + " " + model.getLastName();
        name.setText(nametmp);
        address.setText(model.getAddress());
        tel.setText(model.getTelephone());
        dob.setText(model.getDob());
        email.setText(model.getEmailAddress());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_btn, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        try {
            if (item.getTitle()!=null &&  item.getTitle().equals("Select") || item.getTitle().equals("Selected"))
            {
                if (model.isSelected())
                {
                    item.setTitle("Select");
                    model.setSelected(false);
                }
                else
                {
                    item.setTitle("Selected");
                    model.setSelected(true);
                }

                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onOptionsItemSelected(item);
    }
}
