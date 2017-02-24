package com.alekso.udacitypopularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by alekso on 23/02/2017.
 */

public class MainFragment extends Fragment {

    /**
     * Listener that listen for movie clicks
     */
    private OnMovieClickListener mListener;
    /**
     * TextView to represent API response. Will be removed by RecyclerView later.
     */
    private TextView mTextViewResult;

    /**
     * Default constructure
     */
    public MainFragment() {

    }

    /**
     * @param listener Movie click listener
     * @return
     */
    public static MainFragment newInstance(OnMovieClickListener listener) {
        MainFragment fragment = new MainFragment();
        fragment.mListener = listener;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextViewResult = (TextView) view.findViewById(R.id.tv_result);

        mTextViewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onMovieClick(view);
            }
        });

    }

    public void setText(String text) {
        mTextViewResult.setText(text);
    }

    /**
     * Movie click listener
     */
    interface OnMovieClickListener {
        void onMovieClick(View view);
    }
}
