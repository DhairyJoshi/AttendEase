package com.example.attendease;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class setAttendance extends AppCompatActivity
{
    TextView Date;
    TextView Class;
    db_conn conn;

    @SuppressLint("SetTextI18n")
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_attendance);
        Date = findViewById(R.id.Date);
        Class = findViewById(R.id.Class);
        conn = new db_conn(this);

        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("selectedDate");
        String selectedClass = intent.getStringExtra("selectedClass");
        Date.setText(selectedDate);
        Class.setText(selectedClass);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(0, 50, 0, 50);

        for (int i = 1; i <= conn.getColumnCount(selectedClass); i++)
        {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(i);
            checkBox.setLayoutParams(layoutParams);
            checkBox.setText("Roll No: " + i + "              ");
            checkBox.setTextSize(17);
            checkBox.setTextColor(ContextCompat.getColor(this, R.color.blue1));
            checkBox.setButtonTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.blue1)));
            checkBox.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            checkBox.setScaleX(2);
            checkBox.setScaleY(2);

            linearLayout.addView(checkBox);
        }
    }

    public void Submit (View view)
    {
        String selectedClass = Class.getText().toString();
        String selectedDate = Date.getText().toString();
        boolean dateExists = conn.checkIfDateExists(selectedClass, selectedDate);
        if (dateExists)
        {   
            Toast.makeText(this, "Date Exists", Toast.LENGTH_SHORT).show();
        }
        else
        {
            int classSize = conn.getColumnCount(selectedClass);
            String[] classAttendance = new String[classSize];
            for (int i = 1; i <= classSize; i++)
            {
                CheckBox retrievedCheckBox = findViewById(i);
                if (retrievedCheckBox.isChecked())
                {
                    classAttendance[i - 1] = "Present";
                }
                else
                {
                    classAttendance[i - 1] = "Absent";
                }
            }
            boolean attendanceWritten = conn.writeAttendance(selectedClass, selectedDate, classAttendance);
            if (attendanceWritten)
            {
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Unknown error occurred", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
