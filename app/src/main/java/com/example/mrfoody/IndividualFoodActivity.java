package com.example.mrfoody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class IndividualFoodActivity extends AppCompatActivity {

    TextView foodName,foodPrice,chefName,foodDes,serveTime;
    ImageView foodImage;
    Button checkout,cart;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_food);

        foodName = findViewById(R.id.foodName);
        foodPrice = findViewById(R.id.foodPrice);
        chefName = findViewById(R.id.chefName);
        foodDes = findViewById(R.id.foodDes);
        checkout = findViewById(R.id.checkout);
        cart = findViewById(R.id.addToCart);
        foodImage = findViewById(R.id.foodImage);
        serveTime = findViewById(R.id.serveTime);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        Bundle data = getIntent().getExtras();
        String id = data.getString("id");

        firebaseFirestore.collection("Food Items").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    foodName.setText(task.getResult().getString("Item_name"));
                    foodPrice.setText("Rs "+task.getResult().getString("Price"));
                    chefName.setText("by - "+task.getResult().getString("Chef_name"));
                    foodDes.setText(task.getResult().getString("Item_description"));
                    serveTime.setText("Will get served within "+task.getResult().getString("Time")+" minutes.");
                    Glide.with(IndividualFoodActivity.this).load(task.getResult().getString("image")).into(foodImage);

                }else{
                    Toast.makeText(IndividualFoodActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }




            }
        });











    }
}