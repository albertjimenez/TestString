package com.example.beruto.teststring;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class MyLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {


    private GoogleSignInOptions gso;
    private GoogleApiClient googleApiClient;
    //private final String tokenGoogle = "705749104307-tu8gp08ebooc9295oerlmendjvag6pag.apps.googleusercontent.com";
    public static final int RC_SIGN_IN = 9001;
    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_login);

        //FirebaseAuth


        //Creacion de elementos de la vista
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        //signInButton.setSize(SignInButton.SIZE_STANDARD);
//        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);

        //modelo
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().requestIdToken("821666023381-chac9gkoa69fgf4bv5fuf22gkvu9ujbe.apps.googleusercontent.com").build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent unIntent = new Intent(MyLoginActivity.this, MainActivity.class);
                    unIntent.putExtra("NOMBRE", user.getDisplayName());

                    if(user.getPhotoUrl()==null)
                        unIntent.putExtra("FOTO", "https://www.dirtfan.com/include/graphics/profile_pics/default/default.jpg");
                    else
                        unIntent.putExtra("FOTO", user.getPhotoUrl().toString());

                    startActivity(unIntent);
                }

            }
        };

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null)
            mAuth.removeAuthStateListener(authStateListener);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("YE", "onConnectionFailed:" + connectionResult);


    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("INICIO", "firebaseAuthWithGoogle:" + acct.getId());
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(MyLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            startActivity(new Intent(MyLoginActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.sign_in_button)
            signIn();


    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
//        startActivity(new Intent(this, MainActivity.class));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == MyLoginActivity.RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.d("INICIO", "todo ha ido bien");
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                Toast.makeText(MyLoginActivity.this, "Error de inicio", Toast.LENGTH_SHORT).show();
            }
        }
    }
//    private void handleSignInResult(GoogleSignInResult result) {
//        Log.d("HOLA", "handleSignInResult:" + result.isSuccess());
//
//        if (result.isSuccess()) {
//            // Signed in successfully, show authenticated UI.
//            GoogleSignInAccount acct = result.getSignInAccount();
//
////            Intent intent = new Intent(this, MainActivity.class);
////            intent.putExtra("NOMBRE", acct.getDisplayName());
////            startActivity(intent);
////            updateUI(true);
//        } else {
//            Log.d("HOLA","Error de sesion?");
//            // Signed out, show unauthenticated UI.
////            updateUI(false);
//
//        }
//    }
}
