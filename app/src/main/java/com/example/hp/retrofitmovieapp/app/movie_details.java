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

    TextView movieTitle, movieDesc, movieRating, movieReleasedate, moviePopularity;
    String t,d,r,y;
    // Double p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        movieTitle = (TextView)findViewById(R.id.movie_title);
        movieDesc = (TextView)findViewById(R.id.movie_description);
        movieReleasedate = (TextView)findViewById(R.id.movie_year);
        movieRating = (TextView)findViewById(R.id.movie_rating);
        moviePopularity = (TextView)findViewById(R.id.movie_popularity);

        t = getIntent().getExtras().getString("movie_title");
        d = getIntent().getExtras().getString("movie_description");
        r = getIntent().getExtras().getString("movie_rating");
        y = getIntent().getExtras().getString("movie_year");
     //   p = getIntent().getExtras().getDouble("movie_popularity");


        movieTitle.setText(t);
        movieDesc.setText(d);
        movieReleasedate.setText(y);
        movieRating.setText(r);

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
