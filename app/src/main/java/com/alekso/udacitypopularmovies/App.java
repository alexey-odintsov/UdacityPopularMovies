package com.alekso.udacitypopularmovies;

/**
 * Created by alekso on 20/02/2017.
 */

public class App {
    public static final String PROTOCOL = "http://";
    public static final String HOST = "api.themoviedb.org";
    public static final String API_LEVEL = "/3";
    public static final String POPULAR_MOVIES = "/movie/popular";
    public static final String TOPRATED_MOVIES = "/movie/top_rated";

    public static String getPopularMoviesUrl() {
        return PROTOCOL + HOST + API_LEVEL + POPULAR_MOVIES + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_TOKEN;
    }

    public static String getTopRatedMoviesUrl() {
        return PROTOCOL + HOST + API_LEVEL + TOPRATED_MOVIES + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_TOKEN;
    }

}
