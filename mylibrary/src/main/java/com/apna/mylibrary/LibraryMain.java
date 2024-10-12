package com.apna.mylibrary;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LibraryMain extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        TextView textView = findViewById(R.id.textView);
        Button button = findViewById(R.id.button);

        button.setOnClickListener(view -> {
            Toast.makeText(this, "Button clicked!", Toast.LENGTH_SHORT).show();
            requestPermissions();
        });
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Check if permissions are already granted
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // Request permissions
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        },
                        REQUEST_PERMISSION_CODE);
            } else {
                // Permissions are already granted
                Toast.makeText(this, "Permissions are already granted.", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Permissions are automatically granted on older versions
            Toast.makeText(this, "Permissions are granted.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0) {
                boolean cameraPermissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean locationPermissionGranted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (cameraPermissionGranted && locationPermissionGranted) {
                    Toast.makeText(this, "Permissions granted.", Toast.LENGTH_SHORT).show();
                    // You can proceed with your operation here
                } else {
                    Toast.makeText(this, "Permissions denied.", Toast.LENGTH_SHORT).show();
                    // Handle the case where permissions are not granted
                }
            }
        }
    }
}
