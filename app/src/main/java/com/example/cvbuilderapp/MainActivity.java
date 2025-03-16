package com.example.cvbuilderapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ImageView logo,logoName,logoIcon;
    Animation left_to_right,right_to_left,popup;
    TextView tvLogoText;
    String logoText="Builder";
    int index=0;

    Handler handler=new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
        startTypingAnimation();
        left_to_right= AnimationUtils.loadAnimation(this,R.anim.left_to_right);
        right_to_left=AnimationUtils.loadAnimation(this,R.anim.right_to_left);
        popup=AnimationUtils.loadAnimation(this,R.anim.popup);
        logoName.setAnimation(popup);
        logo.setAnimation(left_to_right);
        logoIcon.setAnimation(right_to_left);
        startHomeScreen();
    }


    private void startTypingAnimation(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(index<logoText.length()){
                    tvLogoText.setText(logoText.substring(0,index+1));
                    index++;
                    handler.postDelayed(this,100);
                }
            }
        },500);
    }

    private void startHomeScreen(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this,Home.class));
            }
        },4000);
    }


    private void init(){
        tvLogoText=findViewById(R.id.tvLogoText);
        logo=findViewById(R.id.logo);
        logoName=findViewById(R.id.logoName);
        logoIcon=findViewById(R.id.logoIcon);
    }
}