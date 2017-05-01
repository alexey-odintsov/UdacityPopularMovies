package com.alekso.udacitypopularmovies.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.databinding.FragmentMainBinding;
import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.alekso.udacitypopularmovies.domain.source.local.MovieContract;
import com.alekso.udacitypopularmovies.ui.details.DetailsActivity;

import java.util.List;

import static com.alekso.udacitypopularmovies.domain.source.DataSource.SORT_FAVORITES;
import static com.alekso.udacitypopularmovies.domain.source.DataSource.SORT_POPULARITY;
import static com.alekso.udacitypopularmovies.domain.source.DataSource.SORT_TOP_RATED;

/**
 * Created by alekso on 23/02/2017.
 */

public class MainFragment extends Fragment implements MainContract.View,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String STATE_SORT = "sort";

    /**
     * Debugging flag
     */
    private static final boolean debug = true;
    private static final String TAG = App.fullTag(MainFragment.class.getSimpleName());

    /**
     * Loader id to load list of favorite movies
     */
    private static final int LOADER_GET_FAVORITE_MOVIES = 1;

    private FragmentMainBinding mViewBinding;
    private MainContract.Presenter mPresenter;
    private MoviesAdapter mAdapter;

    /**
     * Default constructor
     */
    public MainFragment() {
        if (debug) Log.d(TAG, "constructor()");
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
        if (debug) Log.d(TAG, "onCreate(savedInstanceState: " + savedInstanceState + ")");

        super.onCreate(savedInstanceState);
        mAdapter = new MoviesAdapter(new MoviesAdapter.MoviesAdapterOnClickHandler() {
            @Override
            public void onClick(long movieId) {
                mPresenter.movieClick(movieId);
            }
        });

        setHasOptionsMenu(true);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (debug) Log.d(TAG, "onSaveInstanceState(outState: " + outState + ")");

        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SORT, mPresenter.getSort());
    }

    @Override
    public void onResume() {
        if (debug) Log.d(TAG, "onResume()");

        super.onResume();

        SharedPreferences prefs = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        mPresenter.setSort(prefs.getInt(App.SETTINGS_MOVIES_SORT, SORT_POPULARITY));

        if (mPresenter != null) {
            mPresenter.start();
        }
    }

    @Override
    public void onPause() {
        if (debug) Log.d(TAG, "onPause()");

        super.onPause();

        SharedPreferences preferences = getActivity().getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(App.SETTINGS_MOVIES_SORT, mPresenter.getSort());
        editor.commit();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (debug)
            Log.d(TAG, "onCreateView(inflater: " + inflater + "; container: " + container + "; savedInstanceState: " + savedInstanceState + ")");

        mViewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return mViewBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (debug)
            Log.d(TAG, "onViewCreated(view: " + view + "; savedInstanceState: " + savedInstanceState + ")");

        super.onViewCreated(view, savedInstanceState);

        mViewBinding.recyclerViewMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mViewBinding.recyclerViewMovies.setAdapter(mAdapter);

        if (savedInstanceState != null) {
            mPresenter.setSort(savedInstanceState.getInt(STATE_SORT, SORT_POPULARITY));
        }
    }

    @Override
    public void showMovies(List<Movie> movies) {
        mAdapter.setMoviesData(movies);
        mViewBinding.progressBar.setVisibility(View.GONE);
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
    public void showProgressBar() {
        mViewBinding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mViewBinding.progressBar.setVisibility(View.GONE);
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        switch (mPresenter.getSort()) {
            case SORT_POPULARITY:
                menu.findItem(R.id.action_sort_popularity).setChecked(true);
                break;
            case SORT_TOP_RATED:
                menu.findItem(R.id.action_sort_toprated).setChecked(true);
                break;
            case SORT_FAVORITES:
                menu.findItem(R.id.action_sort_favorite).setChecked(true);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort_popularity:
                item.setChecked(true);
                mPresenter.setSort(SORT_POPULARITY);
                mPresenter.loadMovies();
                return true;
            case R.id.action_sort_toprated:
                item.setChecked(true);
                mPresenter.setSort(SORT_TOP_RATED);
                mPresenter.loadMovies();
                return true;
            case R.id.action_sort_favorite:
                item.setChecked(true);
                mPresenter.setSort(SORT_FAVORITES);
                createFavoriteMoviesLoaders();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void createFavoriteMoviesLoaders() {
        if (getLoaderManager().getLoader(LOADER_GET_FAVORITE_MOVIES) == null) {
            getLoaderManager().initLoader(LOADER_GET_FAVORITE_MOVIES, null, this);
        } else {
            getLoaderManager().restartLoader(LOADER_GET_FAVORITE_MOVIES, null, this);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (debug) Log.d(TAG, "onCreateLoader(id: " + id + "; args: " + args + ")");
        switch (id) {
            case LOADER_GET_FAVORITE_MOVIES:
                Log.d(TAG, "uri: " + MovieContract.FavoriteMovieEntry.CONTENT_URI);
                return new CursorLoader(getContext(),
                        MovieContract.FavoriteMovieEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (debug) Log.d(TAG, "onLoadFinished(loader: " + loader + "; cursor: " + data + ")");
        switch (loader.getId()) {
            case LOADER_GET_FAVORITE_MOVIES:
                mPresenter.onGetFavoriteMovies(data);
                break;

            default:
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (debug) Log.d(TAG, "onLoaderReset(loader:" + loader + ")");
    }
}