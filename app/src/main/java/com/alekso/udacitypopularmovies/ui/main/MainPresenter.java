package com.alekso.udacitypopularmovies.ui.main;

import com.alekso.udacitypopularmovies.domain.source.DataSource;
import com.alekso.udacitypopularmovies.domain.source.Repository;
import com.alekso.udacitypopularmovies.domain.model.Movie;

import java.util.List;

/**
 * Created by alekso on 26/02/2017.
 */

public class MainPresenter implements MainContract.Presenter {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private final Repository mRepository;
    private final MainContract.View mView;

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
        mRepository.getMovies(new DataSource.LoadMoviesListener() {
            @Override
            public void onSuccess(List<Movie> movies) {
                mView.showMovies(movies);
            }

            @Override
            public void onError(String message) {
                mView.showErrorLoadingMovies(message);
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
}