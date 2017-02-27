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

        void setSort(int sort);
    }

    interface View extends BaseView<Presenter> {
        /**
         * Shows progress bar
         */
        void showLoadingIndicator();

        /**
         * Shows list of movies
         *
         * @param movies
         */
        void showMovies(List<Movie> movies);

        /**
         * Shows movies loading error message
         *
         * @param message
         */
        void showErrorLoadingMovies(String message);

        /**
         * Starts Movie details activity
         *
         * @param movieId
         */
        void showMovieDetailsActivity(long movieId);
    }
}