package com.alekso.udacitypopularmovies.domain.model;

/**
 * Created by alekso on 01/05/2017.
 */

public class Video {
    private String mId;
    private String mTitle;
    private String mKey;

    public Video(String id, String key, String title) {
        setId(id);
        setKey(key);
        setTitle(title);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        mKey = key;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
