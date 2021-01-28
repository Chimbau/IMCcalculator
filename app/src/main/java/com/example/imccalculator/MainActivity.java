package com.example.imccalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.CalendarContract;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private Button insertActivity;
    private Button createNote;
    private Button aboutButton;
    private TextView imcValue;
    private TextView imcPercent;
    private TextView aboveBelowText;
    private double maleImcAvarage = 25.9;
    private double femaleImcAvarage = 26;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertActivity = findViewById(R.id.insertActivity);
        imcValue = findViewById(R.id.imcValue);
        imcPercent = findViewById(R.id.imcPercent);
        aboveBelowText = findViewById(R.id.percentText);
        createNote = findViewById(R.id.createNote);
        aboutButton = findViewById(R.id.about);

        Intent insertIntent = getIntent();
        Bundle extras = insertIntent.getExtras();
        Double imc = 0.0;
        String sex = "Masculino";
        DecimalFormat df = new DecimalFormat("###.##");
        if(extras != null){
            imc = extras.getDouble("IMC", 0.0);
            sex = extras.getString("SEX"); //Feminino ou Masculino
        }

        if (imc == 0.0) {
            imcValue.setText("-");
        } else {
            imcValue.setText(df.format(imc));
            highLightRow(imc);
            Double percentage;
            percentage = calculatePercentage(imc, sex);

            String aboveBelowString = "";
            if(percentage <= 0){
                aboveBelowString = getString(R.string.aboveText) +  " " + sex ;
            }
            else{
                aboveBelowString = getString(R.string.belowText) + " " + sex;
            }
            aboveBelowText.setText(aboveBelowString);
            imcPercent.setText(df.format(Math.abs(percentage)) + "%");
        }

        insertActivity.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), InsertData.class);
            startActivity(intent);
        });

        double finalImc = imc;
        createNote.setOnClickListener(v -> {

            if(finalImc == 0.0){
                Toast.makeText(this, R.string.aboutToast, Toast.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.ALL_DAY, true)
                        .putExtra(CalendarContract.Events.TITLE, "IMC atual : " + df.format(finalImc))
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, 0)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, 0);
                startActivity(intent);
            }
        });

        aboutButton.setOnClickListener(v -> {
            Uri webpage = Uri.parse(getResources().getString(R.string.about_url));
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(intent);
        });
    }

    private Double calculatePercentage(Double imc, String sex) {
        Double percentage;
        if(sex.equals("Masculino")){
            percentage = (100 - ((100 * imc) / maleImcAvarage));
        }
        else{
            percentage = (100 - ((100 * imc) / femaleImcAvarage));
        }
        return percentage;
    }


    //Change row color according to IMC
    public void highLightRow(Double imc) {
        if (imc < 18.5) {
            findViewById(R.id.magreza).setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
            findViewById(R.id.intervaloMagreza).setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
        }
        if (imc >= 18.5 && imc < 24.9) {
            findViewById(R.id.normal).setBackgroundColor(ContextCompat.getColor(this, R.color.light_green));
            findViewById(R.id.intervaloNormal).setBackgroundColor(ContextCompat.getColor(this, R.color.light_green));
        }
        if (imc >= 25.0 && imc < 29.9) {
            findViewById(R.id.sobrepeso).setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
            findViewById(R.id.intervaloSobrepeso).setBackgroundColor(ContextCompat.getColor(this, R.color.yellow));
        }
        if (imc >= 30.0 && imc < 39.9) {
            findViewById(R.id.obesidade).setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
            findViewById(R.id.intervaloObesidade).setBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        }
        if (imc >= 40.0) {
            findViewById(R.id.obesidadeGrave).setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            findViewById(R.id.intervaloObesidadeGrave).setBackgroundColor(ContextCompat.getColor(this, R.color.red));
        }

    }



}