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
    public static final int DB_VERSION = 3;

    public static final String SQL_CREATE_FAVORITES = "CREATE TABLE " + MovieEntry.TABLE + " ("
            + MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MovieEntry.C_MOVIE_ID + " INTEGER NOT NULL,"
            + MovieEntry.C_TITLE + " TEXT NOT NULL,"
            + MovieEntry.C_ORIGINAL_TITLE + " TEXT,"
            + MovieEntry.C_OVERVIEW + " TEXT,"
            + MovieEntry.C_BACKDROP + " TEXT,"
            + MovieEntry.C_DURATION + " NUMBER,"
            + MovieEntry.C_RELEASE_DATE + " NUMBER,"
            + MovieEntry.C_RATING + " NUMBER,"
            + MovieEntry.C_POSTER + " TEXT"
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
        db.execSQL("DROP TABLE IF EXISTS " + MovieEntry.TABLE);
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
