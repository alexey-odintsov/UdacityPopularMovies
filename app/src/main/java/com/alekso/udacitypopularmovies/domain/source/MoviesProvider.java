package com.alekso.udacitypopularmovies.domain.source;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.alekso.udacitypopularmovies.domain.source.local.DbHelper;
import com.alekso.udacitypopularmovies.domain.source.local.MovieContract;

/**
 * Created by alekso on 21/04/2017.
 */

public class MoviesProvider extends ContentProvider {

    private static final int MOVIES = 100;
    private static final int MOVIES_ITEM = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper mDbHelper;


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MovieContract.MovieEntry.TABLE, MOVIES);
        matcher.addURI(authority, MovieContract.MovieEntry.TABLE + "/*", MOVIES_ITEM);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor c;

        switch (sUriMatcher.match(uri)) {
            case MOVIES:
                c = mDbHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case MOVIES_ITEM:
                String[] where = {uri.getLastPathSegment()};
                c = mDbHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE,
                        projection,
                        MovieContract.MovieEntry.C_MOVIE_ID + " = ? ",
                        where,
                        null,
                        null,
                        sortOrder
                );

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            case MOVIES_ITEM:
                return MovieContract.MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case MOVIES:
                // check whether movie already stored in database
                Cursor c = db.query(
                        MovieContract.MovieEntry.TABLE,
                        new String[]{MovieContract.MovieEntry.C_MOVIE_ID},
                        MovieContract.MovieEntry.C_MOVIE_ID + " = ? ",
                        new String[]{values.getAsString(MovieContract.MovieEntry.C_MOVIE_ID)},
                        null,
                        null,
                        null
                );

                if (c.moveToLast()) { // movie already exists
                    // do nothing or update data
                    long id = db.update(
                            MovieContract.MovieEntry.TABLE,
                            values,
                            MovieContract.MovieEntry.C_MOVIE_ID + " = ? ",
                            new String[]{values.getAsString(MovieContract.MovieEntry.C_MOVIE_ID)}
                    );
                    if (id > 0) {
                        returnUri = MovieContract.MovieEntry.buildMovieUri(id);
                    } else {
                        throw new SQLiteException("Failed to insert row for " + uri);
                    }

                } else { // no such movie, add it
                    long id = db.insert(
                            MovieContract.MovieEntry.TABLE,
                            null,
                            values
                    );
                    if (id > 0) {
                        returnUri = MovieContract.MovieEntry.buildMovieUri(id);
                    } else {
                        throw new SQLiteException("Failed to insert row for " + uri);
                    }
                }

                c.close();
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        switch (match) {
            case MOVIES:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (selection == null || rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case MOVIES:
                rowsUpdated = db.update(MovieContract.MovieEntry.TABLE, values, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
