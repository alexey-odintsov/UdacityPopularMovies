package com.alekso.udacitypopularmovies.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.source.Repository;
import com.alekso.udacitypopularmovies.domain.source.local.LocalDataSourceImpl;
import com.alekso.udacitypopularmovies.domain.source.remote.RemoteDataSourceImpl;
import com.alekso.udacitypopularmovies.ui.details.DetailsContract;
import com.alekso.udacitypopularmovies.ui.details.DetailsFragment;
import com.alekso.udacitypopularmovies.ui.details.DetailsPresenter;

/**
 * Main app activity. Will list popular movies picture in a recyclerView.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String LAST_MOVIE_ID = "lat_movie_id";
    private static final boolean debug = true;
    private MainContract.Presenter mPresenter;
    private DetailsContract.Presenter mDetailsPresenter;
    private long mLastSelectedMovieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (debug) Log.d(TAG, "onCreate(savedInstanceState: " + savedInstanceState + ")");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_content);
        if (fragment == null) {
            fragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
        }

        mPresenter = new MainPresenter(
                Repository.getInstance(
                        LocalDataSourceImpl.getInstance(getContentResolver()),
                        RemoteDataSourceImpl.getInstance(getApplicationContext())
                ), fragment);

        // for tablets show details pane
        if (getResources().getBoolean(R.bool.is_tablet)) {
            if (savedInstanceState != null) {
                mLastSelectedMovieId = savedInstanceState.getLong(LAST_MOVIE_ID, 0);
            }

            //DetailsFragment detailsFragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment2_content);

            //if (detailsFragment == null) {
            DetailsFragment detailsFragment = DetailsFragment.newInstance(mLastSelectedMovieId);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment2_content, detailsFragment, DetailsFragment.TAG).commit();
            //}

            mDetailsPresenter = new DetailsPresenter(mLastSelectedMovieId,
                    Repository.getInstance(
                            LocalDataSourceImpl.getInstance(getContentResolver()),
                            RemoteDataSourceImpl.getInstance(getApplicationContext())
                    ), detailsFragment);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putLong(LAST_MOVIE_ID, mLastSelectedMovieId);

        if (debug) Log.d(TAG, "onSaveInstanceState(bundle: " + outState + ")");
        super.onSaveInstanceState(outState);
    }

    public void setLastMovieId(long movieId) {
        mLastSelectedMovieId = movieId;
    }
}