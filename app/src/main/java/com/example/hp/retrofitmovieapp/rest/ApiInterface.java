package com.example.hp.retrofitmovieapp.rest;

import com.example.hp.retrofitmovieapp.model.Genre;
import com.example.hp.retrofitmovieapp.model.GenreResponse;
import com.example.hp.retrofitmovieapp.model.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by hp on 19-04-2018.
 */

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<MovieResponse.MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}")
    Call<MovieResponse.MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<GenreResponse.GenresResponse> getMovieGenre(@Query("api_key") String apiKey);

}
