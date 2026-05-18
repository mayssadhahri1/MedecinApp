package com.example.medecinapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medecinapp.DBHelper;
import com.example.medecinapp.R;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    EditText nomInput, emailInput, passwordInput, confirmPasswordInput, specialiteInput, telephoneInput;
    Button registerBtn;
    TextView loginRedirect;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Log.d(TAG, "onCreate: démarrage de RegisterActivity");

        // Récupération des vues
        nomInput = findViewById(R.id.nomInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        specialiteInput = findViewById(R.id.specialiteInput);
        telephoneInput = findViewById(R.id.telephoneInput);

        loginRedirect = findViewById(R.id.loginRedirect);

        dbHelper = new DBHelper(this);

        // Événements boutons
        registerBtn.setOnClickListener(v -> registerMedecin());
        loginRedirect.setOnClickListener(v -> {
            Log.d(TAG, "Redirection vers LoginActivity");
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        });
    }

    private void registerMedecin() {
        String nom = nomInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();
        String specialite = specialiteInput.getText().toString().trim();
        String telephone = telephoneInput.getText().toString().trim();

        Log.d(TAG, "registerMedecin: tentative d'inscription pour l'email = " + email);

        // Vérifications simples
        if(nom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
            Log.w(TAG, "registerMedecin: champs manquants");
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password.equals(confirmPassword)){
            Log.w(TAG, "registerMedecin: les mots de passe ne correspondent pas");
            Toast.makeText(this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            return;
        }

        // Appel à DBHelper pour insérer le médecin
        long result = dbHelper.insertMedecinManuel(nom, email, password, specialite, telephone);

        if(result != -1){
            Log.d(TAG, "registerMedecin: médecin inséré avec succès, ID = " + result);
            Toast.makeText(this, "Médecin inséré avec succès ! ID = " + result, Toast.LENGTH_LONG).show();
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            finish();
        } else {
            Log.e(TAG, "registerMedecin: erreur lors de l'insertion pour l'email = " + email);
            Toast.makeText(this, "Erreur : cet email existe déjà ou insertion échouée", Toast.LENGTH_LONG).show();
        }
    }
}
