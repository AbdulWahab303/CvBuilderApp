package com.example.cvbuilderapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class Experience extends AppCompatActivity {


    EditText etFromDate,etToDate,etCompany;
    TextView tvExperienceLogoText;
    Handler handler=new Handler();
    String text="Experience";
    int index=0;
    CheckBox cbPresent;
    Button btnExperienceCancel,btnExperienceSave;

    String[]months={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_experience);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        startTypingAnimation();

        btnExperienceSave.setOnClickListener((v) -> {
            saveExperience();
        });

        btnExperienceCancel.setOnClickListener((v)->{
            setResult(RESULT_CANCELED);
            finish();
        });
        etFromDate.setOnClickListener((v)->{
            showDatePickerDialog(etFromDate);
        });

        etToDate.setOnClickListener((v)->{
            showDatePickerDialog(etToDate);
        });

        cbPresent.setOnCheckedChangeListener((bv,isChecked)->{
            if(isChecked){
                etToDate.setText("Present");
                etToDate.setEnabled(false);
            }
            else{
                etToDate.setText("");
                etToDate.setEnabled(true);
            }
        });
    }


    private void saveExperience() {
        String companyName = etCompany.getText().toString().trim();
        String fromDate = etFromDate.getText().toString().trim();
        String toDate = etToDate.getText().toString().trim();

        if (companyName.isEmpty() || fromDate.isEmpty() || (!cbPresent.isChecked() && toDate.isEmpty())) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent resultIntent = new Intent();
        resultIntent.putExtra("company_name", companyName);
        resultIntent.putExtra("from_date", fromDate);
        resultIntent.putExtra("to_date", toDate);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    private void showDatePickerDialog(EditText editText){
        Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog=new DatePickerDialog(this,(view,year1,month1,dayOfMonth)->{
            StringBuilder selectedDate=new StringBuilder();
            selectedDate.append(dayOfMonth);
            selectedDate.append(" ");
            selectedDate.append(months[month1]);
            selectedDate.append(" ");
            selectedDate.append(year1);
            editText.setText(selectedDate);
        },year,month,day);

        datePickerDialog.show();
    }


    private void init() {
        btnExperienceCancel = findViewById(R.id.btnExperienceCancel);
        btnExperienceSave = findViewById(R.id.btnExperienceSave);
        etCompany = findViewById(R.id.etCompany);
        etFromDate = findViewById(R.id.etFromDate);
        etToDate = findViewById(R.id.etToDate);
        cbPresent = findViewById(R.id.cbPresent);
        tvExperienceLogoText=findViewById(R.id.tvExperienceLogoText);
    }
    private void startTypingAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < text.length()) {
                    tvExperienceLogoText.setText(text.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, 100);
                }
            }
        }, 500);
    }
}