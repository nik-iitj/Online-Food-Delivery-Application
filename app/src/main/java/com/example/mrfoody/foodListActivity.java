package com.example.mrfoody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class foodListActivity extends AppCompatActivity {
    RecyclerView foodListView;
    private List<FoodList>food_list;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FoodRecyclerAdapter foodRecyclerAdapter;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();



        foodListView = findViewById(R.id.foodListView);
        foodListView.setLayoutManager(new LinearLayoutManager(this));

        food_list=new ArrayList<>();
        foodRecyclerAdapter = new FoodRecyclerAdapter(food_list);
        foodListView.setAdapter(foodRecyclerAdapter);


        firebaseFirestore.collection("Food Items").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot queryDocumentSnapshots,  FirebaseFirestoreException e) {
                for(DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()){

                    if(doc.getType() == DocumentChange.Type.ADDED){

                        String food_id = doc.getDocument().getId();


                        FoodList foodList = doc.getDocument().toObject(FoodList.class).withId(food_id);
                        food_list.add(foodList);
                        foodRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            }
        });


//        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//            @Override
//            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
//                int stars = (int) rating;
//
//                String message = null;
//
//                switch (stars){
//                    case 1:
//                        message = "sorry to hear that! :(";
//                        break;
//                    case 2:
//                        message = "Good Enough!";
//                        break;
//
//                    case 3:
//                        message = "Great! Thank you! :)";
//                        break;
//                }
//                Toast.makeText(foodListActivity.this, message, Toast.LENGTH_SHORT).show();
//
//            }
//        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cart:
                Toast.makeText(this, "cart is clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Item1:
                Toast.makeText(this, "Item 1 was clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.Item2:
                Toast.makeText(this, "Item 2 was clicked", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.logout:
                Toast.makeText(this, "Item 3 was clicked", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                finish();
                Intent intent = new Intent(foodListActivity.this,SignInActivity.class);
                startActivity(intent);


                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}