package com.example.hp.retrofitmovieapp.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.example.hp.retrofitmovieapp.R;
import com.example.hp.retrofitmovieapp.adapter.MoviesAdapter;
import com.example.hp.retrofitmovieapp.model.Movie;
import com.example.hp.retrofitmovieapp.model.MovieResponse;
import com.example.hp.retrofitmovieapp.rest.ApiClient;
import com.example.hp.retrofitmovieapp.rest.ApiInterface;

import java.util.List;

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

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY from themoviedb.org first!", Toast.LENGTH_LONG).show();
            return;
        }

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // add divider between list items
        MyDividerItemDecoration dividerItemDecoration = new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 6, Color.WHITE);
        recyclerView.addItemDecoration(dividerItemDecoration);


        // change actionbar color
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#666666")));

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MovieResponse.MoviesResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse.MoviesResponse>() {
            @Override
            public void onResponse(Call<MovieResponse.MoviesResponse> call, Response<MovieResponse.MoviesResponse> response) {
                int statusCode = response.code();
                hidePDialog();
                final List<Movie> movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));

                // recycler view item click listener
                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        String Mt=movies.get(position).getTitle();
                        String Md=movies.get(position).getOverview();
                        Log.i("TEST",""+Mt);
                       // Toast.makeText(getApplicationContext(), "Selected!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, movie_details.class);
                        intent.putExtra("movie_title", Mt);
                        intent.putExtra("movie_description", Md);
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
}
