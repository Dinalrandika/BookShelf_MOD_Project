package com.example.mad_ex2;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class book_view extends AppCompatActivity {
    private DatabaseReference mDatabaseReference;
    private ImageView bookImage;
    private TextView productName, productPrice, bookDescription;
    private EditText bookCount;
    private Button addToCartBtn;
    private String bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view);

        // Initialize Firebase Realtime Database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        // Initialize views
        bookImage = findViewById(R.id.book_image);
        productName = findViewById(R.id.product_name_details);
        productPrice = findViewById(R.id.product_price_details);
        bookDescription = findViewById(R.id.book_description_details);
        bookCount = findViewById(R.id.book_count);
        addToCartBtn = findViewById(R.id.add_to_cart_btn);

        // Get the book ID from the intent
        bookId = getIntent().getStringExtra("book_id");

        // Load book data from Firebase
        loadBookData();

        // Handle Add to Cart button click
        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the book count from the EditText
                int count = Integer.parseInt(bookCount.getText().toString());

                // Store book data in the cart node in Firebase
                addToCart(bookId, count);
            }
        });
    }

    private void loadBookData() {
        // Construct the Firebase database reference for the specific book
        DatabaseReference bookRef = mDatabaseReference.child("books").child(bookId);

        // Listen for changes in the book data
        bookRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve book data from the database
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String price = dataSnapshot.child("price").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String imageUrl = dataSnapshot.child("imageUrl").getValue(String.class);

                    // Update the UI with the retrieved data
                    productName.setText(name);
                    productPrice.setText(price);
                    bookDescription.setText(description);

                    // Load the book image using Picasso or your preferred image loading library
                    Picasso.get().load(imageUrl).into(bookImage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    private void addToCart(String bookId, int count) {
        // Construct a reference to the user's cart in Firebase (you may need to manage user authentication)
        DatabaseReference userCartRef = mDatabaseReference.child("carts").child("user_id").child(bookId);

        // Create a CartItem object and set its values
        Cart_Item cartItem = new Cart_Item(bookId, count);

        // Push the cart item to the user's cart in Firebase
        userCartRef.setValue(cartItem);
    }
}

