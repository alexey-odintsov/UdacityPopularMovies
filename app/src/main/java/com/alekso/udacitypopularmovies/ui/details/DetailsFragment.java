package com.alekso.udacitypopularmovies.ui.details;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.databinding.FragmentDetailsBinding;
import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.alekso.udacitypopularmovies.domain.model.Review;
import com.alekso.udacitypopularmovies.domain.model.Video;
import com.alekso.udacitypopularmovies.domain.source.local.MovieContract;

import java.util.List;

/**
 * Created by alekso on 24/02/2017.
 */

public class DetailsFragment extends Fragment implements DetailsContract.View,
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final boolean debug = true;
    public static final String TAG = App.fullTag(DetailsFragment.class.getSimpleName());
    private static final int LOADER_GET_FAVORITE_MOVIE = 1;

    private DetailsContract.Presenter mPresenter;
    private FragmentDetailsBinding mViewBinding;
    private long mMovieId;
    private MenuItem mFavoriteMenu;

    private ReviewsAdapter mReviewsAdapter;
    private LinearLayoutManager mLayoutManager;
    private VideosAdapter mVideosAdapter;
    private LinearLayoutManager mVideosLayoutManager;

    public DetailsFragment() {

    }

    public static DetailsFragment newInstance(long movieId) {
        if (debug) Log.d(TAG, "newInstance(movieId: " + movieId + ")");

        DetailsFragment fragment = new DetailsFragment();
        fragment.mMovieId = movieId;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (debug) Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        mReviewsAdapter = new ReviewsAdapter(getContext(), new ReviewsAdapter.ReviewsAdapterOnClickHandler() {
            @Override
            public void onClick(Review review) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(review.getContent())
                        .setTitle(review.getAuthor())
                        .setNeutralButton(R.string.close, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        mVideosAdapter = new VideosAdapter(new VideosAdapter.VideosAdapterOnClickHandler() {
            @Override
            public void onClick(Video video) {
                Uri uri = Uri.parse("http://www.youtube.com/watch?v=" + video.getKey());
                Intent videoIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(videoIntent);
            }
        });
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (debug)
            Log.d(TAG, "onCreateView(inflater: " + inflater + "; container: " + container + "; savedInstanceState: " + savedInstanceState + ")");

        mViewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);

        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (debug)
            Log.d(TAG, "onViewCreated(view: " + view + "; savedInstanceState: " + savedInstanceState + ")");

        super.onViewCreated(view, savedInstanceState);

        mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mViewBinding.rvReviews.setLayoutManager(mLayoutManager);
        mViewBinding.rvReviews.setAdapter(mReviewsAdapter);

        mVideosLayoutManager = new LinearLayoutManager(getContext());
        mVideosLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mViewBinding.rvTrailers.setLayoutManager(mVideosLayoutManager);
        mViewBinding.rvTrailers.setAdapter(mVideosAdapter);
    }

    @Override
    public void onResume() {
        if (debug) Log.d(TAG, "onResume()");

        super.onResume();
        mPresenter.start();
        //getLoaderManager().initLoader(LOADER_GET_FAVORITE_MOVIE, null, DetailsFragment.this);
        if (getLoaderManager().getLoader(LOADER_GET_FAVORITE_MOVIE) == null) {
            getLoaderManager().initLoader(LOADER_GET_FAVORITE_MOVIE, null, this);
        } else {
            getLoaderManager().restartLoader(LOADER_GET_FAVORITE_MOVIE, null, this);
        }
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (debug)
            Log.d(TAG, "onCreateOptionsMenu(menu: " + menu + "; inflater: " + inflater + ")");

        inflater.inflate(R.menu.details_menu, menu);
        mFavoriteMenu = menu.findItem(R.id.action_add_to_favorites);
        checkFavoriteMenu(mPresenter.isFavorite());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_to_favorites:
                mPresenter.toggleFavorite();
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void checkFavoriteMenu(boolean check) {
        if (mFavoriteMenu != null) {
            if (check) {
                mFavoriteMenu.setChecked(true);
                mFavoriteMenu.setIcon(android.R.drawable.star_big_on);
            } else {
                mFavoriteMenu.setChecked(false);
                mFavoriteMenu.setIcon(android.R.drawable.star_big_off);
            }
        }
    }

    @Override
    public void showReviews(List<Review> reviews) {
        mReviewsAdapter.setReviewsData(reviews);
    }

    @Override
    public void showVideos(List<Video> items) {
        mVideosAdapter.setVideosData(items);
    }

    @Override
    public void showSelectMovieStub() {
        mViewBinding.layoutDetails.setVisibility(View.GONE);
        mViewBinding.progressBar.setVisibility(View.GONE);
        mViewBinding.textViewStatus.setVisibility(View.VISIBLE);
        mViewBinding.textViewStatus.setText(R.string.select_movie_stub);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (debug) Log.d(TAG, "onCreateLoader(id: " + id + "; args: " + args + ")");
        switch (id) {
            case LOADER_GET_FAVORITE_MOVIE:
                Log.d(TAG, "uri: " + MovieContract.FavoriteMovieEntry.buildMovieUri(mMovieId));
                return new CursorLoader(getContext(),
                        MovieContract.FavoriteMovieEntry.buildMovieUri(mMovieId),
                        null,
                        null,
                        new String[]{String.valueOf(mMovieId)},
                        null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (debug) Log.d(TAG, "onLoadFinished(loader: " + loader + "; cursor: " + data + ")");
        switch (loader.getId()) {
            case LOADER_GET_FAVORITE_MOVIE:
                mPresenter.onGetFavoriteMovie(data);
                break;

            default:
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (debug) Log.d(TAG, "onLoaderReset(loader:" + loader + ")");
    }
}
