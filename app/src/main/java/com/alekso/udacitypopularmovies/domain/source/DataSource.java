package com.alekso.udacitypopularmovies.domain.source;

import com.alekso.udacitypopularmovies.domain.model.Movie;

import java.util.List;

/**
 * Created by alekso on 26/02/2017.
 */

public interface DataSource {
    public static final int SORT_POPULARITY = 0;
    public static final int SORT_TOP_RATED = 1;
    public static final int SORT_FAVORITES = 2;



    /**
     * Callback for retrieving list of items
     *
     * @param <T>
     */
    interface LoadItemsListCallback<T> {
        void onSuccess(List<T> items);

        void onError(String message);
    }

    /**
     * Callback for retrieving an item
     *
     * @param <T>
     */
    interface LoadItemCallback<T> {
        void onSuccess(T result);

        void onError(String message);
    }
}
