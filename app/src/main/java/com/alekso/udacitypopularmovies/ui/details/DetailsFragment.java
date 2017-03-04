package com.alekso.udacitypopularmovies.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by alekso on 24/02/2017.
 */

public class DetailsFragment extends Fragment implements DetailsContract.View {

    private DetailsContract.Presenter mPresenter;

    private ViewGroup mViewGroupDetails;
    private ProgressBar mProgressBar;
    private TextView mTextViewMovieId;
    private TextView mTextViewMovieTitle;
    private TextView mTextViewMovieOriginalTitle;
    private TextView mTextViewMovieOverview;
    private TextView mTextViewMovieDuration;
    private TextView mTextViewMovieReleaseDate;
    private NetworkImageView mImageViewPoster;
    private NetworkImageView mImageViewBackdrop;
    private RatingBar mRatingBar;

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
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewGroupDetails = (ViewGroup) view.findViewById(R.id.ll_details);
        mProgressBar = (ProgressBar) view.findViewById(R.id.pb_progress);
        mTextViewMovieId = (TextView) view.findViewById(R.id.tv_movie_id);
        mTextViewMovieTitle = (TextView) view.findViewById(R.id.tv_movie_title);
        mTextViewMovieOriginalTitle = (TextView) view.findViewById(R.id.tv_movie_original_title);
        mTextViewMovieOverview = (TextView) view.findViewById(R.id.tv_movie_description);
        mTextViewMovieDuration = (TextView) view.findViewById(R.id.tv_movie_duration);
        mTextViewMovieReleaseDate = (TextView) view.findViewById(R.id.tv_movie_year);
        mImageViewPoster = (NetworkImageView) view.findViewById(R.id.iv_movie_poster);
        mImageViewBackdrop = (NetworkImageView) view.findViewById(R.id.iv_movie_backdrop);
        mRatingBar = (RatingBar) view.findViewById(R.id.rb_movie_rating);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showLoadingIndicator() {
        mProgressBar.setVisibility(View.VISIBLE);
        mViewGroupDetails.setVisibility(View.GONE);
    }

    @Override
    public void showMovieInfo(Movie movie) {
        mTextViewMovieId.setText(Long.toString(mMovieId));
        mTextViewMovieTitle.setText(movie.getTitle());
        mTextViewMovieOriginalTitle.setText(getString(R.string.details_original_title) + movie.getOriginalTitle());
        mTextViewMovieOverview.setText(movie.getOverview());
        mTextViewMovieDuration.setText(String.valueOf(movie.getDuration()) + getString(R.string.details_duration_min));
        // TODO: 26/02/2017 format date to current locale
        mTextViewMovieReleaseDate.setText(movie.getReleaseDate());
        mImageViewPoster.setImageUrl(App.getPosterUrl("w500", movie.getPoster()), App.getInstance(getContext()).getImageLoader());
        mImageViewBackdrop.setImageUrl(App.getPosterUrl("original", movie.getBackdrop()), App.getInstance(getContext()).getImageLoader());
        mRatingBar.setRating(movie.getRating() / 2f);

        mProgressBar.setVisibility(View.GONE);
        mViewGroupDetails.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(DetailsContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
