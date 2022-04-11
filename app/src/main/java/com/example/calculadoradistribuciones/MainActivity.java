package com.example.calculadoradistribuciones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerOperadores;
    public Operadores operadorActual;
    private TextView operadorLabel;
    private View layoutResultados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bCalcular = findViewById(R.id.bCalcular);

        //Selector de operadores
        spinnerOperadores = findViewById(R.id.spinner);
        String[] operadores = getResources().getStringArray(R.array.operadores);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, operadores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOperadores.setAdapter(adapter);

        operadorLabel = findViewById(R.id.labelOperador);
        spinnerOperadores.setOnItemSelectedListener(this);
        layoutResultados = findViewById(R.id.tableLayoutResultados);
        layoutResultados.setVisibility(View.INVISIBLE);

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

        double result=0;

        //TODO: no funcionan bien las operaciones
        switch (operadorActual){
            case MENORIGUAL:
                result = calculoBinomialLTEQ(n, p, x);
                break;
            case MENOR:
                result = calculoBinomialLTEQ(n-1, p, x);
                break;
            case MAYOR:
                result = 1 - calculoBinomialLTEQ(n, p, x);
                break;
            case MAYORIGUAL:
                result = 1 - calculoBinomialLTEQ(n-1, p, x);
                break;
            case DISTINTO:
                result = calculoBinomialLTEQ(n-1, p, x) + 1 - calculoBinomialEQ(n, p, x);
                break;
            default:
                result = calculoBinomialEQ(n, p, x);
                break;
        }


        String stringResult = result+"";

        TextView resultLabel = findViewById(R.id.displayResultado);
        resultLabel.setText(stringResult);

        //Calculo la media
        double media = n*p;
        String stringMedia = media+"";
        TextView mediaLabel = findViewById(R.id.displayMedia);
        mediaLabel.setText(stringMedia);

        //Calculo la varianza
        double varianza = n*p*(1-p);
        String stringVarianza = varianza+"";
        TextView varianzaLabel = findViewById(R.id.displayVarianza);
        varianzaLabel.setText(stringVarianza);

        layoutResultados.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(),"¡Calculado!",Toast.LENGTH_SHORT).show();

    }

    public static double calculoBinomialEQ(int n, double p, int x){
        long nCombinatorio = choose(n, x);
        double result = nCombinatorio * Math.pow(p, x) * Math.pow(1-p, n-x);
        return result;
    }// calcula P(X=x) en una Binomial B(n,p)


    public static double calculoBinomialLTEQ(int n, double p, int x){
        double result=0;
        for(int i=n; i>=0; i--){
            result += calculoBinomialEQ(i, p, x);
        }
        return result;
    }// calcula P(X<=x) en una Binomial B(n,p)


    public static long choose(long total, long choose){
        if(total < choose)
            return 0;
        if(choose == 0 || choose == total)
            return 1;
        return choose(total-1,choose-1)+choose(total-1,choose);
    }//calculo recursivo de un numero combinatorio


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId() == R.id.spinner){
            String valueFromSpinner = adapterView.getItemAtPosition(i).toString();

            //Compruebo el valor seleccionado y pongo la variable global a su valor correspondiente
            switch (valueFromSpinner){
                case "menor/igual":
                    operadorActual = Operadores.MENORIGUAL;
                    break;
                case "menor":
                    operadorActual = Operadores.MENOR;
                    break;
                case "mayor/igual":
                    operadorActual = Operadores.MAYORIGUAL;
                    break;
                case "mayor":
                    operadorActual = Operadores.MAYOR;
                    break;
                case "distinto":
                    operadorActual = Operadores.DISTINTO;
                    break;
                default:
                    operadorActual = Operadores.IGUAL;
                    break;
            }

            String operador = operadorActual.getSimbolo();
            operadorLabel.setText(operador);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}

