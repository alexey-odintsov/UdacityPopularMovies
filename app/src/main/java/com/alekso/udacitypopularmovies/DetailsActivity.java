package com.alekso.udacitypopularmovies;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = DetailsActivity.class.getSimpleName();

    DetailsFragment mDetailsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        long movieId = 0;

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(App.EXTRA_MOVIE_ID)) {
            movieId = intent.getLongExtra(App.EXTRA_MOVIE_ID, 0);
        }

        mDetailsFragment = mDetailsFragment.newInstance(movieId);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_content, mDetailsFragment).commit();
    }
}
