package com.example.attendease;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signup extends AppCompatActivity
{
    EditText Name, Username, Email, Password, C_Password;
    String name, username, email, password, c_password;
    db_conn conn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Name = findViewById(R.id.Name);
        Username = findViewById(R.id.Username);
        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Pass);
        C_Password = findViewById(R.id.C_Pass);
    }

    public void executeSignUp (View view)
    {
        name = Name.getText().toString();
        username = Username.getText().toString();
        email = Email.getText().toString();
        password = Password.getText().toString();
        c_password = C_Password.getText().toString();
        if (checkName() && checkUsername() && checkEmail() && checkPassword() && checkC_Password())
        {
            if (password.equals(c_password))
            {
                conn = new db_conn(this);
                Cursor cursor = conn.userExists();
                while (cursor.moveToNext())
                {
                    if (cursor.getString(0).equals(username))
                    {
                        Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show();
                    }
                }
                if (conn.signup(name, username, email, password))
                {
                    Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, login.class));
                }
                else
                {
                    Toast.makeText(this, "Unknown Error Occurred", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                }
            }
            else
            {
                Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean checkName ()
    {
        if (Name.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show();
            Name.setError("Name is required");
            Name.requestFocus();
            return false;
        }
        return true;
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

    private boolean checkEmail ()
    {
        if (Email.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Email is required", Toast.LENGTH_SHORT).show();
            Email.setError("Email is required");
            Email.requestFocus();
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

    private boolean checkC_Password ()
    {
        if (C_Password.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            C_Password.setError("Password is required");
            C_Password.requestFocus();
            return false;
        }
        return true;
    }
}