package com.example.car_identifier;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

@RequiresApi(api = Build.VERSION_CODES.N)
public class IdentifyCarMake extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    private final static CarModels carModels = new CarModels();
    private final Random rnd = new Random();
    private Button button;
    private String randomModelName;
    private String spinnerSelectedModel;
    TextView timer;
    CountDownTimer countDownTimer;
    boolean isTimerOn;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_identify_car_make);

        //Setting up the timer and getting the boolean value of the switch from the Menu Activity.
        timer = findViewById(R.id.text_timer);
        Intent intent = getIntent();
        isTimerOn = intent.getExtras().getBoolean("switchValue");

        //Condition to be executed if the switch is on.
        if (isTimerOn){
            timerOn();
        }
        start();
    }

    //Following code is referred from : https://developer.android.com/reference/android/os/CountDownTimer
    //Setting up the timer.
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
    }//End of referred code.

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    public void start(){
        //Setting up the Image view to set random images.
        ImageView img = findViewById(R.id.imageView_car);
        randomModelName = carModels.carModelsArray.get(rnd.nextInt(10));
        String carName = randomModelName + "_" + rnd.nextInt(3);
        img.setImageDrawable(getResources().getDrawable(getResourceID(carName, getApplicationContext())));

        //Setting up the spinner.
        Spinner spinnerCarModel = findViewById(R.id.spinner_models);
        spinnerCarModel.setOnItemSelectedListener(this);

        //Converting the arrayList to upperCase to use in the spinner.
        List<String> carModelsArrayUpped = carModels.carModelsArray.stream().map(String::toUpperCase).collect(Collectors.toList());

        // Creating adapter for spinner and inserting the values in the list.
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, carModelsArrayUpped);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCarModel.setAdapter(dataAdapter);

        //Set the button label to Submit
        button = findViewById(R.id.submit_button);
        button.setText("Identify");
    }

    //Following code is referred from : https://stackoverflow.com/questions/41130281/random-background-in-android
    protected static int getResourceID(final String resName, final Context ctx) {
        final int ResourceID = ctx.getResources().getIdentifier(resName, "drawable", ctx.getApplicationInfo().packageName);
        if (ResourceID == 0) {
            throw new IllegalArgumentException("No resource string found with name " + resName);
        }else{
            return ResourceID;
        }
    }//End of referred code.

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerSelectedModel = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    //Conditions that should be executed when the button is pressed or in auto submission.
    @SuppressLint({"SetTextI18n", "WrongViewCast"})
    public void checkingConditions(){
        TextView result;
        TextView correct_answer;
        if (button.getText().equals("Identify")){
            button.setText("Next");
            //Checking if the selected answer is correct.
            if (spinnerSelectedModel.equalsIgnoreCase(randomModelName) ){
                String answer = "CORRECT !";
                result = findViewById(R.id.text_result);
                result.setTextColor(Color.rgb(52, 153, 79));
                result.setText(answer);
                result.setTextSize(35);
            }else {
                String answer = "WRONG !";
                result = findViewById(R.id.text_result);
                result.setTextColor(Color.rgb(186, 45, 49));
                result.setText(answer);
                result.setTextSize(35);

                correct_answer = findViewById(R.id.text_answer);
                correct_answer.setTextColor(Color.rgb(219, 204, 33));
                correct_answer.setText(randomModelName.toUpperCase());
                correct_answer.setTextSize(25);
            }
        }else {
            //Restarting the activity.
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }

    //Creating the function of the Button.
    public void onClickButton(View view) {
        checkingConditions();
    }

    //Creating the function that need to be executed when the timer is 0.
    public void submitAuto(){
        checkingConditions();
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