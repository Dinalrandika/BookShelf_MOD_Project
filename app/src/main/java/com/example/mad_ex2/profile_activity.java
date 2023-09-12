package com.example.mad_ex2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class profile_activity extends AppCompatActivity {
    private EditText phoneNumberEditText, fullNameEditText, addressEditText;
    private Button updateButton, logoutButton;
    private ImageButton btnHome, btnCategory, btnCart, btnProfile,btnMaps;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize Firebase components
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        // Initialize views
        phoneNumberEditText = findViewById(R.id.settings_phone_number);
        fullNameEditText = findViewById(R.id.setting_full_name);
        addressEditText = findViewById(R.id.setting_address);
        updateButton = findViewById(R.id.btnupdate);
        logoutButton = findViewById(R.id.btnLogout);
        btnHome = findViewById(R.id.btnHome);
        btnCategory = findViewById(R.id.btnCategory);
        btnCart = findViewById(R.id.btnCart);
        btnProfile = findViewById(R.id.btnProfile);
        btnMaps = findViewById(R.id.btnMap);
        // Set initial values from Firebase if available
        if (user != null) {
            phoneNumberEditText.setText(user.getPhoneNumber());
            fullNameEditText.setText(user.getDisplayName());
            // You can add logic to populate the address if needed
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile); // Replace with your layout

            // Retrieve the data from the Intent
            Intent intent = getIntent();
            if (intent != null) {
                String receivedData = intent.getStringExtra("key");

                // Now, you can use the receivedData in your target activity
                // For example, you can display it in a TextView
                TextView textView = findViewById(R.id.profile_image_btn); // Replace with your TextView
                textView.setText(receivedData);
            }
        }

        // Set up click listeners
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserProfile();
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile_activity.this, user_home.class));
            }
        });

        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile_activity.this, categories_activity.class));
            }
        });

        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile_activity.this, cartactivity.class));
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Refresh the activity
                finish();
                startActivity(getIntent());
            }
        });
        btnMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(profile_activity.this, map_implement.class));
            }
        });
    }

    private void updateUserProfile() {
        // Get user input
        String phoneNumber = phoneNumberEditText.getText().toString().trim();
        String fullName = fullNameEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Check if fields are empty
        if (TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(fullName) || TextUtils.isEmpty(address)) {
            Toast.makeText(profile_activity.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update Firebase user profile
        //user.updatePhoneNumber(phoneNumber);
        user.updateProfile(new UserProfileChangeRequest.Builder()
                .setDisplayName(fullName)
                .build());

        // Update user data in Firebase Realtime Database
        UserProfile userProfile = new UserProfile(phoneNumber, fullName, address);
        databaseReference.child(user.getUid()).setValue(userProfile);

        Toast.makeText(profile_activity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
    }

    private void logout() {
        firebaseAuth.signOut();
        startActivity(new Intent(profile_activity.this, loginactivity.class));
        finish();
    }
}
