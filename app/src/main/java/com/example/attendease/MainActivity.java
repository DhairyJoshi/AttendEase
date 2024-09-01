package com.example.attendease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void gotoSignUp(View view)
    {
        startActivity(new Intent(this, signup.class));
    }

    public void gotoLogin(View view)
    {
        startActivity(new Intent(this, login.class));
    }

    public void gotoAboutUs(View view)
    {
        startActivity(new Intent(this, aboutus.class));
    }
}