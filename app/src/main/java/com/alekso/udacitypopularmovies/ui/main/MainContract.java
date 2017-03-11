package com.alekso.udacitypopularmovies.ui.main;

import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.alekso.udacitypopularmovies.ui.BasePresenter;
import com.alekso.udacitypopularmovies.ui.BaseView;

import java.util.List;

/**
 * Created by alekso on 26/02/2017.
 */

public interface MainContract {
    interface Presenter extends BasePresenter {
        /**
         * loads movies
         */
        void loadMovies();

        /**
         * Handles movie click
         *
         * @param id
         */
        void movieClick(long id);

        /**
         * Returns current sort
         *
         * @return
         */
        int getSort();

        /**
         * Sets sort
         *
         * @param sort
         */
        void setSort(int sort);
    }

    interface View extends BaseView<Presenter> {
        /**
         * Shows progress bar
         */
        void showProgressBar();

        /**
         * Hides progress bar
         */
        void hideProgressBar();

        /**
         * Shows list of movies
         *
         * @param movies
         */
        void showMovies(List<Movie> movies);

        /**
         * Shows status text message
         *
         * @param message
         */
        void showStatusText(String message);

        /**
         * Hides status text
         */
        void hideStatusText();

        /**
         * Starts Movie details activity
         *
         * @param movieId
         */
        void showMovieDetailsActivity(long movieId);
    }
}