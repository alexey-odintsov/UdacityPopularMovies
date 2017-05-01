package com.alekso.udacitypopularmovies.ui.details;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.alekso.udacitypopularmovies.domain.model.Review;
import com.alekso.udacitypopularmovies.domain.source.DataSource;
import com.alekso.udacitypopularmovies.domain.source.Repository;

import java.util.List;

/**
 * Created by alekso on 26/02/2017.
 */

public class DetailsPresenter implements DetailsContract.Presenter {

    private static final boolean debug = true;
    private static final String TAG = App.fullTag(DetailsPresenter.class.getSimpleName());

    private final Repository mRepository;
    private final DetailsContract.View mView;
    private long mMovieId;
    private Movie mMovie;
    private boolean mIsFavorite = false;

    /**
     * @param movieId
     * @param repository
     * @param view
     */
    public DetailsPresenter(long movieId, Repository repository, DetailsContract.View view) {
        if (debug)
            Log.d(TAG, "constructor(movieId: " + movieId + "; repository: " + repository + "; view: " + view + ")");

        mMovieId = movieId;
        mRepository = repository;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (debug) Log.d(TAG, "start()");

        loadMovie();
    }

    private void loadMovie() {
        mView.showProgressBar();

        Context context = ((Fragment) mView).getContext();
        if (!App.isNetworkAvailable(context)) {
            mView.hideProgressBar();
            mView.showStatusText(context.getString(R.string.no_internet_connection));
        } else {
            mView.hideStatusText();
        }

        mRepository.getMovieDetails(mMovieId, new DataSource.LoadItemCallback<Movie>() {
            @Override
            public void onSuccess(Movie movie) {
                mMovie = movie;
                mView.hideProgressBar();
                mView.showMovieInfo(movie);
                mRepository.getMovieReviews(mMovieId, new DataSource.LoadItemsListCallback<Review>() {
                    @Override
                    public void onSuccess(List<Review> items) {
                        mView.showReviews(items);
                    }

                    @Override
                    public void onError(String message) {
                        Log.e(TAG, "Error getting reviews: " + message);
                    }
                });
            }

            @Override
            public void onError(String message) {
                mView.hideProgressBar();
                mView.showStatusText(message);
            }
        });

    }

    @Override
    public void toggleFavorite() {
        if (mMovie != null) {
            if (mIsFavorite) {
                mRepository.removeFavoriteMovie(mMovieId);
            } else {
                mRepository.addFavoriteMovie(mMovie);
            }
        }
    }

    @Override
    public void onGetFavoriteMovie(Cursor data) {
        if (debug) Log.d(TAG, "onGetFavoriteMovie(data: " + data + ")");

        if (data != null && data.moveToLast()) {
            mIsFavorite = true;
        } else {
            mIsFavorite = false;
        }
        mView.checkFavoriteMenu(mIsFavorite);

    }

    @Override
    public boolean isFavorite() {
        return mIsFavorite;
    }
}
