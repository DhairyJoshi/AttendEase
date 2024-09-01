package com.example.attendease;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class addClass extends AppCompatActivity
{
    EditText ClassName, ClassSize;
    String className;
    int classSize;
    db_conn conn;


    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        ClassName = findViewById(R.id.ClassName);
        ClassSize = findViewById(R.id.ClassSize);
    }

    public void executeAdd (View view)
    {
        className = ClassName.getText().toString();
        classSize = Integer.parseInt(ClassSize.getText().toString());
        conn = new db_conn(this);
        if (checkClassName() && checkClassSize())
        {
            if(conn.classExists(className))
            {
                Toast.makeText(this, className + " already exists", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (conn.addClass(className, classSize))
                {
                    Toast.makeText(this, className + " created", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "Unknown error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private boolean checkClassName ()
    {
        if (ClassName.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Class Name is required", Toast.LENGTH_SHORT).show();
            ClassName.setError("Class Name is required");
            ClassName.requestFocus();
            return false;
        }
        return true;
    }

    private boolean checkClassSize ()
    {
        if (ClassSize.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Class Size is required", Toast.LENGTH_SHORT).show();
            ClassSize.setError("Class Size is required");
            ClassSize.requestFocus();
            return false;
        }
        return true;
    }
}