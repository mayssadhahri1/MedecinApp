package com.example.medecinapp.activities;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.medecinapp.DBHelper;
import com.example.medecinapp.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatsActivity extends AppCompatActivity {

    private TextView tvTotalPatients, tvTotalRendezVous, tvTotalOrdonnances;
    private PieChart pieChart;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        // Initialisation des vues
        tvTotalPatients = findViewById(R.id.tvTotalPatients);
        tvTotalRendezVous = findViewById(R.id.tvTotalRendezVous);
        tvTotalOrdonnances = findViewById(R.id.tvTotalOrdonnances);
        pieChart = findViewById(R.id.pieChartStats);

        // Initialisation DBHelper
        dbHelper = new DBHelper(this);

        // Charger les statistiques
        loadStats();
    }



    // Méthode pour animer un TextView de 0 à la valeur finale
    private void animateCounter(final TextView textView, int finalValue) {
        ValueAnimator animator = ValueAnimator.ofInt(0, finalValue);
        animator.setDuration(1000); // Durée 1 seconde
        animator.addUpdateListener(animation -> {
            int animatedValue = (int) animation.getAnimatedValue();
            textView.setText(String.valueOf(animatedValue));
        });
        animator.start();
    }


    private void loadStats() {
        int totalPatients = dbHelper.getTotalPatients();
        int totalRendezVous = dbHelper.getTotalRendezVous();
        int totalOrdonnances = dbHelper.getTotalOrdonnances();

        // Animations des compteurs
        animateCounter(tvTotalPatients, totalPatients);
        animateCounter(tvTotalRendezVous, totalRendezVous);
        animateCounter(tvTotalOrdonnances, totalOrdonnances);

        // PieChart
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(totalPatients, "Patients"));
        entries.add(new PieEntry(totalRendezVous, "Rendez-vous"));
        entries.add(new PieEntry(totalOrdonnances, "Ordonnances"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextSize(16f);
        dataSet.setValueTextColor(getResources().getColor(android.R.color.black));

        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setUsePercentValues(true);
        pieChart.invalidate();
    }


    private void setupPieChart(int patients, int rendezVous, int ordonnances) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(patients, "Patients"));
        entries.add(new PieEntry(rendezVous, "Rendez-vous"));
        entries.add(new PieEntry(ordonnances, "Ordonnances"));

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{Color.parseColor("#FF7043"), Color.parseColor("#42A5F5"), Color.parseColor("#66BB6A")});
        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.WHITE);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setCenterText("Statistiques");
        pieChart.setCenterTextSize(16f);
        pieChart.getDescription().setEnabled(false);

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
        legend.setTextSize(12f);

        pieChart.invalidate(); // rafraîchir le graphique
    }
}
