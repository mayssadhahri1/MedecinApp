package com.example.medecinapp.models;

public class Ordonnance {
    private int id;
    private int patientId;
    private int medecinId;
    private String date;
    private String medicaments;
    private String instructions;

    public Ordonnance(int id, int patientId, int medecinId, String date, String medicaments, String instructions) {
        this.id = id;
        this.patientId = patientId;
        this.medecinId = medecinId;
        this.date = date;
        this.medicaments = medicaments;
        this.instructions = instructions;
    }

    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public int getMedecinId() { return medecinId; }
    public String getDate() { return date; }
    public String getMedicaments() { return medicaments; }
    public String getInstructions() { return instructions; }
}
