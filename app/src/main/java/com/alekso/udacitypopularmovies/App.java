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
