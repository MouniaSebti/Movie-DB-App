package com.example.hp.retrofitmovieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hp on 30-05-2018.
 */

public class VideoResponse {

    public class VideosResponse {

        @SerializedName("id")
        private Integer id;
        @SerializedName("total_results")
        private int totalResults;
        @SerializedName("results")
        private List<Video> results;


        public int getid() {
            return id;
        }

        public void setid(int id) {
            this.id = id;
        }

        public List<Video> getResults() {
            return results;
        }

        public void setResults(List<Video> results) {
            this.results = results;
        }

    }
}
