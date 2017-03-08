package com.alekso.udacitypopularmovies.ui.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alekso.udacitypopularmovies.App;
import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.model.Movie;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alekso on 24/02/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {
    private final MoviesAdapterOnClickHandler mClickHandler;
    List<Movie> mMoviesList = new ArrayList<>();

    public MoviesAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder holder, int position) {
        Movie movie = mMoviesList.get(position);
        Context context = holder.mImageViewPoster.getContext();

        holder.mImageViewPoster.setImageUrl(App.getPosterUrl("w185", movie.getPoster()), App.getInstance(context).getImageLoader());
        holder.mImageViewPoster.setContentDescription(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return mMoviesList == null ? 0 : mMoviesList.size();
    }

    public void setMoviesData(List<Movie> movies) {
        mMoviesList = movies;
        notifyDataSetChanged();
    }

    public interface MoviesAdapterOnClickHandler {
        void onClick(long movieId);
    }

    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        NetworkImageView mImageViewPoster;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            mImageViewPoster = (NetworkImageView) view.findViewById(R.id.network_image_view_poster);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movie movie = mMoviesList.get(adapterPosition);
            mClickHandler.onClick(movie.getId());
        }
    }
}
