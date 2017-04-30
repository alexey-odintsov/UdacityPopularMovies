package com.alekso.udacitypopularmovies.domain.source.remote;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.BuildConfig;
import com.alekso.udacitypopularmovies.domain.source.DataSource;
import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.alekso.udacitypopularmovies.domain.model.MoviesReader;
import com.alekso.udacitypopularmovies.domain.source.RemoteDataSource;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.List;
import java.util.Locale;

import static com.alekso.udacitypopularmovies.domain.source.DataSource.*;

/**
 * Created by alekso on 26/02/2017.
 */

public class RemoteDataSourceImpl implements RemoteDataSource {

    private static final String TAG = RemoteDataSourceImpl.class.getSimpleName();

    private static final String PROTOCOL = "http://";
    private static final String HOST = "api.themoviedb.org";
    private static final String API_LEVEL = "3";
    private static final String POPULAR_MOVIES = "popular";
    private static final String TOP_RATED_MOVIES = "top_rated";
    private static final String MOVIE = "movie";

    private static RemoteDataSourceImpl sInstance;
    private Context mContext;


    /**
     * @param context
     */
    private RemoteDataSourceImpl(Context context) {
        mContext = context;
    }

    /**
     * @param context
     * @return
     */
    public static RemoteDataSourceImpl getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new RemoteDataSourceImpl(context);
        }

        return sInstance;
    }

    /**
     * Builds path to popular movies
     *
     * @return
     */
    private static String getPopularMoviesUrl() {
        Uri uri = Uri.parse(PROTOCOL + HOST).buildUpon()
                .appendPath(API_LEVEL)
                .appendPath(MOVIE)
                .appendPath(POPULAR_MOVIES)
                .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .appendQueryParameter("language", Locale.getDefault().getISO3Country())
                .build();
        return uri.toString();
    }

    /**
     * Builds path to top rated movies
     *
     * @return
     */
    private static String getTopRatedMoviesUrl() {
        Uri uri = Uri.parse(PROTOCOL + HOST).buildUpon()
                .appendPath(API_LEVEL)
                .appendPath(MOVIE)
                .appendPath(TOP_RATED_MOVIES)
                .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .appendQueryParameter("language", Locale.getDefault().getISO3Language())
                .build();
        return uri.toString();
    }

    private String getMovieDetailsUrl(long movieId) {
        Uri uri = Uri.parse(PROTOCOL + HOST).buildUpon()
                .appendPath(API_LEVEL)
                .appendPath(MOVIE)
                .appendPath(Long.toString(movieId))
                .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_TOKEN)
                .appendQueryParameter("language", Locale.getDefault().getISO3Language())
                .build();
        return uri.toString();
    }

    @Override
    public void getMovies(int sort, final DataSource.LoadItemsListCallback<Movie> listener) {
        String url;

        switch (sort) {
            case SORT_TOP_RATED:
                url = getTopRatedMoviesUrl();
                break;
            case SORT_POPULARITY:
            default:
                url = getPopularMoviesUrl();
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<Movie> movies = MoviesReader.parseMoviesList(response);
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
    public void getMovieDetails(final long movieId, final LoadItemCallback<Movie> listener) {
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                getMovieDetailsUrl(movieId), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "MOVIE: " + response);
                        Movie movie = MoviesReader.parseMovieDetails(response);
                        listener.onSuccess(movie);
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

}