package com.example.mad_ex2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class user_home extends AppCompatActivity {
    private EditText searchEditText;
    private ImageButton searchButton;
    private ListView featuredBooksListView;
    private TextView featuredBooksTextView;
    private ImageButton btnHome, btnCategory, btnCart, btnProfile,btnMap;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("books");

        // Initialize views
        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        featuredBooksListView = findViewById(R.id.featuredBooksListView);
        btnHome = findViewById(R.id.btnHome);
        btnCategory = findViewById(R.id.btnCategory);
        btnCart = findViewById(R.id.btnCart);
        btnProfile = findViewById(R.id.btnProfile);
        btnMap= findViewById(R.id.btnMap);

        // Load books from Firebase into the ListView
        loadBooksFromFirebase();

        // Set up search functionality
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchEditText.getText().toString().trim();
                // Implement search logic based on categories and book names
                // Update the ListView with search results
            }
        });

        // Set up button clicks for navigation
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Refresh the activity (reload books or perform other actions)
                finish();
                startActivity(getIntent());
            }
        });

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CategoriesActivity
                startActivity(new Intent(user_home.this, categeory_view.class));
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to CartActivity

                startActivity(new Intent(user_home.this, cartactivity.class));
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to ProfileActivity
                startActivity(new Intent(user_home.this, profile_activity.class));
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to MapActivity
                startActivity(new Intent(user_home.this, Maps_Activity.class));
            }
        });
    }

    private void loadBooksFromFirebase() {
        // Query Firebase Database to load books into the ListView
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Loop through dataSnapshot to get book data and populate the ListView
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }
}