package com.example.cubefaster;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ResultPage extends AppCompatActivity {

    CubeDBHandler cubeDB;
    CubeFasterHelpers cubeFasterHelpers = new CubeFasterHelpers();

    TextView textView_1st, textView_2nd, textView_3rd, textView_4th, textView_5th, textView_result;
    Button btn_tryAgain, btn_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_page);
        cubeDB = new CubeDBHandler(this);

        textView_1st = (TextView) findViewById(R.id.textView_1st);
        textView_2nd = (TextView) findViewById(R.id.textView_2nd);
        textView_3rd = (TextView) findViewById(R.id.textView_3rd);
        textView_4th = (TextView) findViewById(R.id.textView_4th);
        textView_5th = (TextView) findViewById(R.id.textView_5th);
        textView_result = (TextView) findViewById(R.id.textView_result);
        btn_tryAgain = (Button) findViewById(R.id.btn_tryAgain);
        btn_save = (Button) findViewById(R.id.btn_save);

        Intent intent = getIntent();
        final ArrayList<CharSequence> times = (ArrayList<CharSequence>) getIntent().getSerializableExtra("times");
        Collections.sort(times,null);
        textView_1st.setText(String.valueOf(times.get(0)));
        textView_1st.setPaintFlags(textView_1st.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        textView_2nd.setText(String.valueOf(times.get(1)));
        textView_3rd.setText(String.valueOf(times.get(2)));
        textView_4th.setText(String.valueOf(times.get(3)));
        textView_5th.setText(String.valueOf(times.get(4)));
        textView_5th.setPaintFlags(textView_5th.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);

        int averageTime = (cubeFasterHelpers.timeToMilliseconds((times.get(1))) + cubeFasterHelpers.timeToMilliseconds(times.get(2)) + cubeFasterHelpers.timeToMilliseconds(times.get(3))) / 3;
        textView_result.setText(String.valueOf(cubeFasterHelpers.timeConvert((averageTime))));

        btn_tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultPage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date currentDateTime = new Date();

                boolean success = cubeDB.addT3x3data("T3x3",
                        currentDateTime,
                        times.get(0).toString(),
                        times.get(1).toString(),
                        times.get(2).toString(),
                        times.get(3).toString(),
                        times.get(4).toString(),
                        textView_result.getText().toString());

                if (success = true)
                    Toast.makeText(ResultPage.this, "Results saved",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(ResultPage.this, "Results not saved",Toast.LENGTH_LONG).show();
            }
        });
    }
}