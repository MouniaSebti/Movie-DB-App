package com.example.hp.retrofitmovieapp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.hp.retrofitmovieapp.R;
import com.example.hp.retrofitmovieapp.adapter.MoviesAdapter;
import com.example.hp.retrofitmovieapp.model.Movie;
import com.example.hp.retrofitmovieapp.model.MovieResponse;
import com.example.hp.retrofitmovieapp.rest.ApiClient;
import com.example.hp.retrofitmovieapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.provider.Contacts.SettingsColumns.KEY;
import static android.support.v7.recyclerview.R.attr.layoutManager;
import static android.support.v7.widget.RecyclerView.HORIZONTAL;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "6573b960a70ce01602a6f291669ba331";
    String TagTop, TagPop, TagUp, TagNow;
    MoviesAdapter mAdapter;

    private ProgressDialog pDialog;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Movie> movies = null;

        //Adapter
        mAdapter=new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext());

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // add divider between list items
        MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 6, Color.WHITE);
        recyclerView.addItemDecoration(dividerItemDecoration);


        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }


        // change actionbar color
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#FF4081")));

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse.MoviesResponse> call_TopRatedMovies = apiService.getTopRatedMovies(API_KEY);
        Call<MovieResponse.MoviesResponse> call_PopularMovies = apiService.getPopularMovies(API_KEY);
        Call<MovieResponse.MoviesResponse> call_UpcomingMovies = apiService.getUpcomingMovies(API_KEY);
        Call<MovieResponse.MoviesResponse> call_NowPlayingMovies = apiService.getNowPlayingMovies(API_KEY);

        TagTop=getIntent().getExtras().getString("top");
        TagPop=getIntent().getExtras().getString("popular");
        TagUp=getIntent().getExtras().getString("upcoming");
        TagNow=getIntent().getExtras().getString("now_playing");

        //  Log.i("GET INTENT",""+TagTop);

        // retrieve just the top rated movies
        if(Objects.equals(TagTop, "ON")) {

            getSupportActionBar().setTitle("Top Rated");

            call_TopRatedMovies.enqueue(new Callback<MovieResponse.MoviesResponse>() {
                @Override
                public void onResponse(Call<MovieResponse.MoviesResponse> call, Response<MovieResponse.MoviesResponse> response) {
                    int statusCode = response.code();
                    hidePDialog();
                    final List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(mAdapter=new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                    mAdapter.notifyDataSetChanged();

                    // recycler view item click listener
                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Integer Mid = movies.get(position).getId(); // ID
                            String Mt = movies.get(position).getTitle();
                            String Mr = "Rating: " + movies.get(position).getVoteAverage().toString() + "/10";
                            String Md = movies.get(position).getOverview();
                            String Mpp = movies.get(position).getPosterPath();
                            String Ml = "Language: " + movies.get(position).getOriginalLanguage();
                            List<String> Mg = new ArrayList<String>();
                            Mg = movies.get(position).getGenreIds();
                            String My = "Release Date: " + movies.get(position).getReleaseDate();
                            Boolean Mv = movies.get(position).getVideo();

                            Intent intent = new Intent(MainActivity.this, movie_details.class);
                            intent.putExtra("movie_id", Mid);
                            intent.putExtra("movie_title", Mt);
                            intent.putExtra("movie_description", Md);
                            intent.putExtra("movie_rating", Mr);
                            intent.putExtra("movie_year", My);
                            intent.putExtra("movie_poster", Mpp);

                            intent.putExtra("movie_video", Mv);
                            // Log.i("MOVIE ID"," "+Mid);

                            intent.putExtra("movie_original_language", Ml);
                            intent.putStringArrayListExtra("movie_genre", (ArrayList<String>) Mg);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

                }

                @Override
                public void onFailure(Call<MovieResponse.MoviesResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }

        // retrieve just the popular movies
        else if(Objects.equals(TagPop, "ON")) {

            getSupportActionBar().setTitle("Popular");

            call_PopularMovies.enqueue(new Callback<MovieResponse.MoviesResponse>() {
                @Override
                public void onResponse(Call<MovieResponse.MoviesResponse> call, Response<MovieResponse.MoviesResponse> response) {
                    int statusCode = response.code();
                    hidePDialog();
                    final List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(mAdapter=new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                    mAdapter.notifyDataSetChanged();

                    // recycler view item click listener
                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Integer Mid = movies.get(position).getId(); // ID
                            String Mt = movies.get(position).getTitle();
                            String Mr = "Rating: " + movies.get(position).getVoteAverage().toString() + "/10";
                            String Md = movies.get(position).getOverview();
                            String Mpp = movies.get(position).getPosterPath();
                            String Ml = "Language: " + movies.get(position).getOriginalLanguage();
                            List<String> Mg = new ArrayList<String>();
                            Mg = movies.get(position).getGenreIds();
                            String My = "Release Date: " + movies.get(position).getReleaseDate();
                            Boolean Mv = movies.get(position).getVideo();

                            Intent intent = new Intent(MainActivity.this, movie_details.class);
                            intent.putExtra("movie_id", Mid);
                            intent.putExtra("movie_title", Mt);
                            intent.putExtra("movie_description", Md);
                            intent.putExtra("movie_rating", Mr);
                            intent.putExtra("movie_year", My);
                            intent.putExtra("movie_poster", Mpp);

                            intent.putExtra("movie_video", Mv);
                            // Log.i("MOVIE ID"," "+Mid);

                            intent.putExtra("movie_original_language", Ml);
                            intent.putStringArrayListExtra("movie_genre", (ArrayList<String>) Mg);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

                }

                @Override
                public void onFailure(Call<MovieResponse.MoviesResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }

        // retrieve just the upcoming movies
        else if(Objects.equals(TagUp, "ON")) {

        //    Log.i("GET INTENT",""+TagUp);
            getSupportActionBar().setTitle("Upcoming");

            call_UpcomingMovies.enqueue(new Callback<MovieResponse.MoviesResponse>() {
                @Override
                public void onResponse(Call<MovieResponse.MoviesResponse> call, Response<MovieResponse.MoviesResponse> response) {
                    int statusCode = response.code();
                    hidePDialog();
                    final List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(mAdapter=new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                    mAdapter.notifyDataSetChanged();

                    // recycler view item click listener
                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Integer Mid = movies.get(position).getId(); // ID
                            String Mt = movies.get(position).getTitle();
                            String Mr = "Rating: " + movies.get(position).getVoteAverage().toString() + "/10";
                            String Md = movies.get(position).getOverview();
                            String Mpp = movies.get(position).getPosterPath();
                            String Ml = "Language: " + movies.get(position).getOriginalLanguage();
                            List<String> Mg = new ArrayList<String>();
                            Mg = movies.get(position).getGenreIds();
                            String My = "Release Date: " + movies.get(position).getReleaseDate();
                            Boolean Mv = movies.get(position).getVideo();

                            Intent intent = new Intent(MainActivity.this, movie_details.class);
                            intent.putExtra("movie_id", Mid);
                            intent.putExtra("movie_title", Mt);
                            intent.putExtra("movie_description", Md);
                            intent.putExtra("movie_rating", Mr);
                            intent.putExtra("movie_year", My);
                            intent.putExtra("movie_poster", Mpp);

                            intent.putExtra("movie_video", Mv);
                            // Log.i("MOVIE ID"," "+Mid);

                            intent.putExtra("movie_original_language", Ml);
                            intent.putStringArrayListExtra("movie_genre", (ArrayList<String>) Mg);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

                }

                @Override
                public void onFailure(Call<MovieResponse.MoviesResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }

        // retrieve just the now playing movies
        else if(Objects.equals(TagNow, "ON")) {

         //   Log.i("GET INTENT",""+TagNow);
            getSupportActionBar().setTitle("Now Playing");

            call_NowPlayingMovies.enqueue(new Callback<MovieResponse.MoviesResponse>() {
                @Override
                public void onResponse(Call<MovieResponse.MoviesResponse> call, Response<MovieResponse.MoviesResponse> response) {
                    int statusCode = response.code();
                    hidePDialog();
                    final List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(mAdapter=new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                    mAdapter.notifyDataSetChanged();

                    // recycler view item click listener
                    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                        @Override
                        public void onClick(View view, int position) {
                            Integer Mid = movies.get(position).getId(); // ID
                            String Mt = movies.get(position).getTitle();
                            String Mr = "Rating: " + movies.get(position).getVoteAverage().toString() + "/10";
                            String Md = movies.get(position).getOverview();
                            String Mpp = movies.get(position).getPosterPath();
                            String Ml = "Language: " + movies.get(position).getOriginalLanguage();
                            List<String> Mg = new ArrayList<String>();
                            Mg = movies.get(position).getGenreIds();
                            String My = "Release Date: " + movies.get(position).getReleaseDate();
                            Boolean Mv = movies.get(position).getVideo();

                            Intent intent = new Intent(MainActivity.this, movie_details.class);
                            intent.putExtra("movie_id", Mid);
                            intent.putExtra("movie_title", Mt);
                            intent.putExtra("movie_description", Md);
                            intent.putExtra("movie_rating", Mr);
                            intent.putExtra("movie_year", My);
                            intent.putExtra("movie_poster", Mpp);

                            intent.putExtra("movie_video", Mv);
                            // Log.i("MOVIE ID"," "+Mid);

                            intent.putExtra("movie_original_language", Ml);
                            intent.putStringArrayListExtra("movie_genre", (ArrayList<String>) Mg);
                            startActivity(intent);
                        }

                        @Override
                        public void onLongClick(View view, int position) {

                        }
                    }));

                }

                @Override
                public void onFailure(Call<MovieResponse.MoviesResponse> call, Throwable t) {
                    // Log error here since request failed
                    Log.e(TAG, t.toString());
                }
            });
        }
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
