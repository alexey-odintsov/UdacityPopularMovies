package com.alekso.udacitypopularmovies.domain.model;

/**
 * Created by alekso on 24/02/2017.
 */
public class Movie {
    private long mId;
    private String mTitle;
    private String mOriginalTitle;
    private String mOverview;
    private String mPoster;
    private String mBackdrop;
    private int mDuration;
    private String mReleaseDate; // // TODO: 26/02/2017 consider to use unix timestamp
    private float mRating;

    public Movie(Builder builder) {
        this.mId = builder.id;
        this.mTitle = builder.title;
        this.mOriginalTitle = builder.originalTitle;
        this.mOverview = builder.overview;
        this.mPoster = builder.poster;
        this.mDuration = builder.duration;
        this.mReleaseDate = builder.releaseDate;
        this.mRating = builder.rating;
        this.mBackdrop = builder.backdrop;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPoster() {
        return mPoster;
    }

    public int getDuration() {
        return mDuration;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public float getRating() {
        return this.mRating;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    /**
     *
     */
    public static class Builder {

        private long id = 0;
        private String title = "";
        private String originalTitle = "";
        private String overview = "";
        private String poster = "";
        private String backdrop = "";
        private int duration = 0;
        private String releaseDate = "1900-01-01";
        private float rating = 0.0f;

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

        public Builder setOriginalTitle(String originalTitle) {
            this.originalTitle = originalTitle;
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

        public Builder setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder setReleaseDate(String date) {
            this.releaseDate = date;
            return this;
        }

        public Builder setRating(float rating) {
            this.rating = rating;
            return this;
        }

        public Builder setBackdrop(String backdrop) {
            this.backdrop = backdrop;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
