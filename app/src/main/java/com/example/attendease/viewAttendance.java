package com.example.attendease;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class viewAttendance extends AppCompatActivity
{
    String selectedDate, selectedClass;
    Button date;
    db_conn conn;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);
        date = findViewById(R.id.date);

        conn = new db_conn(this);
        int len = conn.getTableCount();
        if (len == 3)
        {
            Toast.makeText(this, "No Classes Added", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, teacherHome.class));
        }
        String[] classes = new String[len-3];
        Cursor cursor = conn.getClasses();
        int i = 0;
        while (cursor.moveToNext())
        {
            if (i >= 3)
            {
                classes[i-3] = cursor.getString(0);
            }
            i++;
        }
        Spinner spinner = findViewById(R.id.selectedClass);
        List<String> items = Arrays.asList(classes);
        Collections.sort(items);
        customSpinnerAdapter adapter = new customSpinnerAdapter(this, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void pickDate (View view)
    {
        Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view1, year1, monthOfYear, dayOfMonth) -> selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year1, year, month, day);
        datePickerDialog.show();
        date.setText(selectedDate);
    }

    public void Submit (View view)
    {
        boolean dateExists = conn.checkIfDateExists(selectedClass, selectedDate);
        if (dateExists)
        {
            int size = conn.getColumnCount(selectedClass);
            String[] colValues = new String[size + 2];
            Cursor cursor = conn.returnAttendance(selectedClass, selectedDate);
            if (cursor.moveToNext())
            {
                for (int j = 0; j < cursor.getColumnCount(); j++)
                {
                    colValues[j] = cursor.getString(j);
                }
            }
            cursor.close();
            String[] filteredValues = new String[colValues.length - 2];
            System.arraycopy(colValues, 2, filteredValues, 0, filteredValues.length);
            Toast.makeText(this, filteredValues.length + "", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, viewAttendance2.class);
            intent.putExtra("array_key", filteredValues);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Records for " + selectedDate + " do not exist", Toast.LENGTH_SHORT).show();
        }
    }
}