package com.example.jhonfregom.egps;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity {

    //defining view objects
    private EditText TextEmail;
    private EditText TextPassword;
    private EditText TextUserName;
    private EditText TextName;
    private EditText TextLastName;
    private EditText TextAge;
    private EditText gender;
    private EditText TextPassWord2;
    private Button btnRegistrar;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference databaseReference;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.email);
        TextPassword = (EditText) findViewById(R.id.password);
        TextUserName = (EditText) findViewById(R.id.username);
        TextName = (EditText) findViewById(R.id.name);
        TextLastName = (EditText) findViewById(R.id.lastname);
        TextAge = (EditText) findViewById(R.id.age);
        gender = (EditText) findViewById(R.id.gender);
        TextPassWord2 = (EditText) findViewById(R.id.password2);
        btnRegistrar = (Button) findViewById(R.id.registrar);


        mAuth = FirebaseAuth.getInstance();
        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        mProgress = new ProgressDialog(this);


        mProgress = new ProgressDialog(this);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
                validar();
            }
        });
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    Intent intent = new Intent(Main2Activity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

    }

    private void validar() {
        TextEmail.setError(null);
        TextPassword.setError(null);
        TextUserName.setError(null);
        TextName.setError(null);
        TextLastName.setError(null);
        TextAge.setError(null);
        gender.setError(null);
        TextPassWord2.setError(null);

        String emailU = TextEmail.getText().toString();
        String passwordU = TextPassword.getText().toString();
        String usernameU = TextUserName.getText().toString();
        String nameU = TextName.getText().toString();
        String lastnameU = TextLastName.getText().toString();
        String genderuU = gender.getText().toString();
        String ageU = TextAge.getText().toString();
        String password2U = TextPassWord2.getText().toString();
        if(TextUtils.isEmpty(usernameU)){
            TextUserName.setError(getString(R.string.error_campo_obligatorio));
            TextUserName.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(nameU)){
            TextName.setError(getString(R.string.error_campo_obligatorio));
            TextName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(lastnameU)){
            TextLastName.setError(getString(R.string.error_campo_obligatorio));
            TextLastName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(ageU)){
            TextAge.setError(getString(R.string.error_campo_obligatorio));
            TextAge.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(genderuU)){
            gender.setError(getString(R.string.error_campo_obligatorio));
            gender.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(emailU)){
            TextEmail.setError(getString(R.string.error_campo_obligatorio));
            TextEmail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(passwordU)) {
            TextPassword.setError(getString(R.string.error_campo_obligatorio));
            TextPassword.requestFocus();
            return;
        }
        int edad = Integer.parseInt(ageU);
        if(edad<1 || edad>99){
            TextAge.setError(getString(R.string.error_valor_entre_0_y_99));
            TextAge.requestFocus();
            return;
        }


    }


    //ON click de registro
        private void startRegister() {
                final String emailU = TextEmail.getText().toString().trim();
                final String passwordU = TextPassword.getText().toString().trim();
                final String usernameU = TextUserName.getText().toString().trim();
                final String nameU = TextName.getText().toString().trim();
                final String lastnameU = TextLastName.getText().toString().trim();
                final String genderuU = gender.getText().toString();
                final String ageU = TextAge.getText().toString().trim();
                final String password2U = TextPassWord2.getText().toString().trim();


            if(password2U.equals(TextPassword.getText().toString())) {

                if (!TextUtils.isEmpty(emailU) && !TextUtils.isEmpty(passwordU)) {
                    mProgress.setMessage("REGISTRANDO USUARIO, POR FAVOR ESPERA...");
                    mProgress.show();
                    mAuth.createUserWithEmailAndPassword(emailU, passwordU)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    mProgress.dismiss();
                                    if (task.isSuccessful()) {
                                        mAuth.signInWithEmailAndPassword(emailU, passwordU);
                                        //Toast.makeText(ActivityRegister.this, user_id, Toast.LENGTH_SHORT).show();

                                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                                        DatabaseReference databaseReference = mDatabase.child(mAuth.getCurrentUser().getUid());

                                        databaseReference.child("username").setValue(usernameU);
                                        databaseReference.child("name").setValue(nameU);
                                        databaseReference.child("lastname").setValue(lastnameU);
                                        databaseReference.child("age").setValue(ageU);
                                        databaseReference.child("gender").setValue(genderuU);
                                        //                databaseReference.child("password2").setValue(password2U);

                                        //         if (passwordU.equals(TextPassword.getText().toString()&&password2U.equals(TextPassWord2.getText().toString()))){

                                        //       }

                                        Toast.makeText(getApplicationContext(), "USUARIO REGISTRADO", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(Main2Activity.this, "CONTRASEÑAS NO COINCIDEN", Toast.LENGTH_SHORT).show();

                                }

                            });
                    mAuthListener = new FirebaseAuth.AuthStateListener() {
                        @Override
                        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                            if (firebaseAuth.getCurrentUser() != null) {
                                Intent intent = new Intent(Main2Activity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }
                    };


                }
            }else
                Toast.makeText(Main2Activity.this, "CONTRASEÑAS NO COINCIDEN", Toast.LENGTH_SHORT).show();

        }
        }