package com.example.car_identifier;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MenuActivity extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch switch_timer;
    Button btn1,btn2,btn3,btn4;
    boolean isTimerOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_menu);

        switch_timer = findViewById(R.id.switch_timer);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.btnMainHint);
        btn3 = findViewById(R.id.btnMainIdentifyImage);
        btn4 = findViewById(R.id.btnMainAdvancedLevel);

        //Checking if the switch is on and changing the boolean value.
        switch_timer.setOnCheckedChangeListener((buttonView, isChecked) -> isTimerOn = isChecked);
        btn1.setOnClickListener(this::launchCarMake);
        btn2.setOnClickListener(this::launchHint);
        btn3.setOnClickListener(this::launchCarImage);
        btn4.setOnClickListener(this::launchAdvancedLevel);
    }

    public void launchCarMake(View view) {
        Intent homeIntent = new Intent(this,IdentifyCarMake.class);
        homeIntent.putExtra("switchValue", isTimerOn);
        startActivity(homeIntent);
    }

    public void launchHint(View view) {
        Intent homeIntent = new Intent(this,Hint.class);
        homeIntent.putExtra("switchValue", isTimerOn);
        startActivity(homeIntent);
    }

    public void launchCarImage(View view) {
        Intent homeIntent = new Intent(this,IdentifyCarImage.class);
        homeIntent.putExtra("switchValue", isTimerOn);
        startActivity(homeIntent);
    }

    public void launchAdvancedLevel(View view) {
        Intent homeIntent = new Intent(this,AdvancedLevel.class);
        homeIntent.putExtra("switchValue", isTimerOn);
        startActivity(homeIntent);
    }

    public void launchQuit(View view) {
        finish();
        System.exit(0);
    }
}