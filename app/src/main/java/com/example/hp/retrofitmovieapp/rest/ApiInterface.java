package com.example.hp.retrofitmovieapp.rest;

import com.example.hp.retrofitmovieapp.model.Genre;
import com.example.hp.retrofitmovieapp.model.GenreResponse;
import com.example.hp.retrofitmovieapp.model.MovieResponse;
import com.example.hp.retrofitmovieapp.model.VideoResponse;

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

    @GET("movie/popular")
    Call<MovieResponse.MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

  /*  @GET("movie/latest")
    Call<MovieResponse.MoviesResponse> getLatestMovies(@Query("api_key") String apiKey); */

    @GET("movie/upcoming")
    Call<MovieResponse.MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<MovieResponse.MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

   /* @GET("movie/{id}")
    Call<MovieResponse.MoviesResponse> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey); */

    @GET("movie/{id}/videos")
    Call<VideoResponse.VideosResponse> getMovieVideos(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("genre/movie/list")
    Call<GenreResponse.GenresResponse> getMovieGenre(@Query("api_key") String apiKey);

    /*  call the primary movie video method:

        http://api.themoviedb.org/3/movie/131634/videos?api_key=###

        Which you can do with append_to_response as well (to get everything in a single HTTP call):

        http://api.themoviedb.org/3/movie/131634?api_key=###&append_to_response=videos*/

}
