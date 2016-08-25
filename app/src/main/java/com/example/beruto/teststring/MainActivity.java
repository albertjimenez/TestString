package com.example.beruto.teststring;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import modelo.modelo.paciente.GestionPaciente;
import modelo.modelo.paciente.GraphicThreadController;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView etiquetaTexto;
    private Button botonMuestraPacientes;
    private Button botonCrearPacientes;
    private ImageView imagen;
    private GestionPaciente gestor;
    //    private GestionMedicos gestionMedicos;
    private final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private FirebaseAuth mAuth;

    public final static String FIREBASE_ED = "EstructuraDeDatos";
    public final static String FIREBASE_PACIENTES = "pacientes";
    public final static String FIREBASE_MEDICOS = "medicos";

    public static FirebaseDatabase firebaseGestionHospital = FirebaseDatabase.getInstance();

    public static DatabaseReference dataPaciente;

    public static DatabaseReference dataED;

    public static DatabaseReference dataMedicos;
    private Bundle bundle;
    private String nombreUsuario;
    private String urlUsuario;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //firebaseGestionHospital.setPersistenceEnabled(true);
        TextView usuario = (TextView) findViewById(R.id.profileName);
        imagen = (ImageView) findViewById(R.id.profilePicture);
        imagen.setOnClickListener(this);


        //Recupera Datos MyLoginActivity
        bundle = getIntent().getExtras();
        if (bundle != null) {
            nombreUsuario = bundle.getString("NOMBRE");
            urlUsuario = bundle.getString("FOTO");
            GraphicThreadController.actualizaTexto(usuario, nombreUsuario);
            //usuario.setText(nombreUsuario);
            Glide.with(this).load(urlUsuario).into(imagen);
        }

        //FIREBASE
        dataPaciente = firebaseGestionHospital.getReference().child(FIREBASE_PACIENTES);
        dataED = firebaseGestionHospital.getReference().child(FIREBASE_ED);
        dataMedicos = firebaseGestionHospital.getReference().child(FIREBASE_MEDICOS);
        dataED.keepSynced(true);
        dataPaciente.keepSynced(true);
        dataMedicos.keepSynced(true);
        mAuth = MyLoginActivity.mAuth;

        //UIElements
        etiquetaTexto = (TextView) findViewById(R.id.textView);
        botonMuestraPacientes = (Button) findViewById(R.id.button2);
        botonMuestraPacientes.setOnClickListener(this);
        botonCrearPacientes = (Button) findViewById(R.id.button);
        botonCrearPacientes.setOnClickListener(this);
        gestor = new GestionPaciente();
//        gestionMedicos = new GestionMedicos();
    }


    @Override
    protected void onResume() {
        super.onResume();
        // cargarGestor();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == event.KEYCODE_BACK) {
//            MyLoginActivity.mAuth.signOut();
            moveTaskToBack(true);
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
        switch (id) {
            case R.id.logout:
                //            signout();
                return true;

            case R.id.share:

                String nombre, correo;
                nombre = mAuth.getCurrentUser().getDisplayName();
                correo = mAuth.getCurrentUser().getEmail();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, nombre + "\n" + correo);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button2:
                Intent intent = new Intent(this, Main22Activity.class);
                intent.putExtra("GESTOR", gestor);
                // intent.putExtra("GESTORM", gestionMedicos);
                startActivity(intent);
                break;
            case R.id.button:
                Intent intent1 = new Intent(this, CrearPaciente.class);
                intent1.putExtra("GESTOR2", gestor);
                //  intent1.putExtra("GESTORM", gestionMedicos);
                startActivity(intent1);
                break;
            case R.id.profilePicture:
                dispatchTakePictureIntent();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            GraphicThreadController.actualizaImagen(imagen, imageBitmap);
//            imagen.setImageBitmap(imageBitmap);
        }
    }

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

    private void signout() {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), MyLoginActivity.class));
        finish();
    }

    private boolean checkUser() {
        if (mAuth != null && mAuth.getCurrentUser() != null)
            return true;
        return false;
    }

        private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
//    private void dispatchTakePictureIntent() throws IOException {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//
//            File photoFile = null;
//
//            try {
//                photoFile = createImageFile();
//            } catch (IOException e) {
//                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.beruto.teststring",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//
//            }
//        }
//    }

//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
//        return image;
//    }

}


