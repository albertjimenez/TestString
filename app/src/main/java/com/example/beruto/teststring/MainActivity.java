package com.example.beruto.teststring;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;

import modelo.modelo.paciente.GestionPaciente;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView etiquetaTexto;
    private Button botonMuestraPacientes;
    private Button botonCrearPacientes;
    private GestionPaciente gestor;
//    private final String BASE_DE_DATOS = "BD";
//    private final String URI_SHAREDPREFERENCES = "GestionPaciente";
    public static Firebase firebaseGestionHospital;

    public static String FIREBASE_URL = "https://gestion-hospital.firebaseio.com";
    public static String FIREBASE_CHILD = "EstructuraDeDatos";
    public static String FIREBASE_CHILD_SIP = "pacientes";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        firebaseGestionHospital= new Firebase(FIREBASE_URL);
        //inicializaciones de mis atributos
        //los rescato de content_main.xml para tener acceso

        etiquetaTexto = (TextView) findViewById(R.id.textView);
        botonMuestraPacientes = (Button) findViewById(R.id.button2);
        botonMuestraPacientes.setOnClickListener(this);
        botonCrearPacientes = (Button) findViewById(R.id.button);
        botonCrearPacientes.setOnClickListener(this);
        gestor = new GestionPaciente();
        //cargarGestor();
    }
//    private void cargarGestor(){
//        Gson gson = new Gson();
//        SharedPreferences loader = getSharedPreferences("GestionPaciente", Context.MODE_PRIVATE);
//        String gestorJSON = loader.getString(BASE_DE_DATOS, "");
//        if(gestor==null)
//            gestor=new GestionPaciente();
//        else
//            gestor = gson.fromJson(gestorJSON,GestionPaciente.class);
//    }
    @Override
    protected void onResume() {
        super.onResume();
       // cargarGestor();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(this,Main22Activity.class);
                intent.putExtra("GESTOR",gestor);
                startActivity(intent);
                break;
            case R.id.button:
                Intent intent1 = new Intent(this,CrearPaciente.class);
                startActivity(intent1);
                break;

        }
    }



}
