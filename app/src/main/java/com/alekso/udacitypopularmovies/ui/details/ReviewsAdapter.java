package com.alekso.udacitypopularmovies.ui.details;

import android.content.Context;
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
    private final ReviewsAdapterOnClickHandler mClickHandler;
    private final int PREVIEW_TEXT_LIMIT;
    private List<Review> mReviewsList = new ArrayList<>();

    public ReviewsAdapter(Context context, ReviewsAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
        PREVIEW_TEXT_LIMIT = context.getResources().getBoolean(R.bool.is_tablet) ? 150 : 50;
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
        if (review.getContent() != null) {
            holder.mContent.setText(
                    review.getContent().length() > PREVIEW_TEXT_LIMIT ?
                            review.getContent().substring(0, PREVIEW_TEXT_LIMIT) + ".." :
                            review.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return mReviewsList == null ? 0 : mReviewsList.size();
    }

    public void setReviewsData(List<Review> reviews) {
        mReviewsList = reviews;
        notifyDataSetChanged();
    }

    public interface ReviewsAdapterOnClickHandler {
        void onClick(Review review);
    }

    public class ReviewsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mAuthor;
        TextView mContent;

        public ReviewsAdapterViewHolder(View view) {
            super(view);
            mAuthor = (TextView) view.findViewById(R.id.tv_author);
            mContent = (TextView) view.findViewById(R.id.tv_content);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Review review = mReviewsList.get(adapterPosition);
            mClickHandler.onClick(review);
        }
    }
}
