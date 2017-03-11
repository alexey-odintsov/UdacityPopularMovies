package com.alekso.udacitypopularmovies.ui.details;

import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.alekso.udacitypopularmovies.ui.BasePresenter;
import com.alekso.udacitypopularmovies.ui.BaseView;

/**
 * Created by alekso on 26/02/2017.
 */

public interface DetailsContract {
    interface Presenter extends BasePresenter {

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


        void showMovieInfo(Movie movie);

        /**
         * Shows movies loading error message
         *
         * @param message
         */
        void showStatusText(String message);

        /**
         * Hides status text
         */
        void hideStatusText();
    }
}
