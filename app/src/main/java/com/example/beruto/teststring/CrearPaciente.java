package com.example.beruto.teststring;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;

import java.util.GregorianCalendar;

import modelo.modelo.paciente.GestionPaciente;
import modelo.modelo.paciente.Paciente;


public class CrearPaciente extends AppCompatActivity {
    //TODO comprobar repetidos DNI, y calcular letra
    //TODO el gestor almacena el SIP como string y yo individualmente lo hago como INT! vigila eso
    private GestionPaciente gestor;
    private DatabaseReference dataED = MainActivity.dataED;
    private DatabaseReference dataPaciente = MainActivity.dataPaciente;

    private EditText textNombre;
    private EditText textApellido;
    private EditText textSIP;
    private EditText textCP;

    private RadioButton radioHombre;
    private RadioButton radioMujer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_paciente);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //insert
        textNombre = (EditText) findViewById(R.id.editNombre);
        textApellido = (EditText) findViewById(R.id.editApellidos);
        textSIP = (EditText) findViewById(R.id.editSIP);
        textCP = (EditText) findViewById(R.id.editCP);
        radioHombre = (RadioButton) findViewById(R.id.radioButtonHombre);
        radioMujer = (RadioButton) findViewById(R.id.radioButtonMujer);

        //TODO

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null)
            gestor = (GestionPaciente) bundle.get("GESTOR2");


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!radioHombre.isChecked() && !radioMujer.isChecked())
                    Snackbar.make(view, "Revisa el género", Snackbar.LENGTH_LONG).show();
                else {

                    boolean isHombre = radioHombre.isChecked();

                    String nombre = textNombre.getText().toString();
                    String apellidos = textApellido.getText().toString();
                    String cp = textCP.getText().toString();
                    final String sip = textSIP.getText().toString();
                    String sexo = "H";
                    if (!isHombre)
                        sexo = "M";
                    if (creaPaciente(nombre, apellidos, sip, cp, sexo))
                        Snackbar.make(view, "Paciente añadido", Snackbar.LENGTH_LONG)
                                .setAction("Deshacer", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        deshacer(sip);
                                    }
                                }).show();

                    else
                        Snackbar.make(view, "revisa los campos", Snackbar.LENGTH_LONG).show();
                }


            }
        });
    }


    private boolean creaPaciente(String nombre, String apellidos, String sip, String cp, String sexo) {
        final int SIP_CHECKED = checkSIP(sip);
        final int CP_CHECKED = checkCP(cp);
        final Gson gson = new Gson();


        if (SIP_CHECKED != -1 && CP_CHECKED != -1) {
            Paciente p = new Paciente(nombre, apellidos, SIP_CHECKED, new GregorianCalendar(), sexo, CP_CHECKED);
            gestor.addPaciente(p);
            dataED.setValue(gson.toJson(gestor));
            dataPaciente.child(sip).setValue(gson.toJson(p));
            return true;
        }
        return false;


    }

    private Integer checkSIP(String sip) {
        try {
            Integer n = Integer.parseInt(sip);
            return n;
        } catch (Exception e) {
            return -1;
        }

    }

    private Integer checkCP(String cp) {
        try {
            Integer n = Integer.parseInt(cp);
            return n;
        } catch (Exception e) {
            return -1;
        }
    }

    private void deshacer(String sip) {
        Gson gson = new Gson();
        int s = checkSIP(sip);
        if (s != -1) {
            Paciente p = gestor.searchPaciente(s);
            gestor.removePaciente(p);
            dataED.setValue(gson.toJson(gestor));
            dataPaciente.child(sip).removeValue();
        }

    }

}
