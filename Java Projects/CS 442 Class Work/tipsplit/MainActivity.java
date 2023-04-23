package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    // Inputs
    private TextView bill;
    private TextView people;

    // Outputs
    private TextView tip;
    private TextView tTip;
    private TextView tipSplit;

    // Tip percentage calculator
    Double tipPercentage = 0.0;
    Double billDbl = 0.0;
    Double personDbl = 0.0;
    Double tipAmount = 0.0;
    Double tipTotal = 0.0;
    Double splitTip = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inputs
        bill = findViewById(R.id.totalWithTax);
        people = findViewById(R.id.peopleInput);

        // Outputs
        tip = findViewById(R.id.textViewTP);
        tTip = findViewById(R.id.textViewTT);
        tipSplit = findViewById(R.id.textViewTS);
    }

    public void radioSelected(View v){
        // Percentage selector
        if (bill.getText().toString().isEmpty()){
            RadioGroup rg = findViewById(R.id.radioTip);
            rg.clearCheck();
            tipPercentage = 0.0;
        } else {
            if (v.getId() == R.id.radio12) {
                tipPercentage = 0.12;
            } else if (v.getId() == R.id.radio15) {
                tipPercentage = 0.15;
            } else if (v.getId() == R.id.radio18) {
                tipPercentage = 0.18;
            } else if (v.getId() == R.id.radio20) {
                tipPercentage = 0.2;
            } else {
                tipPercentage = 0.0;
            }
        }
    }

    public void calculateTip(View v){
        final DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.UP);

        if (bill.getText().toString().isEmpty()) {
            return;
        }

        if (people.getText().toString().isEmpty()) {
            return;
        }

        if (Double.parseDouble(people.getText().toString()) != 0){
        } else {
            return;
        }

        if (tipPercentage != 0) {
        } else {
            return;
        }

        billDbl = Double.parseDouble(bill.getText().toString());
        personDbl = Double.parseDouble(people.getText().toString());

        // Calculation for the tip
        tipAmount = billDbl * tipPercentage;
        tip.setText("$" + String.valueOf(df.format(tipAmount)));

        // Calculation for the total with tip - POSSIBLY FORMAT IT SO THE TIP IS USES THE FORMAT
        tipTotal = billDbl + tipAmount;
        tTip.setText(String.valueOf("$" + df.format(tipTotal)));

        // Calculation for the total with tip split between everyone
        splitTip = tipTotal / personDbl;
        tipSplit.setText(String.valueOf("$" + df.format(splitTip)));
    }

    public void clearAllData(View v){
        // Clear Inputs
        bill.setText("");
        people.setText("");

        // Clear radio group
        RadioGroup rg = findViewById(R.id.radioTip);
        rg.clearCheck();

        // Reset radio tip number
        tipPercentage = 0.0;

        // Clear Outputs
        tip.setText("");
        tTip.setText("");
        tipSplit.setText("");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save text boxes
        outState.putString("TIP", tip.getText().toString());
        outState.putString("TTIP", tTip.getText().toString());
        outState.putString("TIPSPLIT", tipSplit.getText().toString());

        // Save variables
        outState.putDouble("BILLDB", billDbl);
        outState.putDouble("PERSDB", personDbl);
        outState.putDouble("TIPAMT", tipAmount);
        outState.putDouble("TIPTOT", tipTotal);
        outState.putDouble("SPLITIP", splitTip);
        outState.putDouble("TIPPERC", tipPercentage);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);

        // Set text boxes
        tip.setText(savedInstanceState.getString("TIP"));
        tTip.setText(savedInstanceState.getString("TTIP"));
        tipSplit.setText(savedInstanceState.getString("TIPSPLIT"));

        // Set variables
        billDbl = savedInstanceState.getDouble("BILLDBL");
        personDbl = savedInstanceState.getDouble("PERSDB");
        tipAmount = savedInstanceState.getDouble("TIPAMT");
        tipTotal = savedInstanceState.getDouble("TIPTOT");
        splitTip = savedInstanceState.getDouble("SPLITTIP");
        tipPercentage = savedInstanceState.getDouble("TIPPERC");
    }
}
