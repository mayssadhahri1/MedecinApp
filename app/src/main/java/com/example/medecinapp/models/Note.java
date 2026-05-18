package com.example.medecinapp.models;

public class Note {
    private int id;
    private int patientId;
    private String texte;

    public Note(int patientId, String texte) {
        this.patientId = patientId;
        this.texte = texte;
    }

    public Note(int id, int patientId, String texte) {
        this.id = id;
        this.patientId = patientId;
        this.texte = texte;
    }

    public int getId() {
        return id;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getTexte() {
        return texte;
    }

    public void setTexte(String texte) {
        this.texte = texte;
    }
}
