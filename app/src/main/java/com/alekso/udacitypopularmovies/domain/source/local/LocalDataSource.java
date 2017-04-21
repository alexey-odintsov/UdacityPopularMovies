package com.alekso.udacitypopularmovies.domain.source.local;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.alekso.udacitypopularmovies.domain.source.DataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alekso on 17/04/2017.
 */

public class LocalDataSource implements DataSource {
    private static final String TAG = LocalDataSource.class.getSimpleName();

    private static LocalDataSource sInstance;
    private DbHelper mDbHelper;
    private Context mContext;

    /**
     * @param context
     */
    private LocalDataSource(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(mContext);
    }

    /**
     * @param context
     * @return
     */
    public static LocalDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new LocalDataSource(context);
        }

        return sInstance;
    }

    @Override
    public void getMovies(int sort, LoadMoviesListener listener) {
        List<Movie> movies = new ArrayList<>();
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor c = db.query(MovieContract.MovieEntry.TABLE,
                null,
                null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            while (c.moveToNext()) {
                long movieId = c.getLong(c.getColumnIndex(MovieContract.MovieEntry._ID));
                long release = c.getLong(c.getColumnIndex(MovieContract.MovieEntry.C_RELEASE_DATE));
                String title = c.getString(c.getColumnIndex(MovieContract.MovieEntry.C_TITLE));
                String originalTitle = c.getString(c.getColumnIndex(MovieContract.MovieEntry.C_ORIGINAL_TITLE));
                String overview = c.getString(c.getColumnIndex(MovieContract.MovieEntry.C_OVERVIEW));
                int duration = c.getInt(c.getColumnIndex(MovieContract.MovieEntry.C_DURATION));
                int rating = c.getInt(c.getColumnIndex(MovieContract.MovieEntry.C_RATING));
                String backdrop = c.getString(c.getColumnIndex(MovieContract.MovieEntry.C_BACKDROP));
                String poster = c.getString(c.getColumnIndex(MovieContract.MovieEntry.C_POSTER));

                Movie movie = new Movie.Builder()
                        .setId(movieId)
                        .setReleaseDate(new Date(release).toString())
                        .setTitle(title)
                        .setOriginalTitle(originalTitle)
                        .setDuration(duration)
                        .setOverview(overview)
                        .setRating(rating)
                        .setBackdrop(backdrop)
                        .setPoster(poster)
                        .build();
                movies.add(movie);
            }
        }

        if (c != null) {
            c.close();
        }
        db.close();
        listener.onSuccess(movies);
    }

    @Override
    public void getMovieDetails(long movieId, LoadMovieDetailsListener listener) {
        // movie detail will be obtained from remote server
    }
}
