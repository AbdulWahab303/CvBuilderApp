package com.example.cvbuilderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    Uri ProfileImage;
    Button btnProfilePicture, personalDetails, summary, education, experience, certifications, ref,cvPreview;
    TextView tvHomeScreenLogoText;
    String text = "Builder";
    String name, email, address, phone, tvSummary, instName, score, companyName, fromDate, toDate;
    ArrayList<String> resultCertifications,resultRef;
    int index = 0;
    Handler handler = new Handler();


    ActivityResultLauncher<Intent> getProfileImageLauncher, getPersonalDetails, getSummary, getEducation, getExperience, getCertifications, getReferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        startTypingAnimation();
        buttonsCalled();
    }

    private void validateFields() {
        boolean allFilled = ProfileImage != null &&
                name != null && !name.isEmpty() &&
                email != null && !email.isEmpty() &&
                address != null && !address.isEmpty() &&
                phone != null && !phone.isEmpty() &&
                tvSummary != null && !tvSummary.isEmpty() &&
                instName != null && !instName.isEmpty() &&
                score != null && !score.isEmpty() &&
                companyName != null && !companyName.isEmpty() &&
                fromDate != null && !fromDate.isEmpty() &&
                toDate != null && !toDate.isEmpty() &&
                resultCertifications != null && !resultCertifications.isEmpty() &&
                resultRef != null && !resultRef.isEmpty();

        // Enable button if all fields are filled
        cvPreview.setEnabled(allFilled);
    }

    private void buttonsCalled() {
        btnProfilePicture.setOnClickListener((v) -> {
            Intent i = new Intent(this, ProfilePicture.class);
            getProfileImageLauncher.launch(i);
        });
        personalDetails.setOnClickListener((v) -> {
            Intent i = new Intent(this, PersonalDetails.class);
            getPersonalDetails.launch(i);
        });
        summary.setOnClickListener((v) -> {
            Intent i = new Intent(this, Summary.class);
            getSummary.launch(i);
        });
        education.setOnClickListener((v) -> {
            Intent i = new Intent(this, Education.class);
            getEducation.launch(i);
        });
        experience.setOnClickListener((v) -> {
            Intent i = new Intent(this, Experience.class);
            getExperience.launch(i);
        });
        certifications.setOnClickListener((v) -> {
            Intent i = new Intent(this, Certifications.class);
            getCertifications.launch(i);
        });
        ref.setOnClickListener((v) -> {
            Intent i = new Intent(this, References.class);
            getReferences.launch(i);
        });
        cvPreview.setOnClickListener((v)->{
            Intent i=new Intent(this,cvPreview.class);

            i.putExtra("Name", name);
            i.putExtra("Email", email);
            i.putExtra("Address", address);
            i.putExtra("Phone", phone);
            i.putExtra("TvSummary", tvSummary);
            i.putExtra("InstName", instName);
            i.putExtra("Score", score);
            i.putExtra("CompanyName", companyName);
            i.putExtra("FromDate", fromDate);
            i.putExtra("ToDate", toDate);
            i.putStringArrayListExtra("ResultCertifications", resultCertifications);
            i.putStringArrayListExtra("ResultRef", resultRef);
            i.putExtra("ProfileImage",ProfileImage.toString());
            startActivity(i);
        });
    }

    private void init() {
        btnProfilePicture = findViewById(R.id.btnProfilePicture);
        personalDetails = findViewById(R.id.personalDetails);
        summary = findViewById(R.id.summary);
        education = findViewById(R.id.education);
        experience = findViewById(R.id.experience);
        certifications = findViewById(R.id.certifications);
        ref = findViewById(R.id.ref);
        tvHomeScreenLogoText = findViewById(R.id.tvHomeScreenLogoText);
        cvPreview=findViewById(R.id.cvPreview);
        cvPreview.setEnabled(false);


        getProfileImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                (result) -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        String imageUriString = result.getData().getStringExtra("imageUri");
                        if (imageUriString != null) {
                            ProfileImage = Uri.parse(imageUriString);
                            Toast.makeText(this, "Image Selected Successfully!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                    validateFields();
                });

        getPersonalDetails = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        email = data.getStringExtra("email");
                        name = data.getStringExtra("name");
                        address = data.getStringExtra("address");
                        phone = data.getStringExtra("phone");
                        Toast.makeText(this, "Selected Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    validateFields();
                });

        getSummary = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        tvSummary = result.getData().getStringExtra("summary");
                        Toast.makeText(this, "Selected Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    validateFields();
                });

        getEducation = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        instName = result.getData().getStringExtra("institution_name");
                        score = result.getData().getStringExtra("score");
                        Toast.makeText(this, "Selected Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    validateFields();
                });

        getExperience = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        companyName = result.getData().getStringExtra("company_name");
                        fromDate = result.getData().getStringExtra("from_date");
                        toDate = result.getData().getStringExtra("to_date");
                        Toast.makeText(this, "Selected Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    validateFields();
                });

        getCertifications = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        resultCertifications = result.getData().getStringArrayListExtra("certifications");
                        Toast.makeText(this, "Selected Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    validateFields();

                });

        getReferences = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        resultRef=result.getData().getStringArrayListExtra("references");
                        Toast.makeText(this, "Selected Successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                    validateFields();
                });
    }


    private void startTypingAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < text.length()) {
                    tvHomeScreenLogoText.setText(text.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, 100);
                }
            }
        }, 500);
    }
}