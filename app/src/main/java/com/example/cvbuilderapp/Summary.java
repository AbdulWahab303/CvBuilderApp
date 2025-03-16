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

public class Summary extends AppCompatActivity {

    Button btnSummaryCancel,btnSummarySave;
    TextView tvSummaryLogoText;
    EditText etSummary;
    Handler handler=new Handler();
    int index=0;
    String text="Summary";


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
        startTypingAnimation();

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
        etSummary=findViewById(R.id.etSummary);
        btnSummaryCancel=findViewById(R.id.btnSummaryCancel);
        btnSummarySave=findViewById(R.id.btnSummarySave);
        tvSummaryLogoText=findViewById(R.id.tvSummaryLogoText);
    }

    private void startTypingAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < text.length()) {
                    tvSummaryLogoText.setText(text.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, 100);
                }
            }
        }, 500);
    }
}