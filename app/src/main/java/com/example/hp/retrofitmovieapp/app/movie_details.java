package com.example.hp.retrofitmovieapp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.hp.retrofitmovieapp.R;

/**
 * Created by hp on 04-05-2018.
 */

public class movie_details extends AppCompatActivity {

    TextView movieTitle, movieDesc;
    String t,d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        movieTitle = (TextView)findViewById(R.id.Mtitle);
        movieDesc = (TextView)findViewById(R.id.Mdescription);

        t = getIntent().getExtras().getString("movie_title");
        d = getIntent().getExtras().getString("movie_description");

        movieTitle.setText(t);
        movieDesc.setText(d);

        // change actionbar color
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#666666")));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
