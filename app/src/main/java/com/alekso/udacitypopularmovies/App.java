package com.alekso.udacitypopularmovies;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by alekso on 20/02/2017.
 */

class App {
    private static final String PROTOCOL = "http://";
    private static final String HOST = "api.themoviedb.org";
    private static final String API_LEVEL = "/3";
    private static final String POPULAR_MOVIES = "/movie/popular";
    private static final String TOP_RATED_MOVIES = "/movie/top_rated";

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

    public static String getPopularMoviesUrl() {
        return PROTOCOL + HOST + API_LEVEL + POPULAR_MOVIES + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_TOKEN;
    }

    public static String getTopRatedMoviesUrl() {
        return PROTOCOL + HOST + API_LEVEL + TOP_RATED_MOVIES + "?api_key=" + BuildConfig.THE_MOVIE_DB_API_TOKEN;
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
