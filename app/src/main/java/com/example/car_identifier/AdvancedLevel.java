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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;
import java.util.Random;

public class AdvancedLevel extends AppCompatActivity {
    private final static CarModels carModels = new CarModels();
    private final Random rnd = new Random();
    int modelIndex1,modelIndex2,modelIndex3;
    int imageIndex1,imageIndex2,imageIndex3;
    private Button button;
    private int clickCount = 0;
    private int score = 0;
    boolean check1 = false;
    boolean check2 = false;
    boolean check3 = false;
    TextView timer;
    CountDownTimer countDownTimer;
    int timerCounter = 3;
    boolean isTimerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_advanced_level);

        //Setting up the timer and getting the boolean value of the switch from the Menu Activity.
        timer = findViewById(R.id.textTimerAdv);
        Intent intent = getIntent();
        isTimerOn = intent.getExtras().getBoolean("switchValue");

        //Condition to be executed if the switch is on.
        if (isTimerOn){
            timerOn();
        }
        start();
    }

    public void timerOn() {
        countDownTimer = new CountDownTimer(21000, 1000) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long millisUntilFinished) {
                String seconds = millisUntilFinished / 1000 + "";
                timer.setText("Timer: " + seconds);
            }
            @Override
            public void onFinish() {
                submitAuto();
            }
        };
        countDownTimer.start();
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
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
        ImageView img = findViewById(R.id.imageView_car1);
        String carName = carModels.carModelsArray.get(modelIndex1) + "_" + imageIndex1;
        img.setImageDrawable(getResources().getDrawable(getResourceID(carName, getApplicationContext())));

        //Setting up random image 2.
        ImageView img1 = findViewById(R.id.imageView_car2);
        String carName1 = carModels.carModelsArray.get(modelIndex2) + "_" + imageIndex2;
        img1.setImageDrawable(getResources().getDrawable(getResourceID(carName1, getApplicationContext())));

        //Setting up random image 3.
        ImageView img2 = findViewById(R.id.imageView_car3);
        String carName2 = carModels.carModelsArray.get(modelIndex3) + "_" + imageIndex3;
        img2.setImageDrawable(getResources().getDrawable(getResourceID(carName2, getApplicationContext())));

        //Set the button label to Submit
        button = findViewById(R.id.button6);
        button.setText("Submit");
    }

    protected static int getResourceID(final String resName, final Context ctx) {
        final int ResourceID = ctx.getResources().getIdentifier(resName, "drawable", ctx.getApplicationInfo().packageName);
        if (ResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + resName);
        }else{
            return ResourceID;
        }
    }

    //Conditions that should be executed when the button is pressed or in auto submission.
    @SuppressLint("SetTextI18n")
    public void checkingConditions(){
        TextView scoreText = findViewById(R.id.text_Score);
        TextView result1 = findViewById(R.id.textResult1);
        TextView result2 = findViewById(R.id.textResult2);
        TextView result3 = findViewById(R.id.textResult3);

        EditText textBox1 = findViewById(R.id.userInput1);
        EditText textBox2 = findViewById(R.id.userInput2);
        EditText textBox3 = findViewById(R.id.userInput3);
        String userInput1 = textBox1.getText().toString();
        String userInput2 = textBox2.getText().toString();
        String userInput3 = textBox3.getText().toString();

        if(button.getText().equals("Next")){
            //Checking if the button text is equal to Next.
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }else if(clickCount<=3){
            //Checking if the user has inputted all three answers.
            if (userInput1.equalsIgnoreCase(carModels.carModelsArray.get(modelIndex1))
                    && userInput2.equalsIgnoreCase(carModels.carModelsArray.get(modelIndex2))
                    && userInput3.equalsIgnoreCase(carModels.carModelsArray.get(modelIndex3))){
                button.setText("Next");
                score=3;
                String scoreVal = "Score : " + score;
                scoreText.setText(scoreVal);
                textBox1.setTextColor(Color.rgb(52, 153, 79));
                textBox1.setFocusable(false);
                result1.setText("CORRECT!");
                result1.setTextColor(Color.rgb(52, 153, 79));
                textBox2.setTextColor(Color.rgb(52, 153, 79));
                textBox2.setFocusable(false);
                result2.setText("CORRECT!");
                result2.setTextColor(Color.rgb(52, 153, 79));
                textBox3.setTextColor(Color.rgb(52, 153, 79));
                textBox3.setFocusable(false);
                result3.setText("CORRECT!");
                result3.setTextColor(Color.rgb(52, 153, 79));
                clickCount=4;
            }else {
                button.setText("Submit Again");
                //Checking if the first answer is correct.
                if (!check1) {
                    if (userInput1.equalsIgnoreCase(carModels.carModelsArray.get(modelIndex1))) {
                        textBox1.setTextColor(Color.rgb(52, 153, 79));
                        textBox1.setFocusable(false);
                        result1.setText("CORRECT!");
                        result1.setTextColor(Color.rgb(52, 153, 79));
                        score++;
                        check1 = true;
                    } else {
                        if (userInput1.length() > 0) {
                            textBox1.setTextColor(Color.rgb(186, 45, 49));
                            check1 = false;
                        }
                    }
                }
                //Checking if the second answer is correct.
                if (!check2) {
                    if (userInput2.equalsIgnoreCase(carModels.carModelsArray.get(modelIndex2))) {
                        textBox2.setTextColor(Color.rgb(52, 153, 79));
                        textBox2.setFocusable(false);
                        result2.setText("CORRECT!");
                        result2.setTextColor(Color.rgb(52, 153, 79));
                        score++;
                        check2 = true;
                    } else {
                        if (userInput2.length() > 0) {
                            textBox2.setTextColor(Color.rgb(186, 45, 49));
                            check2 = false;
                        }
                    }
                }
                //checking if the third answer is correct.
                if (!check3) {
                    if (userInput3.equalsIgnoreCase(carModels.carModelsArray.get(modelIndex3))) {
                        textBox3.setTextColor(Color.rgb(52, 153, 79));
                        textBox3.setFocusable(false);
                        result3.setText("CORRECT!");
                        result3.setTextColor(Color.rgb(52, 153, 79));
                        score++;
                        check3 = true;
                    } else {
                        if (userInput3.length() > 0) {
                            textBox3.setTextColor(Color.rgb(186, 45, 49));
                            check3 = false;
                        }
                    }
                }
                //Printing the users score.
                String scoreVal = "Score : " + score;
                scoreText.setText(scoreVal);
                if(clickCount==3 && score!=3){
                    //Commands to be executed when the user have tried three times.
                    button.setText("Next");
                    if(!check1){
                        textBox1.setText("WRONG!");
                        textBox1.setFocusable(false);
                        textBox1.setTextColor(Color.rgb(186, 45, 49));
                        result1.setText(carModels.carModelsArray.get(modelIndex1).toUpperCase());
                        result1.setTextColor(Color.rgb(219, 204, 33));
                    }
                    if(!check2){
                        textBox2.setText("WRONG!");
                        textBox2.setFocusable(false);
                        textBox2.setTextColor(Color.rgb(186, 45, 49));
                        result2.setText(carModels.carModelsArray.get(modelIndex2).toUpperCase());
                        result2.setTextColor(Color.rgb(219, 204, 33));
                    }
                    if(!check3){
                        textBox3.setText("WRONG!");
                        textBox3.setFocusable(false);
                        textBox3.setTextColor(Color.rgb(186, 45, 49));
                        result3.setText(carModels.carModelsArray.get(modelIndex3).toUpperCase());
                        result3.setTextColor(Color.rgb(219, 204, 33));
                    }
                }
            }
        }
    }

    public void onClickButton1(View view) {
        clickCount++;
        checkingConditions();
    }

    //Auto submit function to be executed when the timer hits 0.
    @SuppressLint("SetTextI18n")
    public void submitAuto() {
        if (timerCounter == 3 || timerCounter == 2) {
            timerCounter--;
            checkingConditions();
            if (clickCount==4){
                timerCounter=1;
            }
            if (score<3){
                timerOn();
            }
        }else if (timerCounter == 1) {
            clickCount=3;
            checkingConditions();
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