package com.alekso.udacitypopularmovies.ui.main;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.source.DataSource;
import com.alekso.udacitypopularmovies.domain.source.Repository;
import com.alekso.udacitypopularmovies.domain.model.Movie;

import java.util.List;

import static com.alekso.udacitypopularmovies.domain.source.DataSource.SORT_POPULARITY;

/**
 * Created by alekso on 26/02/2017.
 */

public class MainPresenter implements MainContract.Presenter,
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final String TAG = MainPresenter.class.getSimpleName();
    private final Repository mRepository;
    private final MainContract.View mView;
    private int mSort = SORT_POPULARITY;

    private LoaderManager mLoaderManager;

    /**
     * @param repository
     * @param view
     */
    public MainPresenter(Repository repository, MainContract.View view) {
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }


    @Override
    public void loadMovies() {
        Log.d(TAG, "loadMovies");
        mView.showProgressBar();
        Context context = ((Fragment) mView).getContext();
        if (!App.isNetworkAvailable(context)) {
            mView.hideProgressBar();
            mView.showStatusText(context.getString(R.string.no_internet_connection));
        } else {
            mView.hideStatusText();
        }

        mRepository.getMovies(mSort, new DataSource.LoadItemsListCallback<Movie>() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mView.hideProgressBar();
                mView.showMovies(movies);
            }

            @Override
            public void onError(String message) {
                mView.hideProgressBar();
                mView.showStatusText(message);
            }
        });
    }

    @Override
    public void movieClick(long id) {
        mView.showMovieDetailsActivity(id);
    }

    @Override
    public void start() {
        loadMovies();
    }

    public int getSort() {
        return mSort;
    }

    @Override
    public void setSort(int sort) {
        mSort = sort;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}