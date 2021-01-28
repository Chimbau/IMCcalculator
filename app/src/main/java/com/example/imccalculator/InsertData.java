package com.example.imccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class InsertData extends AppCompatActivity {

    private Button insertButton;
    private EditText mass;
    private EditText height;
    private Spinner sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_data);
        insertButton = findViewById(R.id.insertButton);
        mass = findViewById(R.id.mass);
        height = findViewById(R.id.height);
        sex = findViewById(R.id.sex);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_list, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(adapter);

        insertButton.setOnClickListener(v -> {
            if(mass.getText().toString().isEmpty() || height.getText().toString().isEmpty()){
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            }
            else{
                Double imc = calculateIMC();
                if(Double.parseDouble(mass.getText().toString()) <= 0 || Double.parseDouble(height.getText().toString()) <= 0){
                    Toast.makeText(this, "Preencha os campos com valores válidos", Toast.LENGTH_SHORT).show();
                }
                else{
                    Bundle extras = new Bundle();
                    extras.putDouble("IMC", imc);
                    extras.putString("SEX", sex.getSelectedItem().toString());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }
            }
        });
    }

    private double calculateIMC(){
        double imc = 0.0;
        double m = Double.parseDouble(mass.getText().toString());
        double h = Double.parseDouble(height.getText().toString());
        imc = m / Math.pow(h, 2);
        return imc;
    }

}