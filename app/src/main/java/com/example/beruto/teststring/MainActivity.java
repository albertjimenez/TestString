package com.example.beruto.teststring;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicInteger;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView etiquetaTexto;
    private Button boton;
    private Button botonTokistico;
    private EditText cajaTexto;
    private AtomicInteger contador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //inicializaciones de mis atributos
        //los rescato de content_main.xml para tener acceso
        contador = new AtomicInteger(0);
        etiquetaTexto = (TextView) findViewById(R.id.textView);
        boton = (Button) findViewById(R.id.button2);

        // etiquetaTexto.setText("jeje me has tocado");

        //Accion del boton flotante
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        botonTokistico = (Button) findViewById(R.id.botonTokis);
        botonTokistico.setOnClickListener(this);
        boton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            case R.id.botonTokis:
                contador.incrementAndGet();
                Toast.makeText(getApplicationContext(), "Tokis repite " + contador.get()+ " asignaturas", Toast.LENGTH_SHORT).show();
                break;

            case R.id.button2:
                System.out.println("CONTADOR: "+ contador.toString());
                Intent intent = new Intent(this,Main22Activity.class);
                intent.putExtra("ASIGNATURAS",contador.toString());
                startActivity(intent);
                break;

        }
    }

}
