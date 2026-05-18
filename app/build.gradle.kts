plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.medecinapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.medecinapp"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    // AndroidX & Material
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.activity:activity:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // MPAndroidChart pour les statistiques
    implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

    // Tests unitaires
    testImplementation("junit:junit:4.13.2")

    // Tests instrumentés
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    androidTestImplementation("androidx.cardview:cardview:1.0.0")


    implementation("androidx.recyclerview:recyclerview:1.3.1")
}

// Résoudre les conflits Kotlin (inutile si 100% Java, mais sécuritaire)
configurations.all {
    resolutionStrategy {
        force("org.jetbrains.kotlin:kotlin-stdlib:1.8.22")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.22")
        force("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.22")
    }
}
