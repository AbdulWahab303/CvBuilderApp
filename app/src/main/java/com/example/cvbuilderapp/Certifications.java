package com.example.cvbuilderapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class Certifications extends AppCompatActivity {


    Button btnAddCert, btnRemoveCert, btnCertSave, btnCertCancel;
    LinearLayout certificationsContainer;

    ArrayList<EditText> editTextList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_certifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnCertSave.setOnClickListener((v) -> {
            saveCertifications();
        });

        btnRemoveCert.setOnClickListener((v) -> {

            int childCount = certificationsContainer.getChildCount();
            if (childCount > 0) {
                certificationsContainer.removeViewAt(childCount - 1);
                editTextList.remove(childCount - 1);
            }

        });

        btnAddCert.setOnClickListener((v) -> {
            EditText editText = new EditText(this);
            editText.setHint("Enter Certifications Details");
            editText.setTextColor(Color.WHITE);

            editText.setBackgroundResource(R.drawable.edittext_bg);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()
                    )
            );
            layoutParams.setMargins(20, 20, 20, 0);
            editText.setLayoutParams(layoutParams);
            certificationsContainer.addView(editText);
            editTextList.add(editText);
        });

        btnCertCancel.setOnClickListener((v) -> {
            setResult(RESULT_CANCELED);
            finish();
        });


    }

    private void saveCertifications() {
        ArrayList<String> certs = new ArrayList<>();
        for (EditText editText : editTextList) {
            String certText = editText.getText().toString().trim();
            if (!certText.isEmpty()) {
                certs.add(certText);
            }
        }

        if (!certs.isEmpty()) {

            Intent resultIntent = new Intent();
            resultIntent.putStringArrayListExtra("certifications", certs);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "No certifications entered!", Toast.LENGTH_SHORT).show();
        }
    }

    private void init() {
        btnCertCancel = findViewById(R.id.btnCertCancel);
        btnCertSave = findViewById(R.id.btnCertSave);
        btnAddCert = findViewById(R.id.btnAddCert);
        btnRemoveCert = findViewById(R.id.btnRemoveCert);
        certificationsContainer = findViewById(R.id.certificationsContainer);
    }
}