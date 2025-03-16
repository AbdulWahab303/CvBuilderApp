package com.example.cvbuilderapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class cvPreview extends AppCompatActivity {

    RelativeLayout mainLayout;
    TextView tvName, tvEmail, tvAddress, tvPhone, tvSummary, tvEducation, tvExperience;
    ImageView profileImage;
    LinearLayout certificationsContainer, referencesContainer;

    String name, email, address, phone, tvSummaryText, instName, score, companyName, fromDate, toDate;
    ArrayList<String> resultCertifications, resultRef;
    Uri profileImageUri;

    Button btnShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cv_preview);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        getIntentData();
        setDataToUI();

        btnShare.setOnClickListener((v)->{
            shareCVAsImage();
        });
    }



    private void shareCVAsImage() {
        Bitmap bitmap = getScreenshot(mainLayout);
        if (bitmap != null) {
            try {
                File file = saveImage(bitmap);
                if (file != null) {
                    Uri uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    intent.putExtra(Intent.EXTRA_STREAM, uri);
                    startActivity(Intent.createChooser(intent, "Share CV via"));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to share image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Bitmap getScreenshot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private File saveImage(Bitmap bitmap) throws IOException {
        File folder = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "CVs");
        if (!folder.exists()) folder.mkdirs();
        File file = new File(folder, "cv_image.png");
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
        return file;
    }


    private void setDataToUI() {
        tvName.setText(name);
        tvEmail.setText(email);
        tvAddress.setText(address);
        tvPhone.setText(phone);
        tvSummary.setText(tvSummaryText);
        tvEducation.setText(instName + " - Score: " + score);
        tvExperience.setText(companyName + " (" + fromDate + " - " + toDate + ")");
        try {
            profileImage.setImageURI(profileImageUri);
        } catch (SecurityException e) {
            getContentResolver().takePersistableUriPermission(profileImageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            profileImage.setImageURI(profileImageUri);
        }

        if (resultCertifications != null) {
            for (String cert : resultCertifications) {
                TextView textView = new TextView(this);
                textView.setText(cert);
                textView.setTextSize(16);
                certificationsContainer.addView(textView);
            }
        }


        if (resultRef != null) {
            for (String reference : resultRef) {
                TextView textView = new TextView(this);
                textView.setText(reference);
                textView.setTextSize(16);
                referencesContainer.addView(textView);
            }
        }
    }


    private void getIntentData() {
        Intent intent = getIntent();

        if (intent != null) {
            name = intent.getStringExtra("Name");
            email = intent.getStringExtra("Email");
            address = intent.getStringExtra("Address");
            phone = intent.getStringExtra("Phone");
            tvSummaryText = intent.getStringExtra("TvSummary");
            instName = intent.getStringExtra("InstName");
            score = intent.getStringExtra("Score");
            companyName = intent.getStringExtra("CompanyName");
            fromDate = intent.getStringExtra("FromDate");
            toDate = intent.getStringExtra("ToDate");

            resultCertifications = intent.getStringArrayListExtra("ResultCertifications");
            resultRef = intent.getStringArrayListExtra("ResultRef");
            profileImageUri=Uri.parse(intent.getStringExtra("ProfileImage"));


        }
    }

    private void init(){
        btnShare=findViewById(R.id.btnShare);
        mainLayout=findViewById(R.id.mainLayout);
        profileImage = findViewById(R.id.profileImage);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvAddress = findViewById(R.id.tvAddress);
        tvPhone = findViewById(R.id.tvPhone);
        tvSummary = findViewById(R.id.tvSummary);
        tvEducation = findViewById(R.id.tvEducation);
        tvExperience = findViewById(R.id.tvExperience);
        certificationsContainer = findViewById(R.id.certificationsContainer);
        referencesContainer = findViewById(R.id.referencesContainer);
    }
}