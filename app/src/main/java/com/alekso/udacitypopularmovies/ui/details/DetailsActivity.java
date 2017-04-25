package com.alekso.udacitypopularmovies.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.source.Repository;
import com.alekso.udacitypopularmovies.domain.source.local.LocalDataSourceImpl;
import com.alekso.udacitypopularmovies.domain.source.remote.RemoteDataSourceImpl;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getSimpleName();

    private DetailsContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        long movieId = 0;

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(App.EXTRA_MOVIE_ID)) {
            movieId = intent.getLongExtra(App.EXTRA_MOVIE_ID, 0);
        }

        DetailsFragment fragment = (DetailsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_content);

        if (fragment == null) {
            fragment = DetailsFragment.newInstance(movieId);

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_content, fragment).commit();
        }

        mPresenter = new DetailsPresenter(movieId,
                Repository.getInstance(
                        LocalDataSourceImpl.getInstance(getContentResolver()),
                        RemoteDataSourceImpl.getInstance(getApplicationContext())
                ), fragment);
    }
}
