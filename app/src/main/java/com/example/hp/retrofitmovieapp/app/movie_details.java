package com.example.hp.retrofitmovieapp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.retrofitmovieapp.R;
import com.example.hp.retrofitmovieapp.model.Genre;
import com.example.hp.retrofitmovieapp.model.GenreResponse;
import com.example.hp.retrofitmovieapp.model.Movie;
import com.example.hp.retrofitmovieapp.model.MovieResponse;
import com.example.hp.retrofitmovieapp.rest.ApiClient;
import com.example.hp.retrofitmovieapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hp on 04-05-2018.
 */

public class movie_details extends AppCompatActivity {

    TextView movieTitle, movieDesc, movieRating, movieReleasedate, moviePopularity;
    String t,d,r,y,genrename;
    TextView movieGenre;
    ArrayList<String> g=new ArrayList<String>();
    Integer id;
    ArrayList<Integer> mgenre=new ArrayList<Integer>();
    Integer p;

    private final static String API_KEY = "6573b960a70ce01602a6f291669ba331";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        movieTitle = (TextView)findViewById(R.id.movie_title);
        movieDesc = (TextView)findViewById(R.id.movie_description);
        movieReleasedate = (TextView)findViewById(R.id.movie_year);
        movieRating = (TextView)findViewById(R.id.movie_rating);
    //  moviePopularity = (TextView)findViewById(R.id.movie_popularity);
        movieGenre = (TextView)findViewById(R.id.movie_genre);


        id= getIntent().getExtras().getInt("movie_id"); // id of movie, not to confuse with the id of the genre!
        t = getIntent().getExtras().getString("movie_title");
        d = getIntent().getExtras().getString("movie_description");
        r = getIntent().getExtras().getString("movie_rating");
        y = getIntent().getExtras().getString("movie_year");
        mgenre = getIntent().getIntegerArrayListExtra("movie_genre"); //movie_genre
        // p = getIntent().getExtras().getDouble("movie_popularity");

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GenreResponse.GenresResponse> call = apiService.getMovieGenre(API_KEY);
        call.enqueue(new Callback<GenreResponse.GenresResponse>() {
            @Override
            public void onResponse(Call<GenreResponse.GenresResponse> call, Response<GenreResponse.GenresResponse> response) {
                int statusCode = response.code();
                Log.i("Status"," "+statusCode);

                final List<Genre> genres = response.body().getGenres();


                for(int j=0; j<mgenre.size(); j++)
                {
                    genrename="";
                for(int i=0;i<genres.size();i++) {

                    if(genres.get(i).getId().intValue() == mgenre.get(j).intValue())
                    {
                        genrename=genres.get(i).getName();
                      
                        if(j<(mgenre.size()-1)) {
                            movieGenre.append(String.valueOf(genrename));
                            movieGenre.append(", ");
                        }
                        else if(j==(mgenre.size()-1)) {
                            movieGenre.append(String.valueOf(genrename));
                            movieGenre.append(" ");
                        }

                     // Log.i("TEST","NAME: "+genrename);

                    }
                  }
                }
            }

            @Override
            public void onFailure(Call<GenreResponse.GenresResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("Error", "Call failed!");
            }
        });


        // set values to text fields
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
