package com.example.mrfoody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class addItemActivity extends AppCompatActivity {
    TextView greet,emoji;
    ImageView addImg;
    Uri uri;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    EditText itemName,itemDes,time,price;
    Button save;
    Uri imageUri;
    String chef;
    Map<String,String> foodMap= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        greet = findViewById(R.id.greetingTxt);
        emoji=findViewById(R.id.emojiTxt);
        addImg = findViewById(R.id.addImg);
        itemName=findViewById(R.id.itemName);
        itemDes = findViewById(R.id.txtDes);
        price = findViewById(R.id.price);
        time = findViewById(R.id.time);
        save = findViewById(R.id.addItem);

        imageUri = null;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        firebaseFirestore.collection("chef").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){

                    greet.setText("Hey chef " + task.getResult().getString("name"));
                    chef = task.getResult().getString("name");

                }

            }
        });


        uri = null;

        int unicode = 0x1F60B;
        String myEmoji = getEmoji(unicode);

        emoji.setText("What we have got now" + myEmoji+myEmoji);



        addImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select the dish"),100);


            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(itemName.getText().toString()) && !TextUtils.isEmpty(itemDes.getText().toString()) &&
                        !TextUtils.isEmpty(time.getText().toString()) && !TextUtils.isEmpty(price.getText().toString()) ){

                    foodMap.put("Item_name",itemName.getText().toString());
                    foodMap.put("Item_description",itemDes.getText().toString());
                    foodMap.put("Time",time.getText().toString());
                    foodMap.put("Price",price.getText().toString());
                    foodMap.put("Chef_name",chef);
                    foodMap.put("Chef_ID",firebaseAuth.getCurrentUser().getUid());

                    firebaseFirestore.collection("Food Items").add(foodMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete( Task<DocumentReference> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(addItemActivity.this, "Food item added"+myEmoji, Toast.LENGTH_SHORT).show();
                                itemName.setText("");
                                itemDes.setText("");
                                time.setText("");
                                price.setText("");

                            }
                            else{

                                Toast.makeText(addItemActivity.this, "Error! Try Again", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });




                } else{

                    Toast.makeText(addItemActivity.this, "Can't leave anything blank", Toast.LENGTH_SHORT).show();

                }






            }
        });







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode == RESULT_OK){

            if(data.getData()!=null){
                uri = data.getData();

                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
                addImg.setImageURI(uri);

                StorageReference foodImg = storageReference.child("Food Images").child(firebaseAuth.getCurrentUser().getUid()+".jpg");

                foodImg.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        foodImg.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri finalUri) {
                                imageUri = finalUri;
                                foodMap.put("image",imageUri.toString());
                                Toast.makeText(addItemActivity.this, "Storage Reference created", Toast.LENGTH_SHORT).show();

                            }
                        });


                    }
                });




            }


        }





    }

    private String getEmoji(int unicode) {

        return new String(Character.toChars(unicode));
    }
}