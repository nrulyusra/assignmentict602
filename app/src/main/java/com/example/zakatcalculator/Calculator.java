package com.example.zakatcalculator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Calculator extends AppCompatActivity {
    EditText weightEditText, currentGoldValueEditText;

    Toolbar mytoolbar;
    TextView totalValueTextView, zakatPayableTextView, totalZakatTextView;
    RadioGroup radioGroupType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        mytoolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        showInstructionDialog();

        weightEditText = findViewById(R.id.weightEditText);
        currentGoldValueEditText = findViewById(R.id.currentGoldValueEditText);
        totalValueTextView = findViewById(R.id.totalValueTextView);
        zakatPayableTextView = findViewById(R.id.zakatPayableTextView);
        totalZakatTextView = findViewById(R.id.totalZakatTextView);
        radioGroupType = findViewById(R.id.radioGroupType);

        Button calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateZakat();
            }
        });
    }

    private void showInstructionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Instructions to use Zakat Calculator");
        View view = LayoutInflater.from(this).inflate(R.layout.dialog, null);
        builder.setView(view);

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetCalculator();
            }
        });

        builder.setPositiveButton("Got It", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        return true;
    }
    private void resetCalculator() {
        // Clear input fields
        weightEditText.setText("");
        currentGoldValueEditText.setText("");

        // Clear result TextViews
        totalValueTextView.setText("");
        zakatPayableTextView.setText("");
        totalZakatTextView.setText("");

        // Clear radio button selection
        radioGroupType.clearCheck();
    }


    private void calculateZakat() {
        double weight = Double.parseDouble(weightEditText.getText().toString());
        double currentGoldValue = Double.parseDouble(currentGoldValueEditText.getText().toString());

        String type = getSelectedType();

        double x = (type.equals("keep")) ? 85 : 200;
        double goldWeightMinusX = Math.max(weight - x, 0);
        double totalValue = weight * currentGoldValue;
        double zakatPayable = goldWeightMinusX * currentGoldValue * 0.025;

        totalValueTextView.setText("Total Value: RM" + String.format("%.2f", totalValue));
        zakatPayableTextView.setText("Zakat Payable: RM" + String.format("%.2f", zakatPayable));
        totalZakatTextView.setText("Total Zakat: RM" + String.format("%.2f", zakatPayable));
    }

    private String getSelectedType() {
        int selectedRadioButtonId = radioGroupType.getCheckedRadioButtonId();

        if (selectedRadioButtonId == R.id.radioButtonKeep) {
            return "keep";
        } else if (selectedRadioButtonId == R.id.radioButtonWear) {
            return "wear";
        } else {
            // Handle the case when no radio button is selected
            return "";
        }
    }
}