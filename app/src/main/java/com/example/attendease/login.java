package com.example.attendease;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity
{
    EditText Username, Password;
    String username, password;
    db_conn conn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Username = findViewById(R.id.Username);
        Password = findViewById(R.id.Pass);
    }

    public void executeLogin (View view)
    {
        boolean loggedIn = false;
        username = Username.getText().toString();
        password = Password.getText().toString();
        if (checkUsername() && checkPassword())
        {
            conn = new db_conn(this);
            Cursor cursor = conn.login();
            while (cursor.moveToNext())
            {
                if(cursor.getString(0).equals(username) && cursor.getString(1).equals(password))
                {
                    loggedIn = true;
                }
            }
            if (loggedIn)
            {
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, teacherHome.class));
            }
            else
            {
                Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkUsername ()
    {
        if (Username.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            Username.setError("Username is required");
            Username.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkPassword ()
    {
        if (Password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            Password.setError("Password is required");
            Password.requestFocus();
            return false;
        }
        return true;
    }
}
