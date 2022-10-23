package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.example.calculator.R;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_menu);
    }

    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.simpleCalcButton:
                Intent simpleCalc = new Intent(this, MainActivity.class);
                startActivity(simpleCalc);
                break;
            case R.id.advCalcButton:
                Intent advCalc = new Intent(this, MainActivity.class);
                advCalc.putExtra("isAdv", "yes");
                startActivity(advCalc);
                break;
            case R.id.aboutButton:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
                break;
            case R.id.exitButton:
                finish();
                System.exit(0);
                break;
        }
    }
}