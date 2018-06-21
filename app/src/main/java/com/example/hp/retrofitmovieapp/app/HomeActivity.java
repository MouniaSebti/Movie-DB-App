package com.example.hp.retrofitmovieapp.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.hp.retrofitmovieapp.R;

/**
 * Created by hp on 08-06-2018.
 */

//Cardview
public class HomeActivity extends AppCompatActivity {

    Button top,popular,upcoming,nowplaying;
    Intent i_top,i_pop, i_up,i_now;
    String popular_movies="OFF", top_movies="OFF", upcoming_movies="OFF", nowplaying_movies="OFF";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        // change actionbar color
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#FF4081")));

        top = (Button) findViewById(R.id.topratedmovies);
        popular = (Button) findViewById(R.id.popularmovies);
        upcoming = (Button) findViewById(R.id.upcoming);
        nowplaying = (Button) findViewById(R.id.nowplaying);

        top.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                top_movies="ON";

                i_top = new Intent(HomeActivity.this, MainActivity.class);
                i_top.putExtra("top", top_movies); // the title of the toolbar should change
                startActivity(i_top);

            }
        });

        popular.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                popular_movies="ON";

                i_pop = new Intent(HomeActivity.this, MainActivity.class);
                i_pop.putExtra("popular", popular_movies);
                startActivity(i_pop);


            }
        });

        upcoming.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                upcoming_movies="ON";

                i_up = new Intent(HomeActivity.this, MainActivity.class);
                i_up.putExtra("upcoming", upcoming_movies);
                 startActivity(i_up);


            }
        });

        nowplaying.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                nowplaying_movies="ON";

                i_now = new Intent(HomeActivity.this, MainActivity.class);
                i_now.putExtra("now_playing", nowplaying_movies);
                startActivity(i_now);


            }
        });
    }

    }
