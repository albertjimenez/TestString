package com.example.beruto.teststring;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main22Activity extends AppCompatActivity  {
    TextView cajaTexto;
    private String numAsignaturas = "Ye";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cajaTexto = (TextView) findViewById(R.id.textView3);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
            numAsignaturas = (String) bundle.get("ASIGNATURAS");
        else
            numAsignaturas = "0";

        cajaTexto.setText(numAsignaturas + " asignaturas");

    }




    }

