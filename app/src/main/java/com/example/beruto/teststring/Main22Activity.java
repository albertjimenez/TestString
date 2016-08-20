package com.example.beruto.teststring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.GregorianCalendar;

import modelo.modelo.paciente.GestionMedicos;
import modelo.modelo.paciente.GestionPaciente;
import modelo.modelo.paciente.ImplementacionCallback;
import modelo.modelo.paciente.InterfazCallback;
import modelo.modelo.paciente.Medico;
import modelo.modelo.paciente.Paciente;

public class Main22Activity extends AppCompatActivity implements View.OnClickListener, InterfazCallback {
    private GestionPaciente gestor;
//    private GestionMedicos gestionMedicos;
    private TextView textoPaciente;
    private EditText campoSIP;
    private Button botonComprobar;
    private ImplementacionCallback implementacionCallback = new ImplementacionCallback(this);
    private Gson gson;
//    private final String BASE_DE_DATOS = "BD";
//    private final String URI_SHAREDPREFERENCES = "GestionPaciente";

    private DatabaseReference dataED = MainActivity.dataED;
    private DatabaseReference dataPaciente = MainActivity.dataPaciente;
    private DatabaseReference dataMedico = MainActivity.dataMedicos;

    //    public final String FIREBASE_URL = MainActivity.FIREBASE_URL;
//    public  final String FIREBASE_CHILD = MainActivity.FIREBASE_ED;
//    public  final String FIREBASE_CHILD_SIP = MainActivity.FIREBASE_PACIENTES;
//    GeneradorDatosINE generadorDatosINE = new GeneradorDatosINE();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main22);

        gson = new Gson();
        textoPaciente = (TextView) findViewById(R.id.textoPaciente);
        campoSIP = (EditText) findViewById(R.id.cajonSIP);
        botonComprobar = (Button) findViewById(R.id.botonComprobar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            gestor = (GestionPaciente) bundle.get("GESTOR");
//            gestionMedicos = (GestionMedicos) bundle.get("GESTORM");
        }

        botonComprobar.setOnClickListener(this);


//        dataMedico.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//              if(dataSnapshot.getValue()!=null)
//                  gestionMedicos = (GestionMedicos) gson.fromJson(dataSnapshot.getValue().toString(), GestionMedicos.class);
//
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        dataED.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Gson gson = new Gson();
                String gestorJSON = "";
                if (dataSnapshot.getValue() != null) {
                    gestorJSON = dataSnapshot.getValue().toString();
                } else {
                    gestorJSON = gson.toJson(new GestionPaciente(true), GestionPaciente.class);
                    Toast.makeText(getApplicationContext(), "Sin datos", Toast.LENGTH_SHORT).show();
                }


                try {
                    gestor = gson.fromJson(gestorJSON, GestionPaciente.class);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Ha habido un error desde el servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        dataPaciente.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Paciente p = new Gson().fromJson(dataSnapshot.getValue().toString(), Paciente.class);
                //TODO notificar
               // if (gestor.editPaciente(p))
//                    Toast.makeText(getApplicationContext(), "Se ha editado " + p.getNombre() + " - " + p.getDni(), Toast.LENGTH_SHORT).show();
                 //   notificame(p);

//                else
//                    Toast.makeText(getApplicationContext(), "No se ha podido editar " + p.getNombre() + " - " + p.getDni(), Toast.LENGTH_SHORT).show();

                guardarFireBase();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Paciente p = new Gson().fromJson(dataSnapshot.getValue().toString(), Paciente.class);
                if (gestor.removePaciente(p))
                    Toast.makeText(getApplicationContext(), "Se ha borrado " + p.getNombre() + " - " + p.getDni(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(), "No se ha borrado " + p.getNombre() + " - " + p.getDni(), Toast.LENGTH_SHORT).show();
                guardarFireBase();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        gestor.vaciar();
//        guardarGestor();


    }

    private void modelo() {
        String valorRecogido = campoSIP.getText().toString();
        Paciente p = null;
        int sip = -1;
        if (valorRecogido.equals(""))
            Toast.makeText(getApplicationContext(), "Campo vacío", Toast.LENGTH_LONG).show();
        else {
            try {
                sip = Integer.parseInt(valorRecogido);
                p = gestor.searchPaciente(sip);

            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "No es un número", Toast.LENGTH_LONG).show();
            }

            if (p != null)
                textoPaciente.setText(p.toString2());
            else if (sip >= 0) {
//                    String nombre =generadorDatosINE.getNombre();
//                    String apellido = generadorDatosINE.getApellido();

                String nombre = "Santi";
                String apellido = "Bernabeuses";
                textoPaciente.setText("No existo...aún, pero lo añado");
                Paciente patient = new Paciente(nombre, apellido, sip,
                        new GregorianCalendar(2000, 1, 16), "H", 12500 );
                gestor.addPaciente(patient);
                guardarFireBase();
                guardarPaciente(patient);
                //guardarGestor();

                //Toast.makeText(getApplicationContext(), "Paciente añadido a FireBase", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onClick(View v) {
        modelo();
    }

//    private void guardarGestor(){
//        SharedPreferences loader = getSharedPreferences(URI_SHAREDPREFERENCES, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = loader.edit();
//        Gson gson = new Gson();
//        String gestorJSON = gson.toJson(gestor,GestionPaciente.class);
//        editor.putString(BASE_DE_DATOS,gestorJSON);
//        editor.commit();
//    }

    private void guardarFireBase() {
        Gson gson = new Gson();
        String miGestorJSON = gson.toJson(gestor, GestionPaciente.class);
        dataED.setValue(miGestorJSON);
    }

    private void guardarPaciente(Paciente paciente) {
        Gson gson = new Gson();
        String miPacienteJSON = gson.toJson(paciente, Paciente.class);
        Integer sipPaciente = paciente.getDni();
        dataPaciente.child(sipPaciente.toString()).setValue(miPacienteJSON);
    }

    //TODO Se avisara a todos menos a él mismo
    @Override
    public void notificame(Medico medico) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Atención");
        alertDialogBuilder.setMessage("Se ha modificado el medico:\n" + medico.toString());
        alertDialogBuilder.setPositiveButton("Ok", null);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}

