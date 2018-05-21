package com.example.jhonfregom.egps;

public class Usuario {
    private String username;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String password2;
    private String age;
    private String gender;
    private String latitud;
    private String longitud;

    public Usuario(String emailU, String passwordU, String password2U, String usernameU, String nameU, String lastnameU, String ageU, String genderuU) {
    }

    public Usuario(String username, String name, String lastname, String email, String password, String password2, String age, String gender, String latitud, String longitud) {
        this.username = username;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.age = age;
        this.gender = gender;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
