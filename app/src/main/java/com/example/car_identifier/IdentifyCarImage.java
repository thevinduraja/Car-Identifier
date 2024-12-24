package com.example.car_identifier;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class IdentifyCarImage extends AppCompatActivity {
    private final static CarModels carModels = new CarModels();
    private final Random rnd = new Random();
    int modelIndex1,modelIndex2,modelIndex3;
    int imageIndex1,imageIndex2,imageIndex3;
    String selectedName1,selectedName2,selectedName3;
    String carName,carName1,carName2;
    ImageView img, img1, img2;
    TextView timer;
    CountDownTimer countDownTimer;
    boolean isTimerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_identify_car_image);

        //Setting up the timer and getting the boolean value of the switch from the Menu Activity.
        timer = findViewById(R.id.text_timer1);
        Intent intent = getIntent();
        isTimerOn = intent.getExtras().getBoolean("switchValue");

        //Condition to be executed if the switch is on.
        if (isTimerOn){
            timerOn();
        }
        start();
    }

    public void timerOn(){
        countDownTimer = new CountDownTimer(21000,1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = millisUntilFinished / 1000 + "";
                timer.setText("Timer: "+seconds);
            }
            @Override
            public void onFinish() {submitAuto();}
        };
        countDownTimer.start();
    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    public void start(){
        //Generating indexes for the three random images.
        while(true){
            modelIndex1 = rnd.nextInt(10);
            modelIndex2 = rnd.nextInt(10);
            modelIndex3 = rnd.nextInt(10);
            if (modelIndex1!=modelIndex2 && modelIndex2!=modelIndex3 && modelIndex1!=modelIndex3){
                imageIndex1 = rnd.nextInt(3);
                imageIndex2 = rnd.nextInt(3);
                imageIndex3 = rnd.nextInt(3);
                if (imageIndex1!=imageIndex2 && imageIndex2!=imageIndex3 && imageIndex1!=imageIndex3){
                    break;
                }
            }
        }

        //Setting up random image 1.
        img = findViewById(R.id.imageChoose_car1);
        carName = carModels.carModelsArray.get(modelIndex1) + "_" + imageIndex1;
        img.setImageDrawable(getResources().getDrawable(getResourceID(carName, getApplicationContext())));

        //Setting up random image 2.
        img1 = findViewById(R.id.imageChoose_car2);
        carName1 = carModels.carModelsArray.get(modelIndex2) + "_" + imageIndex2;
        img1.setImageDrawable(getResources().getDrawable(getResourceID(carName1, getApplicationContext())));

        //Setting up random image 3.
        img2 = findViewById(R.id.imageChoose_car);
        carName2 = carModels.carModelsArray.get(modelIndex3) + "_" + imageIndex3;
        img2.setImageDrawable(getResources().getDrawable(getResourceID(carName2, getApplicationContext())));

        //Selecting a random model name from the three images that will be loaded.
        TextView selectedModel = findViewById(R.id.text_carModel);
        int ranCount = rnd.nextInt(3);
        if (ranCount==0){
            selectedName1 =  carModels.carModelsArray.get(modelIndex1);
            selectedModel.setText(selectedName1.toUpperCase());
        }else if (ranCount==1){
            selectedName2 =  carModels.carModelsArray.get(modelIndex2);
            selectedModel.setText(selectedName2.toUpperCase());
        }else if (ranCount==2){
            selectedName3 =  carModels.carModelsArray.get(modelIndex3);
            selectedModel.setText(selectedName3.toUpperCase());
        }

        //Set the button label to Submit
        Button button = findViewById(R.id.button_next);
        button.setText("Next");
    }

    protected static int getResourceID(final String resName, final Context ctx) {
        final int ResourceID = ctx.getResources().getIdentifier(resName, "drawable", ctx.getApplicationInfo().packageName);
        if (ResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + resName);
        }else{
            return ResourceID;
        }
    }

    //Function to be executed when the first image is pressed.
    @SuppressLint("SetTextI18n")
    public void clickImage1(View view){
        String clickedModel = carName.split("_")[0];
        TextView result = findViewById(R.id.text_resultModel);
        if ( selectedName1  != null && selectedName1.equalsIgnoreCase(clickedModel)){
            result.setText("Correct");
            result.setTextColor(Color.rgb(52, 153, 79));
        }else {
            result.setText("Wrong");
            result.setTextColor(Color.rgb(186, 45, 49));
        }
        img.setClickable(false);
        img1.setClickable(false);
        img2.setClickable(false);
    }

    //Function to be executed when the second image is pressed.
    @SuppressLint("SetTextI18n")
    public void clickImage2(View view){
        String clickedModel = carName1.split("_")[0];
        TextView result = findViewById(R.id.text_resultModel);
        if ( selectedName2  != null && selectedName2.equalsIgnoreCase(clickedModel)){
            result.setText("Correct");
            result.setTextColor(Color.rgb(52, 153, 79));
        }else {
            result.setText("Wrong");
            result.setTextColor(Color.rgb(186, 45, 49));
        }
        img.setClickable(false);
        img1.setClickable(false);
        img2.setClickable(false);
    }

    //Function to be executed when the third image is pressed.
    @SuppressLint("SetTextI18n")
    public void clickImage3(View view){
        String clickedModel = carName2.split("_")[0];
        TextView result = findViewById(R.id.text_resultModel);
        if ( selectedName3  != null && selectedName3.equalsIgnoreCase(clickedModel)){
            result.setText("Correct");
            result.setTextColor(Color.rgb(52, 153, 79));
        }else {
            result.setText("Wrong");
            result.setTextColor(Color.rgb(186, 45, 49));
        }
        img.setClickable(false);
        img1.setClickable(false);
        img2.setClickable(false);
    }

    //Reloading the Activity.
    public void onNextButton(View view) {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    //Auto submit function to be executed when the timer hits 0.
    @SuppressLint("SetTextI18n")
    public void submitAuto(){
        String clickedModel = carName.split("_")[0];
        String clickedModel1 = carName1.split("_")[0];
        String clickedModel2 = carName2.split("_")[0];
        img.setClickable(false);
        img1.setClickable(false);
        img2.setClickable(false);
        TextView result = findViewById(R.id.text_resultModel);
        result.setText("Correct Image Is This One");
        result.setTextColor(Color.rgb(52, 153, 79));

        if ( selectedName1  != null && selectedName1.equalsIgnoreCase(clickedModel)){
            img1.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.INVISIBLE);
        }else if ( selectedName2  != null && selectedName2.equalsIgnoreCase(clickedModel1)){
            img.setVisibility(View.INVISIBLE);
            img2.setVisibility(View.INVISIBLE);
        }else if ( selectedName3  != null && selectedName3.equalsIgnoreCase(clickedModel2)){
            img.setVisibility(View.INVISIBLE);
            img1.setVisibility(View.INVISIBLE);
        }
    }

    //Stopping the timer when the user navigates back to the menu.
    public void onDestroy() {
        if (isTimerOn) {
            super.onDestroy();
            countDownTimer.cancel();
        }else {
            super.onDestroy();
        }
    }
}