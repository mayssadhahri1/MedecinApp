package com.example.medecinapp.models;

public class Patient {
    private int id;
    private String nom;
    private String prenom;
    private String dateNaissance;
    private String telephone;
    private String email;
    private String adresse;

    public Patient(int id, String nom, String prenom, String dateNaissance, String telephone, String email, String adresse){
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
        this.email = email;
        this.adresse = adresse;
    }

    public int getId() { return id; }
    public String getNom() { return nom; }
    public String getPrenom() { return prenom; }
    public String getDateNaissance() { return dateNaissance; }
    public String getTelephone() { return telephone; }
    public String getEmail() { return email; }
    public String getAdresse() { return adresse; }
}
