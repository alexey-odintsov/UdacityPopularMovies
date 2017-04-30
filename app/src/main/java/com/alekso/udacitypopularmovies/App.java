package com.alekso.udacitypopularmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.util.LruCache;

import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alekso on 20/02/2017.
 */

public class App {

    /**
     * Intent extra to pass a movie id
     */
    public static final String EXTRA_MOVIE_ID = "movie_id";
    /**
     * Shared preference file name
     */
    public static final String SHARED_PREFERENCES_NAME = "movies_prefs";
    public static final String SETTINGS_MOVIES_SORT = "movies_sort";

    private static final String TAG = "PopMovies";
    /**
     * Base Url for images loading
     */
    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    /**
     * Instance of the class
     */
    private static App sInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    /**
     * @param context
     */
    private App(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    public static String fullTag(String tag) {
        return TAG + ":" + tag;
    }

    /**
     * @param context
     * @return
     */
    public static synchronized App getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new App(context);
        }

        return sInstance;
    }

    public static String getPosterUrl(String size, String path) {
        return IMAGE_BASE_URL + size + "/" + path;
    }

    /**
     * Checks for available Internet connection.
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    /**
     * Returns the request queue
     *
     * @return
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Adds request to the queue
     *
     * @param req
     * @param <T>
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    /**
     * Returns Image loader
     *
     * @return
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }
}
