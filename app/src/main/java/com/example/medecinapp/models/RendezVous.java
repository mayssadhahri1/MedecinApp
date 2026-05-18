package com.example.medecinapp.models;

public class RendezVous {
    private int id;
    private int patientId;
    private int medecinId;
    private String date;
    private String statut;
    private int rappel;
    private String patientName; // nom du patient pour affichage

    // Constructeur vide
    public RendezVous() {}

    // Constructeur complet sans patientName
    public RendezVous(int id, int patientId, int medecinId, String date, String statut, int rappel){
        this.id = id;
        this.patientId = patientId;
        this.medecinId = medecinId;
        this.date = date;
        this.statut = statut;
        this.rappel = rappel;
    }

    // Constructeur complet avec patientName
    public RendezVous(int id, int patientId, int medecinId, String date, String statut, int rappel, String patientName) {
        this.id = id;
        this.patientId = patientId;
        this.medecinId = medecinId;
        this.date = date;
        this.statut = statut;
        this.rappel = rappel;
        this.patientName = patientName;
    }

    // GETTER & SETTER
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    public int getMedecinId() { return medecinId; }
    public void setMedecinId(int medecinId) { this.medecinId = medecinId; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public int getRappel() { return rappel; }
    public void setRappel(int rappel) { this.rappel = rappel; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    @Override
    public String toString() {
        return "RendezVous{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", medecinId=" + medecinId +
                ", date='" + date + '\'' +
                ", statut='" + statut + '\'' +
                ", rappel=" + rappel +
                ", patientName='" + patientName + '\'' +
                '}';
    }
}
