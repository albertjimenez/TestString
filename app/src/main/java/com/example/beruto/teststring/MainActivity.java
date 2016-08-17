package com.example.beruto.teststring;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import modelo.modelo.paciente.GestionPaciente;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView etiquetaTexto;
    private Button botonMuestraPacientes;
    private Button botonCrearPacientes;
    private GestionPaciente gestor;
    private final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;



//    private final String BASE_DE_DATOS = "BD";
//    private final String URI_SHAREDPREFERENCES = "GestionPaciente";


//    public static String FIREBASE_URL = "https://console.firebase.google.com/project/validadorgesthosp";
    public static String FIREBASE_ED = "EstructuraDeDatos";
    public static String FIREBASE_PACIENTES = "pacientes";

    public static FirebaseDatabase firebaseGestionHospital = FirebaseDatabase.getInstance();

    public static DatabaseReference dataPaciente;

    public static DatabaseReference dataED;
    private Bundle bundle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //firebaseGestionHospital.setPersistenceEnabled(true);


        bundle = getIntent().getExtras();


        dataPaciente = firebaseGestionHospital.getReference().child(FIREBASE_PACIENTES);
        dataED = firebaseGestionHospital.getReference().child(FIREBASE_ED);
        dataED.keepSynced(true);
        dataPaciente.keepSynced(true);


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
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == event.KEYCODE_BACK) {
//            MyLoginActivity.mAuth.signOut();
            finish();
        }
        return true;
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
                Intent intent = new Intent(this, Main22Activity.class);
                intent.putExtra("GESTOR", gestor);
                startActivity(intent);
                break;
            case R.id.button:
                Intent intent1 = new Intent(this, CrearPaciente.class);
                intent1.putExtra("GESTOR2", gestor);
                startActivity(intent1);
                break;

        }

    }

//    public boolean isGooglePlayServicesAvailable(Context context) {
//        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
//        return resultCode == ConnectionResult.SUCCESS;
//
//
//    }

    private boolean checkPlayServices() {
        GoogleApiAvailability gApi = GoogleApiAvailability.getInstance();
        int resultCode = gApi.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (gApi.isUserResolvableError(resultCode)) {
                gApi.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
               // Toast.makeText(this, getResources().getString(R.string.common_google_play_services_api_unavailable_text), Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }


}
