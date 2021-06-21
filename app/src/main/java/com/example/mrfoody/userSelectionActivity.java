package com.example.mrfoody;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class userSelectionActivity extends AppCompatActivity {
    Button chef,customer;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Map<String,String>userMap=new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_selection);

        chef=findViewById(R.id.btnChef);
        customer=findViewById(R.id.btnCustomer);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());

        firebaseFirestore.collection("customers").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        Intent intent = new Intent(userSelectionActivity.this,foodListActivity.class);
                        startActivity(intent);
                    }

                    //Toast.makeText(userSelectionActivity.this, "Lite le", Toast.LENGTH_SHORT).show();

                }else{

                    //userMap.put("name",account.getDisplayName());



                }

            }
        });

        firebaseFirestore.collection("chef").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    if(task.getResult().exists()){

                        Intent intent = new Intent(userSelectionActivity.this,addItemActivity.class);
                        startActivity(intent);
                    }


                }else{



                }

            }
        });






        chef.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are You sure you want to continue as a Chef :)");

                builder.setPositiveButton("Yup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userMap.put("name",account.getDisplayName());
                        firebaseFirestore.collection("chef").document(firebaseAuth.getCurrentUser().getUid()).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if(task.isSuccessful()){

                                    AlertDialog.Builder nameBuilder = new AlertDialog.Builder(v.getContext());
                                    final EditText editText = new EditText(userSelectionActivity.this);
                                    nameBuilder.setTitle("Name");
                                    nameBuilder.setMessage("Tell us what we can call you..");
                                    nameBuilder.setView(editText);
                                    editText.setText(account.getDisplayName());

                                    nameBuilder.setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            firebaseFirestore.collection("chef").document(firebaseAuth.getCurrentUser().getUid()).update("name",editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(Task<Void> task) {
                                                    Intent intent = new Intent(userSelectionActivity.this,addItemActivity.class);
                                                    startActivity(intent);
                                                }
                                            });

                                        }
                                    });

                                    nameBuilder.show();
                                    finish();





                                } else{


                                    Toast.makeText(userSelectionActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                }





                            }
                        });

                    }
                });
                builder.setNegativeButton("Noooo",null);
                builder.show();






            }
        });


        customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Confirmation");
                builder.setMessage("Are You sure you want to continue as a Chef :)");

                builder.setPositiveButton("Yup", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userMap.put("name",account.getDisplayName());
                        firebaseFirestore.collection("customers").document(firebaseAuth.getCurrentUser().getUid()).set(userMap);

                        Intent intent = new Intent(userSelectionActivity.this,userProfileSetupActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Noooo",null);


            }
        });






    }
}