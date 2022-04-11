package com.example.calculadoradistribuciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bCalcular = findViewById(R.id.bCalcular);

    }

    public void calcularBin(View view){
        EditText nEditText = findViewById(R.id.campoNRepeticiones);
        String nString = nEditText.getText().toString();
        int n = Integer.parseInt(nString);

        EditText pEditText = findViewById(R.id.campoProbabilidad);
        String pString = pEditText.getText().toString();
        double p = Double.parseDouble(pString);

        EditText xEditText = findViewById(R.id.campoNumeroOperacion);
        String xString = xEditText.getText().toString();
        int x = Integer.parseInt(xString);

        double result = calculoBinomialEQ(n, p, x);
        String stringResult = result+"";

        TextView resultLabel = findViewById(R.id.displayResultado);
        resultLabel.setText(stringResult);
    }

    public static double calculoBinomialEQ(int n, double p, int x){
        long nCombinatorio = choose(n, x);
        double result = nCombinatorio * Math.pow(p, x) * Math.pow(1-p, n-x);
        return result;
    }// calcula P(X=x) en una Binomial B(n,p)

    public static long choose(long total, long choose){
        if(total < choose)
            return 0;
        if(choose == 0 || choose == total)
            return 1;
        return choose(total-1,choose-1)+choose(total-1,choose);
    }//calculo recursivo de un numero combinatorio







}

