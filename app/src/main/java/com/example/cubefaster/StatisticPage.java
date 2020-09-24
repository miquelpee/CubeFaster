package com.example.cubefaster;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StatisticPage extends OptionsMenuActivity {

    CubeDBHandler cubeDB;
    TextView textView_t3x3, textView_s3x3;
    Button button_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic_page);

        textView_t3x3 = (TextView) findViewById(R.id.textView_t3x3);
        textView_t3x3.setMovementMethod(new ScrollingMovementMethod());
        textView_s3x3 = (TextView) findViewById(R.id.textView_s3x3);
        textView_s3x3.setMovementMethod(new ScrollingMovementMethod());
        button_delete = (Button) findViewById(R.id.button_delete);

        cubeDB = new CubeDBHandler(this);
        int countT = 1, countS = 1;

        Cursor t3x3 = cubeDB.getT3x3Data();
        StringBuffer t3x3Data = new StringBuffer();

        int i = t3x3.getCount();

        while(t3x3.moveToNext()){
            t3x3Data.append(">>> " + countT + " <<<\n");
            t3x3Data.append("Round average: " + t3x3.getString(8)+"\n");
            t3x3Data.append("Time 1: " + t3x3.getString(4)+"\n");
            t3x3Data.append("Time 2: " + t3x3.getString(5)+"\n");
            t3x3Data.append("Time 3: " + t3x3.getString(6)+"\n");
            t3x3Data.append("Removed times: " + t3x3.getString(3)+ " & " + t3x3.getString(7) + "\n\n");
            countT++;
        }
        textView_t3x3.setText(t3x3Data.toString());

        Cursor s3x3 = cubeDB.getS3x3Data();
        StringBuffer s3x3Data = new StringBuffer();

        while(s3x3.moveToNext()){
            s3x3Data.append(">>> " + countS + " <<<\n");
            s3x3Data.append("Time : " + s3x3.getString(3) + "\n\n");
            countS++;
        }
        textView_s3x3.setText(s3x3Data.toString());

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticPage.this, DeleteTimes.class);
                startActivity(intent);
            }
        });
    }
}