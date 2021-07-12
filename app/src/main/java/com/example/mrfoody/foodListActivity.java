package com.example.mrfoody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

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





    }
}