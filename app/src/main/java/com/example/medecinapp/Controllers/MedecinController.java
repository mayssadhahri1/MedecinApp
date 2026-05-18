package com.example.medecinapp.Controllers;

import android.content.Context;
import com.example.medecinapp.DBHelper;

public class MedecinController {

    private DBHelper dbHelper;

    public MedecinController(Context context) {
        dbHelper = new DBHelper(context);
        // Optionnel : créer un médecin test au démarrage
        dbHelper.insertTestMedecin();
    }

    // Vérifie si un médecin existe avec email et mot de passe
    public boolean checkMedecin(String email, String motDePasse) {
        // On appelle directement la méthode de DBHelper qui vérifie l'email et le mot de passe en clair
        return dbHelper.checkMedecin(email, motDePasse);
    }


    // Méthode pour enregistrer un médecin

}
