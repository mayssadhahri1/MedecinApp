package com.example.medecinapp.Controllers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.medecinapp.DBHelper;
import com.example.medecinapp.models.RendezVous;

import java.util.ArrayList;

public class RendezVousController {

    private DBHelper dbHelper;

    public RendezVousController(Context context) {
        dbHelper = new DBHelper(context);
    }

    // ================== CRUD ==================
    public boolean addRendezVous(RendezVous rdv) {
        long result = dbHelper.insertRendezVous(rdv);
        return result != -1;
    }

    public boolean updateRendezVous(RendezVous rdv) {
        return dbHelper.updateRendezVous(rdv);
    }

    public boolean deleteRendezVous(int id) {
        return dbHelper.deleteRendezVous(id);
    }

    // ================== LISTE PAR DATE ==================
    public ArrayList<RendezVous> getRendezVousByDate(String date) {
        ArrayList<RendezVous> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("rendezvous", null, "date LIKE ?", new String[]{date + "%"}, null, null, "date ASC");

        if (cursor != null && cursor.moveToFirst()) {
            do {
                int patientId = cursor.getInt(cursor.getColumnIndexOrThrow("patient_id"));
                String patientNom = dbHelper.getPatientNameById(patientId);

                RendezVous rv = new RendezVous(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        patientId,
                        cursor.getInt(cursor.getColumnIndexOrThrow("medecin_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("statut")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("rappel"))
                );
                rv.setPatientName(patientNom); // ajoute le nom récupéré
                list.add(rv);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    // ================== RECHERCHE ==================

    // ================== VERIFICATIONS ==================
    public boolean patientHasRendezVous(int patientId, String date) {
        return dbHelper.patientHasRendezVous(patientId, date);
    }

    public int getRendezVousCountByDate(String date) {
        return dbHelper.getRendezVousCountByDate(date);
    }

    public boolean isTimeSlotTaken(String dateTime) {
        return dbHelper.isTimeSlotTaken(dateTime);
    }

    // ================== PATIENTS DISPONIBLES ==================
    public Cursor getAvailablePatients(String date) {
        return dbHelper.getAvailablePatients(date);
    }


}
