package com.example.cvbuilderapp;

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

public class References extends AppCompatActivity {

    Button btnAddRef,btnRemoveRef,btnRefCancel,btnRefSave;
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

    private void saveRef(){
        ArrayList<ArrayList<String>> refs = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            refs.add(new ArrayList<>());
        }

        int fieldIndex = 0;
        for (EditText editText : editTextList) {
            String refText = editText.getText().toString().trim();
            if (!refText.isEmpty()) {
                refs.get(fieldIndex % 5).add(refText); // Distribute among 5 fields
            }
            fieldIndex++;
        }

        if (!refs.isEmpty()) {
            Toast.makeText(this, "References Saved: " + refs, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No references entered!", Toast.LENGTH_SHORT).show();
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
    }
}