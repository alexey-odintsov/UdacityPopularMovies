package com.alekso.udacitypopularmovies.ui.details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alekso.udacitypopularmovies.R;
import com.alekso.udacitypopularmovies.domain.model.Video;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alekso on 01/05/2017.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideosAdapterViewHolder> {
    private final VideosAdapter.VideosAdapterOnClickHandler mClickHandler;
    List<Video> mVideosList = new ArrayList<>();

    public VideosAdapter(VideosAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    @Override
    public VideosAdapter.VideosAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.video_item, parent, false);
        return new VideosAdapter.VideosAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideosAdapter.VideosAdapterViewHolder holder, int position) {
        Video video = mVideosList.get(position);

        holder.mLink.setText(video.getKey());
        holder.mTitle.setText(video.getTitle());
    }

    @Override
    public int getItemCount() {
        return mVideosList == null ? 0 : mVideosList.size();
    }

    public void setVideosData(List<Video> videos) {
        mVideosList = videos;
        notifyDataSetChanged();
    }

    public interface VideosAdapterOnClickHandler {
        void onClick(Video video);
    }


    public class VideosAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mLink;
        TextView mTitle;

        public VideosAdapterViewHolder(View view) {
            super(view);
            mLink = (TextView) view.findViewById(R.id.tv_link);
            mTitle = (TextView) view.findViewById(R.id.tv_title);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Video video = mVideosList.get(adapterPosition);
            mClickHandler.onClick(video);

        }
    }
}
