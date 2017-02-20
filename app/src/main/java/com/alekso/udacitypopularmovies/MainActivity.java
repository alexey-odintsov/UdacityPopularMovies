package com.alekso.udacitypopularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private TextView mTextViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = (TextView) findViewById(R.id.tv_result);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET,
                App.getTopRatedMoviesUrl(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mTextViewResult.setText("RESPONSE: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mTextViewResult.setText("ERROR: " + error);
                    }
                });

        getRequestQueue().add(jsonRequest);
    }

    private RequestQueue getRequestQueue() {
        return Volley.newRequestQueue(MainActivity.this);
    }
}