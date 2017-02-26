package com.alekso.udacitypopularmovies.ui.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.source.Repository;
import com.alekso.udacitypopularmovies.domain.source.remote.RemoteDataSource;

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
                Repository.getInstance(RemoteDataSource.getInstance(getApplicationContext())), fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort) {
            // TODO: 25/02/2017 implement sorting
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}