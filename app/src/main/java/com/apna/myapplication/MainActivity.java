package com.apna.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.apna.mylibrary.LibraryMain;

public class MainActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Inflate the XML layout for MainActivity

        Button buttonLaunch = findViewById(R.id.buttonLaunch);
        buttonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the ExampleActivity from the library
                Intent intent = new Intent(MainActivity.this, LibraryMain.class);
                startActivity(intent);
            }
        });
    }
}
