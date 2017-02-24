package com.alekso.udacitypopularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by alekso on 23/02/2017.
 */

public class MainFragment extends Fragment implements MoviesAdapter.MoviesAdapterOnClickHandler {

    /**
     * Listener that listen for movie clicks
     */
    private OnMovieClickListener mListener;
    private TextView mTextViewStatus;
    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;

    /**
     * Default constructure
     */
    public MainFragment() {

    }

    /**
     * @param listener Movie click listener
     * @return
     */
    public static MainFragment newInstance(OnMovieClickListener listener) {
        MainFragment fragment = new MainFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_movies);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        mTextViewStatus = (TextView) view.findViewById(R.id.tv_status);
    }

    public void setMovies(List<Movie> movies) {
        mAdapter.setMoviesData(movies);

        if (movies == null || movies.size() == 0) {
            showError("No movies found");
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            mTextViewStatus.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(long movieId) {
        mListener.onMovieClick(movieId);
    }

    public void showError(String text) {
        mTextViewStatus.setText(text);
        mRecyclerView.setVisibility(View.GONE);
        mTextViewStatus.setVisibility(View.VISIBLE);
    }

    /**
     * Movie click listener
     */
    interface OnMovieClickListener {
        void onMovieClick(long movieId);
    }
}
