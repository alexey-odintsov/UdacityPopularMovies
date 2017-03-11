package com.alekso.udacitypopularmovies.ui.details;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.databinding.FragmentDetailsBinding;
import com.alekso.udacitypopularmovies.domain.model.Movie;

/**
 * Created by alekso on 24/02/2017.
 */

public class DetailsFragment extends Fragment implements DetailsContract.View {

    private DetailsContract.Presenter mPresenter;
    private FragmentDetailsBinding mViewBinding;
    private long mMovieId;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(long movieId) {
        DetailsFragment fragment = new DetailsFragment();
        fragment.mMovieId = movieId;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showProgressBar() {
        mViewBinding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mViewBinding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMovieInfo(Movie movie) {
        mViewBinding.textViewId.setText(Long.toString(mMovieId));
        mViewBinding.textViewTitle.setText(movie.getTitle());
        mViewBinding.textViewOriginalTitle.setText(getString(R.string.details_original_title, movie.getOriginalTitle()));
        mViewBinding.textViewDescription.setText(movie.getOverview());
        mViewBinding.textViewDuration.setText(getString(R.string.details_duration_min, movie.getDuration()));
        mViewBinding.textViewYear.setText(movie.getReleaseDate());
        mViewBinding.networkImageViewPoster.setImageUrl(App.getPosterUrl("w500", movie.getPoster()), App.getInstance(getContext()).getImageLoader());
        mViewBinding.networkImageViewBackdrop.setImageUrl(App.getPosterUrl("original", movie.getBackdrop()), App.getInstance(getContext()).getImageLoader());
        mViewBinding.ratingBar.setRating(movie.getRating() / 2f); // I use 5 star rating. API returns 10 star rating.
        mViewBinding.layoutDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void showStatusText(String message) {
        mViewBinding.textViewStatus.setVisibility(View.VISIBLE);
        mViewBinding.textViewStatus.setText(message);
    }

    @Override
    public void hideStatusText() {
        mViewBinding.textViewStatus.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
