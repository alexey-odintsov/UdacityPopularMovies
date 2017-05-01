package com.alekso.udacitypopularmovies.ui.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alekso on 01/05/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsAdapterViewHolder> {
    List<Review> mReviewsList = new ArrayList<>();

    public ReviewsAdapter() {
    }

    @Override
    public ReviewsAdapter.ReviewsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_item, parent, false);
        return new ReviewsAdapter.ReviewsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewsAdapter.ReviewsAdapterViewHolder holder, int position) {
        Review review = mReviewsList.get(position);

        holder.mAuthor.setText(review.getAuthor());
        holder.mContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mReviewsList == null ? 0 : mReviewsList.size();
    }

    public void setReviewsData(List<Review> reviews) {
        mReviewsList = reviews;
        notifyDataSetChanged();
    }

    public class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthor;
        TextView mContent;

        public ReviewsAdapterViewHolder(View view) {
            super(view);
            mAuthor = (TextView) view.findViewById(R.id.tv_author);
            mContent = (TextView) view.findViewById(R.id.tv_content);
        }
    }
}
