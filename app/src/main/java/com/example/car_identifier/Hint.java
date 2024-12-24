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

public class Hint extends AppCompatActivity {
    private final static CarModels carModels = new CarModels();
    private final Random rnd = new Random();
    private Button button;
    char[] guessWordChar;
    char[] guessWordCharOrigin;
    String guessWord;
    String guessWordDashes;
    int clickCounter = 0;
    int tryCounter = 3;
    TextView timer;
    CountDownTimer countDownTimer;
    int timerCounter = 3;
    boolean timerState=false;
    boolean isTimerOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_hint);

        //Setting up the timer and getting the boolean value of the switch from the Menu Activity.
        timer = findViewById(R.id.HintTimer);
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

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void start(){
        //Setting up the Image view to set random images.
        ImageView img = findViewById(R.id.imageView_car);
        String randomModelName = carModels.carModelsArray.get(rnd.nextInt(10));
        String carName = randomModelName + "_" + rnd.nextInt(3);
        img.setImageDrawable(getResources().getDrawable(getResourceID(carName, getApplicationContext())));

        //Setting up the textView with dashes.
        TextView guessWordText = findViewById(R.id.textViewHint);
        guessWord = carName.split("_")[0].toUpperCase();
        guessWordCharOrigin = guessWord.toCharArray();
        int guessWordLen = guessWord.length();
        guessWordDashes = new String(new char[guessWordLen]).replace("\0", "_");
        guessWordChar = guessWordDashes.toCharArray();
        guessWordText.setText(guessWordDashes);

        //Set the button label to Submit and Number of tries to 3.
        button = findViewById(R.id.BtnHint);
        button.setText("Submit");

        //Setting the number of tries to 3.
        TextView tries = findViewById(R.id.textViewHintTries);
        tries.setText("Tries Left : 3");
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
        int counter = 0;
        EditText charText = findViewById(R.id.editTextChar);
        TextView guessWordText = findViewById(R.id.textViewHint);
        TextView results = findViewById(R.id.HintResults);
        TextView answer = findViewById(R.id.HintAnswer);
        TextView tries = findViewById(R.id.textViewHintTries);
        String charVal = charText.getText().toString();

        if (button.getText().equals("Next")){ //Checking if the button text is equal to Next.
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }//Checking if the word is correct.
        else if (clickCounter<3){
            for (int i = 0; i < guessWord.length(); i++) {
                if (charVal.equalsIgnoreCase(String.valueOf(guessWordCharOrigin[i]))) {
                    guessWordChar[i] = guessWordCharOrigin[i];
                    if (String.valueOf(guessWordChar).equalsIgnoreCase(String.valueOf(guessWordCharOrigin))) {
                        button = findViewById(R.id.BtnHint);
                        button.setText("Next");
                        results.setText("Correct");
                        results.setTextColor(Color.rgb(52, 153, 79));
                        timerState=true;
                        if (timerCounter==1 || timerCounter==2) {
                            countDownTimer.cancel();
                        }
                    }
                }
            }
            guessWordDashes = String.valueOf(guessWordChar);
            guessWordText.setText(guessWordDashes);
            //The part to be executed if the entered letter is wrong.
            for (int i = 0; i < guessWord.length(); i++) {
                //Checking if the entered letter is a valid one by going through each character.
                if (!charVal.equalsIgnoreCase(String.valueOf(guessWordCharOrigin[i]))) {
                    counter++;
                }
            }
            //The counter will only be equal to guess word if the entered letter is wrong.
            if (guessWord.length()==counter){
                if (timerCounter==1){
                    tryCounter=1;
                }
                tryCounter--;
                tries.setText("Tries Left : "+tryCounter);
                clickCounter++;
                //The part to be executed when the user have used all three tries.
                if(tryCounter==0){
                    button = findViewById(R.id.BtnHint);
                    button.setText("Next");
                    results.setText("Wrong");
                    tries.setText("Tries Left : 0");
                    results.setTextColor(Color.rgb(186, 45, 49));
                    answer.setText(guessWord.toUpperCase());
                    answer.setTextColor(Color.rgb(219, 204, 33));
                    if (timerCounter==1 || timerCounter==2) {
                        countDownTimer.cancel();
                    }
                }
            }
            charText.setText(null);
        }
    }

    public void onHintButton(View view) {
        checkingConditions();
    }

    //Creating the function that need to be executed when the timer is 0.
    @SuppressLint("SetTextI18n")
    public void submitAuto() {
        if (timerCounter == 3) {
            timerCounter--;
            checkingConditions();
            if (!timerState) {
                timerOn();
            }
        }else if (timerCounter == 2) {
            checkingConditions();
            timerCounter--;
            if (!timerState) {
                timerOn();
            }
        }else if (timerCounter==1){
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