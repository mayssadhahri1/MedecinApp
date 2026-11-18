# 🩺 MedecinApp — Application Android de gestion de cabinet médical

> Application mobile native qui aide un médecin à gérer ses patients, ses rendez-vous et ses ordonnances, avec des statistiques visuelles.

![Java](https://img.shields.io/badge/Java-Android-orange?logo=android&logoColor=white)
![SQLite](https://img.shields.io/badge/SQLite-003B57?logo=sqlite&logoColor=white)
![MPAndroidChart](https://img.shields.io/badge/Charts-MPAndroidChart-blue)
![Material](https://img.shields.io/badge/UI-Material%20Design-757575?logo=materialdesign&logoColor=white)

## ✨ Fonctionnalités

- 🔐 **Inscription et connexion** du médecin
- 👥 **Patients** : ajout, modification, liste et recherche
- 📅 **Rendez-vous** : planification et suivi
- 💊 **Ordonnances** : création et consultation par patient
- 💬 **Messagerie**, contacts et historique des appels
- 📊 **Tableau de bord** et **statistiques** avec graphiques (MPAndroidChart)

## 🏗️ Architecture

| Couche | Contenu |
|---|---|
| `activities` | Écrans : `Login`, `Register`, `Dashboard`, `Patients`, `RendezVous`, `AddOrdonnance`, `Stats`, `Messagerie`… |
| `controllers` | Logique métier : `MedecinController`, `PatientController`, `RendezVousController`, `OrdonnanceController` |
| `models` | `Patient`, `RendezVous`, `Ordonnance`, `Note`, `CallHistory` |
| `adapters` | Adaptateurs RecyclerView pour les listes |
| `DBHelper` | Base locale **SQLite** (tables `medecin`, `patient`, `rendezvous`, `ordonnance`) |

## 🚀 Lancer le projet

1. Ouvrir le dossier dans **Android Studio**
2. Synchroniser Gradle
3. Lancer sur un émulateur ou un téléphone Android

## 🛠️ Stack

Java · Android SDK · SQLite · RecyclerView · Material Design · MPAndroidChart · Gradle (Kotlin DSL)

---

👩‍💻 **Mayssa Dhahri** — Élève-ingénieure en Génie Logiciel · [LinkedIn](https://www.linkedin.com/in/mayssa-dhahri)
