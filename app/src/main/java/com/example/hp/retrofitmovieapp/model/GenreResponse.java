package com.example.hp.retrofitmovieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 15-05-2018.
 */

public class GenreResponse {

    public class GenresResponse {

        @SerializedName("genres")
        private List<Genre> genres;
        @SerializedName("total_genres")
        private int totalGenres;

       /* @SerializedName("id")
        private Integer id;
        @SerializedName("name")
        private String name;  */

     /*   public int getPage() {
            return page;
        }
        public void setPage(int page) {
            this.page = page;
        } */

        public List<Genre> getGenres() {
            return genres;
        }

        public void setGenres(List<Genre> genres) {
            this.genres = genres;
        }


        public int getTotalGenres() {
            return totalGenres;
        }

        public void setTotalGenres(int totalResults) {
            this.totalGenres = totalGenres;
        }

    }
}
