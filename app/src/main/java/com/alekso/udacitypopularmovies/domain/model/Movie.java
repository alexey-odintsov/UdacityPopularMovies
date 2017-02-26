package com.alekso.udacitypopularmovies.domain.model;

/**
 * Created by alekso on 24/02/2017.
 */
public class Movie {
    private long mId;
    private String mTitle;
    private String mOverview;
    private String mPoster;

    public Movie(Builder builder) {
        this.mId = builder.id;
        this.mTitle = builder.title;
        this.mOverview = builder.overview;
        this.mPoster = builder.poster;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPoster() {
        return mPoster;
    }

    /**
     *
     */
    public static class Builder {

        private long id = 0;
        private String title = "";
        private String overview = "";
        private String poster;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setOverview(String overview) {
            this.overview = overview;
            return this;
        }

        public Builder setPoster(String poster) {
            this.poster = poster;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
