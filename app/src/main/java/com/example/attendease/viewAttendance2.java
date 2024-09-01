package com.example.attendease;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Objects;

public class viewAttendance2 extends AppCompatActivity
{
    @SuppressLint("SetTextI18n")
    protected void onCreate (Bundle savedBundleInstance)
    {
        super.onCreate(savedBundleInstance);
        setContentView(R.layout.activity_view_attendance2);

        Intent intent = getIntent();
        String[] receivedArray = intent.getStringArrayExtra("array_key");
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        for (int i = 0; i < Objects.requireNonNull(receivedArray).length; i++)
        {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setText("Rollno " + (i + 1) + ": " + receivedArray[i]);
            textView.setTextSize(40);
            textView.setTextColor(ContextCompat.getColor(this, R.color.blue1));
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textView.getLayoutParams();
            params.setMargins(0, 10, 0, 10);
            textView.setLayoutParams(params);
            linearLayout.addView(textView);
        }
    }
}
