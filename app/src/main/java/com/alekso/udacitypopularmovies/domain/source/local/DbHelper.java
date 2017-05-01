package com.alekso.udacitypopularmovies.domain.source.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.alekso.udacitypopularmovies.domain.source.local.MovieContract.*;

/**
 * Created by alekso on 17/04/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "popular_movies.db";
    public static final int DB_VERSION = 5;

    public static final String SQL_CREATE_FAVORITES = "CREATE TABLE " + FavoriteMovieEntry.TABLE + " ("
            + FavoriteMovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavoriteMovieEntry.C_MOVIE_ID + " INTEGER NOT NULL,"
            + FavoriteMovieEntry.C_TITLE + " TEXT NOT NULL,"
            + FavoriteMovieEntry.C_POSTER + " TEXT"
            + ")";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        createTables(db);
    }

    /**
     * Deletes tables
     *
     * @param db
     */
    private void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + FavoriteMovieEntry.TABLE);
    }

    /**
     * Creates initial db structure
     *
     * @param db
     */
    private void createTables(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_FAVORITES);
    }
}
