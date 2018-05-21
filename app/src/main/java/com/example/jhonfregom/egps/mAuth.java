package com.example.jhonfregom.egps;

import com.google.firebase.auth.FirebaseAuth;

public class mAuth {
    private String email;
    private String password;
    private FirebaseAuth mAuth;

    public mAuth() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public void setmAuth(FirebaseAuth mAuth) {
        this.mAuth = mAuth;
    }
}
