package com.alekso.udacitypopularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alekso on 24/02/2017.
 */

public class DetailsFragment extends Fragment {

    private TextView mTextViewMovieId;
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

        mTextViewMovieId = (TextView) view.findViewById(R.id.tv_movie_id);
        mTextViewMovieId.setText(Long.toString(mMovieId));
    }
}
