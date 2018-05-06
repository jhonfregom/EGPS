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

public class Main2Activity extends AppCompatActivity{

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

        mAuth = FirebaseAuth.getInstance();
        //inicializamos el objeto firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance();

        //Referenciamos los views
        TextEmail = (EditText) findViewById(R.id.email);
        TextPassword = (EditText) findViewById(R.id.password);
        TextUserName = (EditText) findViewById(R.id.username);
        TextName = (EditText) findViewById(R.id.name);
        TextLastName = (EditText) findViewById(R.id.lastname);
        TextAge = (EditText) findViewById(R.id.age);
        TextPassWord2 = (EditText) findViewById(R.id.password2);
        btnRegistrar = (Button) findViewById(R.id.registrar);


        mProgress = new ProgressDialog(this);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
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

    private void startRegister() {
        final String name = TextUserName.getText().toString().trim();
        final String email = TextEmail.getText().toString().trim();
        final String password = TextPassword.getText().toString().trim();
        final String name1 = TextName.getText().toString().trim();
        final String lastname = TextLastName.getText().toString().trim();
        final String age = TextAge.getText().toString().trim();
        final String password2 = TextPassWord2.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            mProgress.setMessage("Registering, please wait...");
            mProgress.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgress.dismiss();
                            if (task.isSuccessful()) {
                                mAuth.signInWithEmailAndPassword(email, password);
                                //Toast.makeText(ActivityRegister.this, user_id, Toast.LENGTH_SHORT).show();

                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
                                DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                                currentUserDB.child("name").setValue(name);
                                currentUserDB.child("name1").setValue(name1);
                                currentUserDB.child("lastname").setValue(lastname);
                                currentUserDB.child("age").setValue(age);
                                currentUserDB.child("password2").setValue(password2);
                                            } else
                                Toast.makeText(Main2Activity.this, "error registering user", Toast.LENGTH_SHORT).show();

                        }
                    });
        }

    }


}
