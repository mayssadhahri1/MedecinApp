package com.example.medecinapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.medecinapp.models.Patient;
import com.example.medecinapp.models.Ordonnance;
import com.example.medecinapp.models.RendezVous;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "medecin.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table Medecin
        db.execSQL("CREATE TABLE IF NOT EXISTS medecin(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT, " +
                "email TEXT UNIQUE, " +
                "mot_de_passe TEXT, " +
                "specialite TEXT, " +
                "telephone TEXT)");

        // Table Patient
        db.execSQL("CREATE TABLE IF NOT EXISTS patient(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nom TEXT, " +
                "prenom TEXT, " +
                "date_naissance TEXT, " +
                "email TEXT, " +
                "telephone TEXT, " +
                "adresse TEXT)");

        // Table RendezVous
        db.execSQL("CREATE TABLE IF NOT EXISTS rendezvous(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "patient_id INTEGER, " +
                "medecin_id INTEGER, " +
                "date TEXT, " +
                "statut TEXT, " +
                "rappel INTEGER DEFAULT 1)");

        // Table Ordonnance
        db.execSQL("CREATE TABLE IF NOT EXISTS ordonnance(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "patient_id INTEGER, " +
                "medecin_id INTEGER, " +
                "date TEXT, " +
                "medicaments TEXT, " +
                "instructions TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ordonnance");
        db.execSQL("DROP TABLE IF EXISTS rendezvous");
        db.execSQL("DROP TABLE IF EXISTS patient");
        db.execSQL("DROP TABLE IF EXISTS medecin");
        onCreate(db);
    }

    // ================== PATIENT ==================
    public long insertPatient(Patient patient) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom", patient.getNom());
        cv.put("prenom", patient.getPrenom());
        cv.put("date_naissance", patient.getDateNaissance());
        cv.put("email", patient.getEmail());
        cv.put("telephone", patient.getTelephone());
        cv.put("adresse", patient.getAdresse());
        return db.insert("patient", null, cv);
    }

    public boolean updatePatient(Patient patient) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nom", patient.getNom());
        cv.put("prenom", patient.getPrenom());
        cv.put("date_naissance", patient.getDateNaissance());
        cv.put("email", patient.getEmail());
        cv.put("telephone", patient.getTelephone());
        cv.put("adresse", patient.getAdresse());
        return db.update("patient", cv, "id=?", new String[]{String.valueOf(patient.getId())}) > 0;
    }

    public boolean deletePatient(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("patient", "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    public Cursor getAllPatients() {
        SQLiteDatabase db = getReadableDatabase();
        return db.query("patient", null, null, null, null, null, "nom ASC");
    }

    public String getPatientNameById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        String name = null;
        Cursor cursor = db.query("patient", new String[]{"nom"}, "id=?", new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndexOrThrow("nom"));
            cursor.close();
        }
        return name;
    }

    public int getTotalPatients() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM patient", null);
        int total = 0;
        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getInt(0);
            cursor.close();
        }
        return total;
    }

    // ================== RENDEZ-VOUS ==================
    public long addRendezVous(RendezVous r) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("patient_id", r.getPatientId());
        cv.put("medecin_id", r.getMedecinId());
        cv.put("date", r.getDate());
        cv.put("statut", r.getStatut());
        cv.put("rappel", r.getRappel());
        return db.insert("rendezvous", null, cv);
    }

    public boolean updateRendezVous(RendezVous r) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("date", r.getDate());
        cv.put("statut", r.getStatut());
        cv.put("rappel", r.getRappel());
        return db.update("rendezvous", cv, "id=?", new String[]{String.valueOf(r.getId())}) > 0;
    }

    public boolean deleteRendezVous(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("rendezvous", "id=?", new String[]{String.valueOf(id)}) > 0;
    }

    public ArrayList<RendezVous> getRendezVousByDate(String date) {
        ArrayList<RendezVous> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("rendezvous", null, "date LIKE ?", new String[]{date + "%"}, null, null, "date ASC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(new RendezVous(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("patient_id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("medecin_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("statut")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("rappel"))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public int getTotalRendezVous() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM rendezvous", null);
        int total = 0;
        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getInt(0);
            cursor.close();
        }
        return total;
    }

    // ================== ORDONNANCE ==================
    public long addOrdonnance(Ordonnance o) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("patient_id", o.getPatientId());
        cv.put("medecin_id", o.getMedecinId());
        cv.put("date", o.getDate());
        cv.put("medicaments", o.getMedicaments());
        cv.put("instructions", o.getInstructions());
        return db.insert("ordonnance", null, cv);
    }

    public boolean updateOrdonnance(Ordonnance o) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("patient_id", o.getPatientId());
        cv.put("medecin_id", o.getMedecinId());
        cv.put("date", o.getDate());
        cv.put("medicaments", o.getMedicaments());
        cv.put("instructions", o.getInstructions());
        return db.update("ordonnance", cv, "id=?", new String[]{String.valueOf(o.getId())}) > 0;
    }

    public boolean deleteOrdonnance(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("ordonnance", "id=?", new String[]{String.valueOf(id)}) > 0;
    }
    public ArrayList<Ordonnance> getOrdonnancesByPatientList(int patientId) {
        ArrayList<Ordonnance> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                "ordonnance",
                null,
                "patient_id = ?",
                new String[]{String.valueOf(patientId)},
                null,
                null,
                "date DESC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Ordonnance o = new Ordonnance(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("patient_id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("medecin_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("medicaments")),
                        cursor.getString(cursor.getColumnIndexOrThrow("instructions"))
                );
                list.add(o);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }


    public ArrayList<Ordonnance> getAllOrdonnances() {
        ArrayList<Ordonnance> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("ordonnance", null, null, null, null, null, "date DESC");
        if (cursor != null && cursor.moveToFirst()) {
            do {
                list.add(new Ordonnance(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("patient_id")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("medecin_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("date")),
                        cursor.getString(cursor.getColumnIndexOrThrow("medicaments")),
                        cursor.getString(cursor.getColumnIndexOrThrow("instructions"))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return list;
    }

    public int getTotalOrdonnances() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM ordonnance", null);
        int total = 0;
        if (cursor != null && cursor.moveToFirst()) {
            total = cursor.getInt(0);
            cursor.close();
        }
        return total;
    }

    // ================== MEDECIN ==================
    public long insertMedecinManuel(String nom, String email, String password, String specialite, String telephone) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("medecin", new String[]{"id"}, "email=?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return -1; // Email existe déjà
        }
        if (cursor != null) cursor.close();

        ContentValues values = new ContentValues();
        values.put("nom", nom);
        values.put("email", email);
        values.put("mot_de_passe", password);
        values.put("specialite", specialite);
        values.put("telephone", telephone);
        return db.insert("medecin", null, values);
    }

    public boolean checkMedecin(String email, String motDePasse) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("medecin",
                new String[]{"id"},
                "email=? AND mot_de_passe=?",
                new String[]{email, motDePasse},
                null, null, null);
        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) cursor.close();
        return exists;
    }

    public boolean patientHasRendezVous(int patientId, String date) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("rendezvous", null,
                "patient_id=? AND date LIKE ?",
                new String[]{String.valueOf(patientId), date + "%"},
                null, null, null);
        boolean exists = cursor != null && cursor.getCount() > 0;
        if (cursor != null) cursor.close();
        return exists;
    }

    public boolean isTimeSlotTaken(String dateTime) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("rendezvous", null, "date=?", new String[]{dateTime}, null, null, null);
        boolean taken = cursor != null && cursor.getCount() > 0;
        if (cursor != null) cursor.close();
        return taken;
    }

    public Cursor getAvailablePatients(String date) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM patient WHERE id NOT IN " +
                "(SELECT patient_id FROM rendezvous WHERE date LIKE ?)", new String[]{date + "%"});
    }

    public long insertTestMedecin() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query("medecin", new String[]{"id"}, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return -1; // Table déjà remplie
        }
        if (cursor != null) cursor.close();

        ContentValues cv = new ContentValues();
        cv.put("nom", "Dr Test");
        cv.put("email", "test@medecin.com");
        cv.put("mot_de_passe", "1234");
        cv.put("specialite", "Cardiologue");
        cv.put("telephone", "12345678");
        return db.insert("medecin", null, cv);
    }

    // Alias pour compatibilité
    public long insertRendezVous(RendezVous r) {
        return addRendezVous(r);
    }

    public int getRendezVousCountByDate(String date) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("rendezvous", null, "date LIKE ?", new String[]{date + "%"}, null, null, null);
        int count = cursor != null ? cursor.getCount() : 0;
        if (cursor != null) cursor.close();
        return count;
    }
}
