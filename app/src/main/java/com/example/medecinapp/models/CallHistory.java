package com.example.medecinapp.models;

public class CallHistory {
    private int id;
    private int patientId;
    private String type; // "appel" ou "sms"
    private String dateTime;
    private String message; // pour SMS seulement, peut être null pour appel

    public CallHistory(int id, int patientId, String type, String dateTime, String message) {
        this.id = id;
        this.patientId = patientId;
        this.type = type;
        this.dateTime = dateTime;
        this.message = message;
    }

    public int getId() { return id; }
    public int getPatientId() { return patientId; }
    public String getType() { return type; }
    public String getDateTime() { return dateTime; }
    public String getMessage() { return message; }
}
