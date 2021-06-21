package com.example.mrfoody;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class addItemActivity extends AppCompatActivity {
    TextView greet,emoji;
    ImageView addImg;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        greet = findViewById(R.id.greetingTxt);
        emoji=findViewById(R.id.emojiTxt);
        addImg = findViewById(R.id.addImg);
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







    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100 && resultCode == RESULT_OK){

            if(data.getData()!=null){
                uri = data.getData();
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
                addImg.setImageURI(uri);




            }


        }





    }

    private String getEmoji(int unicode) {

        return new String(Character.toChars(unicode));
    }
}