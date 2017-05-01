package com.alekso.udacitypopularmovies.domain.model;

/**
 * Created by alekso on 01/05/2017.
 */

public class Video {
    private String mLink;
    private String mTitle;

    public Video(String link, String title) {
        setLink(link);
        setTitle(title);
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
