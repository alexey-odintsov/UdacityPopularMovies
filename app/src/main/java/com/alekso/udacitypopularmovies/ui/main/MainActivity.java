package com.alekso.udacitypopularmovies.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.source.Repository;
import com.alekso.udacitypopularmovies.domain.source.local.LocalDataSourceImpl;
import com.alekso.udacitypopularmovies.domain.source.remote.RemoteDataSourceImpl;

/**
 * Main app activity. Will list popular movies picture in a recyclerView.
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private MainContract.Presenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
    }

}