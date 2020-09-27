package com.example.cubefaster;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends OptionsMenuActivity {

    Handler timeHandler = new Handler();
    CubeFasterHelpers cubeFasterHelpers = new CubeFasterHelpers();
    long startTime = 0L, timeInMilliSeconds = 0L, timeSwapBuffer = 0L, updateTime = 0L;

    Button button;
    TextView textView_time, textView_1st, textView_2nd, textView_3rd, textView_4th, textView_5th, textView_scramble;
    ArrayList<CharSequence> times = new ArrayList<CharSequence>();
    int round = 0;

    //Cube timing.
    Runnable updateTimeThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliSeconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuffer+timeInMilliSeconds;
            int secs = (int)(updateTime/1000);
            int mins=secs/60;
            secs%=60;
            int milliseconds=(int)(updateTime%1000);
            //textView_time.setText(""+mins+":"+String.format("%2d",secs)+":"+String.format("%3d",milliseconds));
            textView_time.setText(mins + ":" + secs + ":"  + milliseconds);
            timeHandler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        button.setText("Start");
        textView_time = (TextView) findViewById(R.id.textView_time);

        textView_time.setText("Ready for first round?\nCube Faster!");
        textView_1st = (TextView) findViewById(R.id.textView_1st);
        textView_1st.setText("First round time...");
        textView_2nd = (TextView) findViewById(R.id.textView_2nd);
        textView_2nd.setText("Second round time...");
        textView_3rd = (TextView) findViewById(R.id.textView_3rd);
        textView_3rd.setText("Third round time...");
        textView_4th = (TextView) findViewById(R.id.textView_4th);
        textView_4th.setText("Fourth round time...");
        textView_5th = (TextView) findViewById(R.id.textView_5th);
        textView_5th.setText("Fifth round time...");

        //Scramble the cube.
        CubeFasterScrambleGenerator scrambler = new CubeFasterScrambleGenerator(25);
        textView_scramble = (TextView) findViewById(R.id.textView_scramble);
        textView_scramble.setText(scrambler.getScramble());

        //Going through all five tournament times using switch-case. At the end moving to another activity.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CubeFasterScrambleGenerator scrambler = new CubeFasterScrambleGenerator(25);
                button.setText("Stop");
                int duration; String time;
                startTime = SystemClock.uptimeMillis();
                timeHandler.postDelayed(updateTimeThread,0);

                switch (round) {
                    case 1:
                        //Stopping time. Converting time to milliseconds and then converting time to format mm:ss:mss. Same goes to all cases.
                        timeHandler.removeCallbacks(updateTimeThread);
                        duration = cubeFasterHelpers.timeToMilliseconds(textView_time.getText());
                        time = cubeFasterHelpers.timeConvert((duration));
                        times.add(time);
                        textView_1st.setText(times.get(0));
                        textView_time.setText("Ready for second round?\nCube Faster!");
                        textView_scramble.setText(scrambler.getScramble());
                        button.setText("Start");
                        break;
                    case 3:
                        timeHandler.removeCallbacks(updateTimeThread);
                        duration = cubeFasterHelpers.timeToMilliseconds(textView_time.getText());
                        time = cubeFasterHelpers.timeConvert((duration));
                        times.add(time);
                        textView_2nd.setText(times.get(1));
                        textView_time.setText("Ready for third round?\nCube Faster!");
                        textView_scramble.setText(scrambler.getScramble());
                        button.setText("Start");
                        break;
                    case 5:
                        timeHandler.removeCallbacks(updateTimeThread);
                        duration = cubeFasterHelpers.timeToMilliseconds(textView_time.getText());
                        time = cubeFasterHelpers.timeConvert((duration));
                        times.add(time);
                        textView_3rd.setText(times.get(2));
                        textView_time.setText("Ready for fourth round?\nCube Faster!");
                        button.setText("Start");
                        break;
                    case 7:
                        timeHandler.removeCallbacks(updateTimeThread);
                        duration = cubeFasterHelpers.timeToMilliseconds(textView_time.getText());
                        time = cubeFasterHelpers.timeConvert((duration));
                        times.add(time);
                        textView_4th.setText(times.get(3));
                        textView_time.setText("Ready for final round?\nCube Faster!");
                        textView_scramble.setText(scrambler.getScramble());
                        button.setText("Start");
                        break;
                    case 9:
                        timeHandler.removeCallbacks(updateTimeThread);
                        duration = cubeFasterHelpers.timeToMilliseconds(textView_time.getText());
                        time = cubeFasterHelpers.timeConvert((duration));
                        times.add(time);
                        textView_5th.setText(times.get(4));
                        Intent intent = new Intent(MainActivity.this, ResultPage.class);
                        intent.putExtra("times", times);
                        startActivity(intent);
                        break;
                }
                round++;
            }});
    }
}