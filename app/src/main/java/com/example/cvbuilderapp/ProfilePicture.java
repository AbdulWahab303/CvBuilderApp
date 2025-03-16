package com.example.cvbuilderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProfilePicture extends AppCompatActivity {

    Button btnProfileCancel,btnSelectImage,btnProfileSave;

    ImageView ivPreviewImage;
    Uri selectedImage=null;

    ActivityResultLauncher<String>imagePickLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile_picture);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnSelectImage.setOnClickListener((v)->{
            imagePickLauncher.launch("image/*");

        });

        btnProfileSave.setOnClickListener(v -> {
            if (selectedImage != null) {
                Intent result = new Intent();
                result.putExtra("imageUri", selectedImage.toString());
                setResult(RESULT_OK, result);
                finish();
            } else {
                Toast.makeText(this,"No Images Selected",Toast.LENGTH_LONG).show();
            }
        });

        btnProfileCancel.setOnClickListener((v)->{
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void init(){
        btnProfileCancel=findViewById(R.id.btnProfileCancel);
        btnSelectImage=findViewById(R.id.btnSelectImage);
        ivPreviewImage=findViewById(R.id.ivPreviewImage);
        btnProfileSave=findViewById(R.id.btnProfileSave);

        imagePickLauncher=registerForActivityResult(new ActivityResultContracts.GetContent(),v->{
            if(v!=null){
                getContentResolver().takePersistableUriPermission(v,Intent.FLAG_GRANT_READ_URI_PERMISSION);
                selectedImage=v;
                ivPreviewImage.setImageURI(v);
            }
        });
    }
}