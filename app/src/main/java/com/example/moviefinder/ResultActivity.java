package com.example.moviefinder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.toolbox.JsonObjectRequest;

public class ResultActivity extends Activity  {
    TextView title_view,rate_view,release_view,run_view,genre_view,actor_view,plot_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        title_view = findViewById(R.id.title);
        rate_view = findViewById(R.id.rating);
        release_view = findViewById(R.id.released);
        run_view = findViewById(R.id.runtime);
        genre_view = findViewById(R.id.genre);
        actor_view = findViewById(R.id.actor);
        plot_view = findViewById(R.id.plot);

        final ProgressBar progressBar = findViewById(R.id.indeterminateBar);
        progressBar.setVisibility(View.VISIBLE);


        String movieName = getIntent().getStringExtra("movie");
        String url = "https://www.omdbapi.com/?t=" + movieName + "&apikey=ca833554";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null,
                        response -> {
                        try {
                            if (response.has("Error")) {
                                String errorMessage = response.getString("Error");
                                Toast.makeText(ResultActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                goBackToMain();
                                }
                            else {
                                String title = response.getString("Title");
                                String year = response.getString("Year");
                                String rating = response.getString("imdbRating");
                                String released = response.getString("Released");
                                String runtime = response.getString("Runtime");
                                String genre = response.getString("Genre");
                                String actor = response.getString("Actors");
                                String plot = response.getString("Plot");

                                title_view.setText(getString(R.string.title_year_format, title, year));
                                rate_view.setText(getString(R.string.rating_format, rating));
                                release_view.setText(getString(R.string.released_format, released));
                                run_view.setText(getString(R.string.runtime_format, runtime));
                                genre_view.setText(getString(R.string.genre_format, genre));
                                actor_view.setText(getString(R.string.actors_format, actor));
                                plot_view.setText(getString(R.string.plot_format, plot));


                                progressBar.setVisibility(View.GONE);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                },error -> {
                    Toast.makeText(ResultActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                    Log.e("Volley", error.toString());
                    progressBar.setVisibility(View.GONE);
                    goBackToMain();
                });
        String REQUEST_TAG = "com.example.moviefinder";
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, REQUEST_TAG);
    }

    private void goBackToMain() {
        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onBackPressed(View view) {
        goBackToMain();
    }


}
