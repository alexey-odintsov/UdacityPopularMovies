package com.alekso.udacitypopularmovies;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alekso on 24/02/2017.
 */
public class Movie {
    @SerializedName("id")
    private long mId;
    @SerializedName("title")
    private String mTitle;

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
