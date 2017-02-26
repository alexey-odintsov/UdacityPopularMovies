package com.alekso.udacitypopularmovies.data.source.remote;

import android.content.Context;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.BuildConfig;
import com.alekso.udacitypopularmovies.data.source.DataSource;
import com.alekso.udacitypopularmovies.model.Movie;
import com.alekso.udacitypopularmovies.model.MoviesReader;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by alekso on 26/02/2017.
 */

public class RemoteDataSource implements DataSource {

    private static final String PROTOCOL = "http://";
    private static final String HOST = "api.themoviedb.org";
    private static final String API_LEVEL = "/3";
    private static final String POPULAR_MOVIES = "/movie/popular";
    private static final String TOP_RATED_MOVIES = "/movie/top_rated";
    private static RemoteDataSource sInstance;
    private Context mContext;


    /**
     * @param context
     */
    private RemoteDataSource(Context context) {
        mContext = context;
    }

    /**
     * @param context
     * @return
     */
    public static RemoteDataSource getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RemoteDataSource(context);
        }

        return sInstance;
    }

    /**
     * Builds path to popular movies
     *
     * @return
     */
    private static String getPopularMoviesUrl() {
        return PROTOCOL + HOST + API_LEVEL + POPULAR_MOVIES + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_TOKEN;
    }

    /**
     * Builds path to top rated movies
     *
     * @return
     */
    private static String getTopRatedMoviesUrl() {
        return PROTOCOL + HOST + API_LEVEL + TOP_RATED_MOVIES + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_TOKEN;
    }

    @Override
    public void getMovies(final LoadMoviesListener listener) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                getTopRatedMoviesUrl(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Movie> movies = MoviesReader.parse(response);
                        listener.onSuccess(movies);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.getMessage());
                    }
                });

        App.getInstance(mContext).addToRequestQueue(jsonRequest);
    }

    @Override
    public void getMovieDetails(long movieId, LoadMovieDetailsListener listener) {
        // TODO: 26/02/2017 implement details loading
        listener.onError("Loading for movie " + movieId);
    }
}
