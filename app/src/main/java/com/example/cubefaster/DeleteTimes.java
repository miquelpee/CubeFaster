package com.example.cubefaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DeleteTimes extends AppCompatActivity{

    CubeDBHandler cubeDB;

    public ListView getT3x3Data(){
        ListView t3x3ListView = (ListView) findViewById(R.id.delete_t3x3);
        cubeDB = new CubeDBHandler(this);
        Cursor t3x3DB = cubeDB.getT3x3DataDel();
        ArrayList <String> t3x3ArrayList = new ArrayList<>();

        int countT = 1;

        while(t3x3DB.moveToNext()){
            t3x3ArrayList.add("Time no : " + countT + " | AVG time : " + t3x3DB.getString(1) + " | ID : " + t3x3DB.getString(0));
            countT++;
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, t3x3ArrayList);
        t3x3ListView.setAdapter(adapter);
        return t3x3ListView;
    }

    public ListView getS3x3Data(){
        ListView s3x3ListView = (ListView) findViewById(R.id.delete_s3x3);
        cubeDB = new CubeDBHandler(this);
        Cursor s3x3DB = cubeDB.getS3x3DataDel();
        ArrayList <String> s3x3ArrayList = new ArrayList<>();

        int countT = 1;

        while(s3x3DB.moveToNext()){
            s3x3ArrayList.add("Time no : " + countT + " | Time : " + s3x3DB.getString(1) + " | ID : " + s3x3DB.getString(0));
            countT++;
        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, s3x3ArrayList);
        s3x3ListView.setAdapter(adapter);
        return s3x3ListView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_times);

        Button button_back = (Button) findViewById(R.id.button_back);
        getT3x3Data();
        getS3x3Data();

        getT3x3Data().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = getT3x3Data().getItemAtPosition(position).toString();
                int pos = text.indexOf("ID");
                String idToDel = text.substring(pos+5);
                boolean success = cubeDB.delT3x3Data(idToDel);

                if (success = true)
                    Toast.makeText(DeleteTimes.this, "Record deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(DeleteTimes.this, "Record not deleted",Toast.LENGTH_LONG).show();

                getT3x3Data();
            }
        });

        getS3x3Data().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = getS3x3Data().getItemAtPosition(position).toString();
                int pos = text.indexOf("ID");
                String idToDel = text.substring(pos+5);
                boolean success = cubeDB.delS3x3Data(idToDel);

                if (success = true)
                    Toast.makeText(DeleteTimes.this, "Record deleted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(DeleteTimes.this, "Record not deleted",Toast.LENGTH_LONG).show();

                getS3x3Data();
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeleteTimes.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}