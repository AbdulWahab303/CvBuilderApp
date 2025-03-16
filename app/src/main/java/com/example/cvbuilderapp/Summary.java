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

public class Summary extends AppCompatActivity {

    Button btnSummaryCancel,btnSummarySave;
    EditText etSummary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_summary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();

        btnSummarySave.setOnClickListener((v) -> {
            String summary = etSummary.getText().toString().trim();

            if (summary.isEmpty()) {
                Toast.makeText(this, "Summary cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("summary", summary);
            setResult(RESULT_OK, resultIntent);
            finish();
        });


        btnSummaryCancel.setOnClickListener((v)->{
            setResult(RESULT_CANCELED);
            finish();
        });
    }

    private void init(){

        btnSummaryCancel=findViewById(R.id.btnSummaryCancel);
        btnSummarySave=findViewById(R.id.btnSummarySave);
    }
}