package com.example.cubefaster;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Single3x3 extends OptionsMenuActivity {

    CubeDBHandler cubeDB;

    Handler timeHandler = new Handler();
    CubeFasterHelpers cubeFasterHelpers = new CubeFasterHelpers();
    long startTime = 0L, timeInMilliSeconds = 0L, timeSwapBuffer = 0L, updateTime = 0L;

    Button button, btn_save;
    TextView textView_time, textView_scramble;
    int round = 0;

    Runnable updateTimeThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuffer+timeInMilliSeconds;
            int secs = (int)(updateTime/1000);
            int mins=secs/60;
            secs%=60;
            int milliseconds=(int)(updateTime%1000);
            //textView_time.setText(""+mins+":"+String.format("%2d",secs)+":"+String.format("%2d",milliseconds));
            textView_time.setText(mins + ":" + secs + ":"  + milliseconds);
            timeHandler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single3x3);
        cubeDB = new CubeDBHandler(this);

        button = (Button) findViewById(R.id.button);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_save.setEnabled(false);
        button.setText("Start");
        textView_time = (TextView) findViewById(R.id.textView_time);
        textView_time.setText("Cube Faster!");
        textView_scramble = (TextView) findViewById(R.id.textView_scramble);

        CubeFasterScrambleGenerator scrambler = new CubeFasterScrambleGenerator(25);
        textView_scramble.setText(scrambler.getScramble());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CubeFasterScrambleGenerator scrambler = new CubeFasterScrambleGenerator(25);
                button.setText("Stop");
                btn_save.setEnabled(false);
                int duration; String time;
                startTime = SystemClock.uptimeMillis();
                timeHandler.postDelayed(updateTimeThread, 0);

                switch (round) {
                    case 1:
                        timeHandler.removeCallbacks(updateTimeThread);
                        duration = cubeFasterHelpers.timeToMilliseconds(textView_time.getText());
                        time = cubeFasterHelpers.timeConvert((duration));
                        textView_time.setText(time);
                        button.setText("Start");
                        btn_save.setEnabled(true);
                        textView_scramble.setText(scrambler.getScramble());
                        round = -1;
                        break;
                }
                round++;
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentDateTime = new Date();
                boolean success = cubeDB.addS3x3data("S3x3", currentDateTime, textView_time.getText().toString());

                if (success = true)
                    Toast.makeText(Single3x3.this, "Results saved",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Single3x3.this, "Results not saved",Toast.LENGTH_LONG).show();
            }
        });
    }
}