package com.alekso.udacitypopularmovies.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.model.Movie;
import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.ui.details.DetailsActivity;

import java.util.List;

/**
 * Created by alekso on 23/02/2017.
 */

public class MainFragment extends Fragment implements MainContract.View {

    private MainContract.Presenter mPresenter;

    private TextView mTextViewStatus;
    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;

    /**
     * Default constructor
     */
    public MainFragment() {
        //
    }

    /**
     * @return
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MoviesAdapter(new MoviesAdapter.MoviesAdapterOnClickHandler() {
            @Override
            public void onClick(long movieId) {
                mPresenter.movieClick(movieId);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movies);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mTextViewStatus = (TextView) view.findViewById(R.id.tv_status);
    }

    @Override
    public void showLoadingIndicator() {
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mAdapter.setMoviesData(movies);
        mTextViewStatus.setVisibility(View.GONE);
    }

    @Override
    public void showErrorLoadingMovies(String message) {
        mTextViewStatus.setText(message);
    }

    @Override
    public void showMovieDetailsActivity(long movieId) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(App.EXTRA_MOVIE_ID, movieId);
        startActivity(intent);
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}