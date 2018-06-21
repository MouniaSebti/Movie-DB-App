package com.example.hp.retrofitmovieapp.app;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.hp.retrofitmovieapp.R;
import com.example.hp.retrofitmovieapp.model.Genre;
import com.example.hp.retrofitmovieapp.model.GenreResponse;
import com.example.hp.retrofitmovieapp.model.Video;
import com.example.hp.retrofitmovieapp.model.VideoResponse;
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

    TextView movieTitle, movieDesc, movieRating, movieReleasedate, moviePopularity, movieOriginalLanguage,movieGenre;
    ImageView moviePoster;
    String t,d,r,y,l,pp,genrename,vidPath;
    ArrayList<String> g=new ArrayList<String>();
    Button trailer;
    Integer id;
    ArrayList<Integer> mgenre=new ArrayList<Integer>();
    Integer p;
    MediaController mc;
    Intent i;

    private final static String API_KEY = "6573b960a70ce01602a6f291669ba331";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_details);

        moviePoster=(ImageView)findViewById(R.id.movie_poster);
        movieTitle = (TextView)findViewById(R.id.movie_title);
        movieDesc = (TextView)findViewById(R.id.movie_description);
        movieReleasedate = (TextView)findViewById(R.id.movie_year);
        movieRating = (TextView)findViewById(R.id.movie_rating);
    //  moviePopularity = (TextView)findViewById(R.id.movie_popularity);
        movieGenre = (TextView)findViewById(R.id.movie_genre);
        movieOriginalLanguage = (TextView)findViewById(R.id.movie_original_language);
      //  trailer =(VideoView) findViewById(R.id.trailer);
        mc = new MediaController(this);
        trailer = (Button) findViewById(R.id.trailer);

        id= getIntent().getExtras().getInt("movie_id"); // id of movie, not to confuse with the id of the genre!
        t = getIntent().getExtras().getString("movie_title");
        d = getIntent().getExtras().getString("movie_description");
        r = getIntent().getExtras().getString("movie_rating");
        y = getIntent().getExtras().getString("movie_year");
        l = getIntent().getExtras().getString("movie_original_language");
        pp = getIntent().getExtras().getString("movie_poster");
        mgenre = getIntent().getIntegerArrayListExtra("movie_genre"); //movie_genre
        // p = getIntent().getExtras().getDouble("movie_popularity");

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<GenreResponse.GenresResponse> call_genre = apiService.getMovieGenre(API_KEY); // Get the movie genres
        call_genre.enqueue(new Callback<GenreResponse.GenresResponse>() {
            @Override
            public void onResponse(Call<GenreResponse.GenresResponse> call_genre, Response<GenreResponse.GenresResponse> response) {
                int statusCode = response.code();
                Log.i("Status Genres"," "+statusCode);

                final List<Genre> genres = response.body().getGenres();

                movieGenre.setText("Genre: ");
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


        Call<VideoResponse.VideosResponse> call_videos = apiService.getMovieVideos(id, API_KEY); // Get the movie's available trailers
        call_videos.enqueue(new Callback<VideoResponse.VideosResponse>() {

            @Override
            public void onResponse(Call<VideoResponse.VideosResponse> call, Response<VideoResponse.VideosResponse> response) {
                int statusCode = response.code();
                Log.i("Status Videos"," "+statusCode);

                final List<Video> videos = response.body().getResults();

                videos.get(0).getkey();

              //  vidPath="http://youtube.com/embed/"+videos.get(0).getkey()+"?autoplay=1&vq=small";
              // vidPath="http://youtube.com/watch?v="+videos.get(0).getkey();

                // Go to get(1) if first trailer isn't available and so on and so forth..

                trailer.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        watchTrailer(movie_details.this,videos.get(0).getkey());

                     /*   i = new Intent(movie_details.this, MovieTrailer.class);
                        i.putExtra("movie_trailer", vidPath);
                        startActivity(i); */


                    }
                });

             /* trailer.setVideoURI(Uri.parse(vidPath));
                trailer.setMediaController(mc);
                trailer.requestFocus();
                trailer.start();

                Log.i("VIDEOS","SIZE OF "+id+" is: " +videos.size());  */

            }

                @Override
                public void onFailure(Call<VideoResponse.VideosResponse> call_videos, Throwable t) {
                    // Log error here since request failed
                    Log.e("Error", "Call failed!");
                }

            });


        // set values to text fields
        movieTitle.setText(t);
        movieDesc.setText(d);
        movieReleasedate.setText(y);
        movieRating.setText(r);
        movieOriginalLanguage.setText(l);

       // Poster of the movie!!!
       Glide.with(this)
                .load(pp)
                .into(moviePoster); //holder.poster

        // change actionbar color
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#FF4081")));
    }

    public static void watchTrailer(Context context, String id){
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + id));
        try {
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }
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
