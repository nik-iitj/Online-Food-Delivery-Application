package com.example.mrfoody;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;


public class userProfileSetupActivity extends AppCompatActivity {
    EditText name,tableNumber,contactNumber,totalCount;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_setup);

        name = findViewById(R.id.editTextPersonName);
        tableNumber = findViewById(R.id.table);
        contactNumber = findViewById(R.id.number);
        totalCount = findViewById(R.id.count);

        save = findViewById(R.id.button3);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        assert account != null;
        name.setText(account.getDisplayName());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(name.getText()) && !TextUtils.isEmpty(tableNumber.getText()) && !TextUtils.isEmpty(contactNumber.getText())&&
                !TextUtils.isEmpty(totalCount.getText())){

                    Intent intent = new Intent(userProfileSetupActivity.this,foodListActivity.class);
                    startActivity(intent);

                }

                else{
                    Toast.makeText(userProfileSetupActivity.this, "These are required fields, can't leave empty", Toast.LENGTH_SHORT).show();
                }
            }
        });











    }
}