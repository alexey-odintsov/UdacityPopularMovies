package com.alekso.udacitypopularmovies.domain.source.local;

import android.provider.BaseColumns;

/**
 * Created by alekso on 16/04/2017.
 */

public final class MovieContract {
    public static abstract class MovieEntry implements BaseColumns {
        public static final String TABLE = "favorite_movies";
        public static final String C_TITLE = "title";
        public static final String C_ORIGINAL_TITLE = "original_title";
        public static final String C_OVERVIEW = "overview";
        public static final String C_POSTER = "poster";
        public static final String C_BACKDROP = "backdrop";
        public static final String C_DURATION = "duration";
        public static final String C_RELEASE_DATE = "release_date";
        public static final String C_RATING = "rating";
    }
}
