package com.example.cvbuilderapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Education extends AppCompatActivity {

    Button btnEducationCancel,btnEducationSave;
    EditText etInstName,etScore;
    Handler handler=new Handler();
    String text="Education";
    int index=0;
    TextView tvEducationLogoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_education);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        init();
        startTypingAnimation();

        btnEducationSave.setOnClickListener((v) -> {
            String instName = etInstName.getText().toString().trim();
            String score = etScore.getText().toString().trim();

            if (instName.isEmpty() || score.isEmpty()) {
                Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent resultIntent = new Intent();
            resultIntent.putExtra("institution_name", instName);
            resultIntent.putExtra("score", score);
            setResult(RESULT_OK, resultIntent);
            finish();
        });


        btnEducationCancel.setOnClickListener((v)->{
            setResult(RESULT_CANCELED);
            finish();
        });
    }


    private void init() {
        btnEducationCancel = findViewById(R.id.btnEducationCancel);
        btnEducationSave = findViewById(R.id.btnEducationSave);
        etInstName = findViewById(R.id.etInstName);
        etScore = findViewById(R.id.etScore);
        tvEducationLogoText=findViewById(R.id.tvEducationLogoText);
    }
    private void startTypingAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < text.length()) {
                    tvEducationLogoText.setText(text.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, 100);
                }
            }
        }, 500);
    }
}