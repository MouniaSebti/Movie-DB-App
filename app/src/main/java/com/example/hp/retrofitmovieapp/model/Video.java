package com.example.hp.retrofitmovieapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 30-05-2018.
 */

public class Video {
    @SerializedName("iso_3166_1")
    private String iso_3166_1;
    @SerializedName("iso_639_1")
    private String iso_639_1;
    @SerializedName("name")
    private String name;
    @SerializedName("key")
    private String key;
    @SerializedName("site")
    private String site;
    @SerializedName("id")
    private String id;
    @SerializedName("size")
    private Integer size;
    @SerializedName("type")
    private String type;

    public Video (String iso_3166_1, String iso_639_1, String name, String id,
                 String key, String site, Integer size, String type) {
        this.iso_3166_1 = iso_3166_1;
        this.iso_639_1 = iso_639_1;
        this.name = name;
        this.id = id;
        this.key = key;
        this.id = id;
        this.site = site;
        this.size = size;
        this.type = type;
    }

    public String getiso_3166_1() {
        return iso_3166_1;
    }

    public void setiso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getiso_639_1() {
        return iso_639_1;
    }

    public void setiso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }
    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }
    public String getsite() {
        return site;
    }

    public void setsite(String site) {
        this.site = site;
    }

    public Integer getsize() {
        return size;
    }

    public void setsize(Integer size) {
        this.size = size;
    }

    public String gettype() {
        return type;
    }

    public void settype(String type) {
        this.type = type;
    }




}
