package com.example.attendease;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class addStudent extends AppCompatActivity
{
    EditText stuSize;
    String selectedClass;
    int studentSize;
    db_conn conn;

    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        stuSize = findViewById(R.id.studentsSize);

        conn = new db_conn(this);
        int len = conn.getTableCount();
        if (len == 3) {
            Toast.makeText(this, "No Classes To Be Removed", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, teacherHome.class));
        }
        String[] classes = new String[len - 3];
        Cursor cursor = conn.getClasses();
        int i = 0;
        while (cursor.moveToNext()) {
            if (i >= 3) {
                classes[i - 3] = cursor.getString(0);
            }
            i++;
        }
        Spinner spinner = findViewById(R.id.selectedClass);
        List<String> items = Arrays.asList(classes);
        Collections.sort(items);
        customSpinnerAdapter adapter = new customSpinnerAdapter(this, items);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedClass = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void Submit (View view)
    {
        if (checkStuSize())
        {
            studentSize = Integer.parseInt(stuSize.getText().toString());
            boolean studentsAdded = conn.addStudent(selectedClass, studentSize);
            if (studentsAdded)
            {
                Toast.makeText(this, studentSize + " Students Added", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Unknown Error Occurred", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private boolean checkStuSize ()
    {
        if (stuSize.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Size is required", Toast.LENGTH_SHORT).show();
            stuSize.setError("Size is required");
            stuSize.requestFocus();
            return false;
        }
        return true;
    }
}
