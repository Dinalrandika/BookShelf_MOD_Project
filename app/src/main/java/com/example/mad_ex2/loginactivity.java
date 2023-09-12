package com.example.mad_ex2;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginactivity extends AppCompatActivity {

    private EditText emailEditText;
    private TextView RegisterNow;
    private EditText passwordEditText;
    private Button loginButton;

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);

        dbHelper = new DatabaseHelper(this);

        emailEditText = findViewById(R.id.EEmail);
        passwordEditText = findViewById(R.id.EPassword);
        loginButton = findViewById(R.id.btnLogin);
        RegisterNow = findViewById(R.id.RegisterNow);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                // Validate credentials and check if the user is an admin
                if (validateLogin(email, password)) {
                    if (isAdmin(email)) {
                        // Redirect to AdminPanelActivity
                        startActivity(new Intent(loginactivity.this, user_home.class));
                    } else {
                        // Redirect to UserHomeActivity
                        startActivity(new Intent(loginactivity.this, user_home.class));
                    }
                } else {
                    Toast.makeText(loginactivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }

                // Create an Intent
                Intent intentsend = new Intent(loginactivity.this, profile_activity.class);

                // Add data to the Intent
                intentsend.putExtra("key", email);

                // Start the target activity
                startActivity(intentsend);
            }
        });
        RegisterNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to Registration_activity
                startActivity(new Intent(loginactivity.this, RegistrationActivity.class));
            }
        });
    }

    private boolean validateLogin(String email, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "email = ? AND password = ?";
        String[] selectionArgs = {email, password};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
        boolean valid = cursor.getCount() > 0;
        cursor.close();
        return valid;
    }

    private boolean isAdmin(String email) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "email = ? AND is_admin = 1";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(DatabaseHelper.TABLE_USERS, null, selection, selectionArgs, null, null, null);
        boolean isAdmin = cursor.getCount() > 0;
        cursor.close();
        return isAdmin;
    }
}