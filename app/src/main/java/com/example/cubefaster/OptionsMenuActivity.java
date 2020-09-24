package com.example.cubefaster;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class OptionsMenuActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.cube_faster_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.t3x3:
                Intent t3x3 = new Intent(this, MainActivity.class);
                startActivity(t3x3);
                return true;

            case R.id.s3x3:
                Intent s3x3 = new Intent(this, Single3x3.class);
                startActivity(s3x3);
                return true;

            case R.id.stats:
                Intent stats = new Intent(this,StatisticPage.class);
                startActivity(stats);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
