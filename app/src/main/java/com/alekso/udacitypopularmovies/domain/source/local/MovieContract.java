package com.alekso.udacitypopularmovies.domain.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.alekso.udacitypopularmovies.BuildConfig;

/**
 * Created by alekso on 16/04/2017.
 */

public final class MovieContract {

    public static final String CONTENT_AUTHORITY = BuildConfig.APPLICATION_ID;
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";

    public static abstract class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static final String TABLE = "favorite_movies";
        public static final String C_TITLE = "title";
        public static final String C_MOVIE_ID = "movie_id";
        public static final String C_ORIGINAL_TITLE = "original_title";
        public static final String C_OVERVIEW = "overview";
        public static final String C_POSTER = "poster";
        public static final String C_BACKDROP = "backdrop";
        public static final String C_DURATION = "duration";
        public static final String C_RELEASE_DATE = "release_date";
        public static final String C_RATING = "rating";

        public static Uri buildMovieUri(long movieId) {
            return ContentUris.withAppendedId(CONTENT_URI, movieId);
        }
    }
}
