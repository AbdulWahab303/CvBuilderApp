package com.example.cvbuilderapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PersonalDetails extends AppCompatActivity {

    Button btnPersonalCancel,btnPersonalSave;
    EditText etEmail,etName,etAddress,etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_personal_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnPersonalSave.setOnClickListener((v)->{
            savePersonalDetails();
        });

        btnPersonalCancel.setOnClickListener((v)->{
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void init(){
        btnPersonalCancel = findViewById(R.id.btnPersonalCancel);
        btnPersonalSave = findViewById(R.id.btnPersonalSave);
        etEmail = findViewById(R.id.etEmail);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etPhone);
    }

    private void savePersonalDetails() {

        String email = etEmail.getText().toString().trim();
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (email.isEmpty() || name.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "All fields are required!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("email", email);
        resultIntent.putExtra("name", name);
        resultIntent.putExtra("address", address);
        resultIntent.putExtra("phone", phone);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}