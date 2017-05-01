package com.alekso.udacitypopularmovies.domain.model;

import android.util.Log;

import com.alekso.udacitypopularmovies.App;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alekso on 01/05/2017.
 */

public class VideosReader {
    private static final boolean debug = true;
    private static final String TAG = App.fullTag(VideosReader.class.getSimpleName());

    public static List<Video> getListFromJson(JSONObject response) {
        if (debug) Log.d(TAG, "getListFromJson(response: " + response + ")");

        List<Video> videos = new ArrayList<>();
        Gson gson = new Gson();

        VideosReader.Response itemsResponse = gson.fromJson(response.toString(), VideosReader.Response.class);
        if (debug) Log.d(TAG, "Videos: " + itemsResponse);

        if (itemsResponse != null) {
            for (VideosReader.Response.VideoItem m : itemsResponse.results) {
                Video video = new Video(m.link, m.title);
                Log.d(TAG, "found item: " + video);
                videos.add(video);
            }

            return videos;
        } else {
            return new ArrayList<>();
        }
    }

    class Response {
        List<VideosReader.Response.VideoItem> results;

        class VideoItem {
            @SerializedName("id")
            String link;
            @SerializedName("name")
            String title;
        }
    }
}
