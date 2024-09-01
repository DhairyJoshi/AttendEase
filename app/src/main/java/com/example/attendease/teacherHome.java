package com.example.attendease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class teacherHome extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
    }

    public void attendanceClicked (View view)
    {
        startActivity(new Intent(this, Attendance.class));
    }

    public void viewClicked (View view)
    {
        startActivity(new Intent(this, viewAttendance.class));
    }

    public void addClassClicked (View view)
    {
        startActivity(new Intent(this, addClass.class));
    }

    public void removeClassClicked (View view)
    {
        startActivity(new Intent(this, removeClass.class));
    }

    public void addStudentClicked (View view)
    {
        startActivity(new Intent(this, addStudent.class));
    }

    public void removeStudentClicked (View view)
    {
        startActivity(new Intent(this, removeStudent.class));
    }
}
