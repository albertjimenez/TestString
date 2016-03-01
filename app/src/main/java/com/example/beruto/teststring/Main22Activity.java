package com.example.beruto.teststring;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.GregorianCalendar;

import modelo.modelo.paciente.GestionPaciente;
import modelo.modelo.paciente.Paciente;

public class Main22Activity extends AppCompatActivity implements View.OnClickListener  {
    private GestionPaciente gestor;
    private TextView textoPaciente;
    private EditText campoSIP;
    private Button botonComprobar;
    private final String BASE_DE_DATOS = "BD";
    private final String URI_SHAREDPREFERENCES = "GestionPaciente";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);
        textoPaciente = (TextView) findViewById(R.id.textoPaciente);
        campoSIP = (EditText) findViewById(R.id.cajonSIP);
        botonComprobar = (Button) findViewById(R.id.botonComprobar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle!=null)
            gestor = (GestionPaciente) bundle.get("GESTOR");

        botonComprobar.setOnClickListener(this);


    }
    private void modelo(){
        String valorRecogido = campoSIP.getText().toString();
        Paciente p = null;
        int sip = -1;
            if(valorRecogido.equals(""))
                Toast.makeText(getApplicationContext(), "Campo vacío", Toast.LENGTH_LONG).show();
            else{
                try {
                    sip = Integer.parseInt(valorRecogido);
                     p = gestor.searchPaciente(sip);

                }
                catch (NumberFormatException e){
                    Toast.makeText(getApplicationContext(), "No es un número", Toast.LENGTH_LONG).show();
                }

                if(p!=null)
                    textoPaciente.setText(p.toString2());
                else if(sip>=0){
                    textoPaciente.setText("No existo...aún, pero lo añado");
                    Paciente patient = new Paciente("Santi", "Hospitalero", sip,
                            new GregorianCalendar(), "H", "Soltero",
                            "Vinaros", "Castellon", 12500, "Franky");
                    gestor.addPaciente(patient);
                    guardarGestor();
                    Toast.makeText(getApplicationContext(), "Paciente añadido", Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "Negativos no, gracias", Toast.LENGTH_LONG).show();
            }
    }

    @Override
    public void onClick(View v) {
        modelo();
    }

    private void guardarGestor(){
        SharedPreferences loader = getSharedPreferences(URI_SHAREDPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loader.edit();
        Gson gson = new Gson();
        String gestorJSON = gson.toJson(gestor,GestionPaciente.class);
        editor.putString(BASE_DE_DATOS,gestorJSON);
        editor.commit();
    }
}

