package com.example.moviefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText movieNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movieNameEditText = findViewById(R.id.movie);
        Button searchButton = findViewById(R.id.search_btn);
        searchButton.setOnClickListener(v -> {
            String movieName = movieNameEditText.getText().toString().trim();
            if (!movieName.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("movie", movieName);
                startActivity(intent);
            } else {
                Toast.makeText(MainActivity.this, "Please enter a movie title", Toast.LENGTH_SHORT).show();
            }
        });
    }
}