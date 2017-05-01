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

public class ReviewsReader {
    private static final boolean debug = true;
    private static final String TAG = App.fullTag(ReviewsReader.class.getSimpleName());

    public static List<Review> getListFromJson(JSONObject response) {
        if (debug) Log.d(TAG, "getListFromJson(response: " + response + ")");

        List<Review> reviews = new ArrayList<>();
        Gson gson = new Gson();

        Response itemsResponse = gson.fromJson(response.toString(), Response.class);
        if (debug) Log.d(TAG, "Reviews: " + itemsResponse);

        if (itemsResponse != null) {
            for (Response.ReviewItem m : itemsResponse.results) {
                Review review = new Review(m.id, m.author, m.content);
                Log.d(TAG, "found item: " + review);
                reviews.add(review);
            }

            return reviews;
        } else {
            return new ArrayList<>();
        }
    }

    class Response {
        List<ReviewItem> results;

        class ReviewItem {
            String id;
            String author;
            String content;
        }
    }
}
