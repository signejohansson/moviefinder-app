package com.example.moviefinder;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

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


        String movieName = getIntent().getStringExtra("movie");
        String url = "https://www.omdbapi.com/?t=" + movieName + "&apikey=ca833554";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String title = response.getString("Title");
                            String year = response.getString("Year");
                            String rating = response.getString("imdbRating");
                            String released = response.getString("Released");
                            String runtime = response.getString("Runtime");
                            String genre = response.getString("Genre");
                            String actor = response.getString("Actors");
                            String plot = response.getString("Plot");

                            title_view.setText(title + " (" + year + ")");
                            rate_view.setText("Rating: " + rating);
                            release_view.setText("Released: " + released);
                            run_view.setText("Runtime: " + runtime);
                            genre_view.setText("Genre: " + genre);
                            actor_view.setText("Actors: " + actor);
                            plot_view.setText("Plot: " + plot);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ResultActivity.this, "Error occurred!", Toast.LENGTH_SHORT).show();
                        Log.e("Volley", error.toString());
                    }
                });
        String REQUEST_TAG = "com.example.moviefinder";
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, REQUEST_TAG);
    }

    public void onBackPressed(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
