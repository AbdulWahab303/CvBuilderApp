package com.example.cvbuilderapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class References extends AppCompatActivity {

    Button btnAddRef,btnRemoveRef,btnRefCancel,btnRefSave;
    TextView tvRefLogoText;
    Handler handler=new Handler();
    int index=0;
    String text="References";
    LinearLayout refContainer;
    ArrayList<EditText>editTextList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_references);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        startTypingAnimation();
        btnAddRef.setOnClickListener((v)->{
           addRef();
        });

        btnRemoveRef.setOnClickListener((v)->{
            removeRef();
        });

        btnRefCancel.setOnClickListener((v)->{
            setResult(RESULT_CANCELED);
            finish();
        });

        btnRefSave.setOnClickListener((v)->{
            saveRef();
        });
    }

    private void saveRef() {
        ArrayList<String> refs = new ArrayList<>();
        int totalFields = editTextList.size();

        if (totalFields % 5 != 0) {
            Toast.makeText(this, "Each reference must have 5 fields filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int i = 0; i < totalFields; i += 5) {
            boolean isValidReference = true;
            StringBuilder refEntry = new StringBuilder();

            for (int j = 0; j < 5; j++) {
                String refText = editTextList.get(i + j).getText().toString().trim();

                if (refText.isEmpty()) {
                    isValidReference = false;
                    break;
                }

                refEntry.append(refText);
                if (j < 4) refEntry.append(" | ");
            }

            if (isValidReference) {
                refs.add(refEntry.toString());
            }
        }

        if (!refs.isEmpty()) {
            Intent resultIntent = new Intent();
            resultIntent.putStringArrayListExtra("references", refs);
            setResult(RESULT_OK, resultIntent);
            finish();
        } else {
            Toast.makeText(this, "No complete references entered!", Toast.LENGTH_SHORT).show();
        }
    }


    private void removeRef(){
        int childCount=refContainer.getChildCount();
        if(childCount>0){
            for(int i=0;i<5;i++){
                refContainer.removeViewAt(childCount-1-i);
                editTextList.remove(childCount-1-i);
            }

        }
    }

    private void addRef(){
        EditText editTextName=new EditText(this);
        EditText editTextJobTitle=new EditText(this);
        EditText editTextCompany=new EditText(this);
        EditText editTextEmail=new EditText(this);
        EditText editTextRelation=new EditText(this);

        editTextName.setHint("Enter Name");
        editTextJobTitle.setHint("Enter Job Title");
        editTextCompany.setHint("Enter Company");
        editTextEmail.setHint("Enter Email");
        editTextRelation.setHint("Enter Your Relation");

        editTextName.setTextColor(Color.WHITE);
        editTextJobTitle.setTextColor(Color.WHITE);
        editTextCompany.setTextColor(Color.WHITE);
        editTextEmail.setTextColor(Color.WHITE);
        editTextRelation.setTextColor(Color.WHITE);


        editTextName.setBackgroundResource(R.drawable.edittext_bg);
        editTextJobTitle.setBackgroundResource(R.drawable.edittext_bg);
        editTextCompany.setBackgroundResource(R.drawable.edittext_bg);
        editTextEmail.setBackgroundResource(R.drawable.edittext_bg);
        editTextRelation.setBackgroundResource(R.drawable.edittext_bg);


        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,50,getResources().getDisplayMetrics()
                )
        );
        layoutParams.setMargins(20,20,20,20);

        editTextName.setLayoutParams(layoutParams);
        editTextJobTitle.setLayoutParams(layoutParams);
        editTextCompany.setLayoutParams(layoutParams);
        editTextRelation.setLayoutParams(layoutParams);
        editTextEmail.setLayoutParams(layoutParams);

        refContainer.addView(editTextName);
        refContainer.addView(editTextJobTitle);
        refContainer.addView(editTextCompany);
        refContainer.addView(editTextRelation);
        refContainer.addView(editTextEmail);

        editTextList.add(editTextName);
        editTextList.add(editTextJobTitle);
        editTextList.add(editTextCompany);
        editTextList.add(editTextRelation);
        editTextList.add(editTextEmail);
    }
    private void init() {
        refContainer = findViewById(R.id.refContainer);
        btnAddRef = findViewById(R.id.btnAddRef);
        btnRemoveRef = findViewById(R.id.btnRemoveRef);
        btnRefCancel = findViewById(R.id.btnRefCancel);
        btnRefSave = findViewById(R.id.btnRefSave);
        tvRefLogoText=findViewById(R.id.tvRefLogoText);
    }

    private void startTypingAnimation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index < text.length()) {
                    tvRefLogoText.setText(text.substring(0, index + 1));
                    index++;
                    handler.postDelayed(this, 100);
                }
            }
        }, 500);
    }
}