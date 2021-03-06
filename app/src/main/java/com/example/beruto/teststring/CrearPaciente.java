package com.example.beruto.teststring;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.GregorianCalendar;

import modelo.modelo.paciente.GestionMedicos;
import modelo.modelo.paciente.GestionPaciente;
import modelo.modelo.paciente.Medico;
import modelo.modelo.paciente.Paciente;


public class CrearPaciente extends AppCompatActivity {
    //TODO comprobar repetidos DNI, y calcular letra
    //TODO el gestor almacena el SIP como string y yo individualmente lo hago como INT! vigila eso
    private GestionPaciente gestor;
    //    private GestionMedicos gestionMedicos;
    private DatabaseReference dataED = MainActivity.dataED;
    private DatabaseReference dataPaciente = MainActivity.dataPaciente;
    private DatabaseReference dataMedico = MainActivity.dataMedicos;
    //FirebaseAuth
    private FirebaseAuth mAuth = MyLoginActivity.mAuth;


    private EditText textNombre;
    private EditText textApellido;
    private EditText textSIP;
    private EditText textCP;
    private EditText textNacimiento;

    private RadioButton radioHombre;
    private RadioButton radioMujer;
    final Paciente paciente = new Paciente(null,null,0,null,null,0);


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
        textNacimiento = (EditText) findViewById(R.id.datePicker);
        radioHombre = (RadioButton) findViewById(R.id.radioButtonHombre);
        radioMujer = (RadioButton) findViewById(R.id.radioButtonMujer);


        //TODO

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            gestor = (GestionPaciente) bundle.get("GESTOR2");
//            gestionMedicos = new GestionMedicos();
        }


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

        //FOCUS nacimiento
        textNacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                BirthPicker myDialogFragment = new BirthPicker();
                myDialogFragment.setPaciente(paciente);
                myDialogFragment.show(getFragmentManager(),"datePicker");
            }
        });

    }

//        dataMedico.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if(dataSnapshot.getValue()!=null)
//                    gestionMedicos = (GestionMedicos) new Gson().fromJson(dataSnapshot.getValue().toString(), GestionMedicos.class);
//                else
//                    gestionMedicos = new GestionMedicos();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }


    private boolean creaPaciente(String nombre, String apellidos, String sip, String cp, String sexo) {
        final int SIP_CHECKED = checkSIP(sip);
        final int CP_CHECKED = checkCP(cp);
        final Gson gson = new Gson();


        if (SIP_CHECKED != -1 && CP_CHECKED != -1) {
            Paciente p = new Paciente(nombre, apellidos, SIP_CHECKED, paciente.getFechaNacimiento(), sexo, CP_CHECKED);

            if (gestor.searchPaciente(SIP_CHECKED) == null) {
                gestor.addPaciente(p);
                dataED.setValue(gson.toJson(gestor));
                dataPaciente.child(Integer.toString(p.getDni())).setValue(gson.toJson(p));
            }
            else
                aviso(p);

//            gestionMedicos.addMedico(new Medico(
//                    mAuth.getCurrentUser().getDisplayName(),
//                    mAuth.getCurrentUser().getUid(),
//                    mAuth.getCurrentUser().getPhotoUrl().toString()),
//                    p);

//            dataMedico.setValue(gson.toJson(gestionMedicos));

//            dataED.setValue(gson.toJson(gestor));
//            dataPaciente.child(sip).setValue(gson.toJson(p));
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
    private void aviso(final Paciente unPaciente){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setPositiveButton("Sustituir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Gson g = new Gson();
                gestor.addPaciente(unPaciente);
                Log.d("DNI", Integer.toString(unPaciente.getDni()));
                dataED.setValue(g.toJson(gestor));
                dataPaciente.child(Integer.toString(unPaciente.getDni())).setValue(g.toJson(unPaciente));

            }
        });
        alertBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertBuilder.setMessage("Ya hay un paciente con el mismo DNI "+ unPaciente.getDni());
        alertBuilder.setTitle("Aviso");
        alertBuilder.setCancelable(false);
        alertBuilder.create().show();
    }

}
