package com.cyberx.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resultTv, solutionTv;
    public MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    public MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEqual;
    public MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    public MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        assignId(buttonC, R.id.clear_btn);
        assignId(buttonBrackOpen, R.id.openBracket_btn);
        assignId(buttonBrackClose, R.id.close_btn);
        assignId(buttonDivide, R.id.divide_btn);
        assignId(buttonMultiply, R.id.multiply_btn);
        assignId(buttonPlus, R.id.plus_btn);
        assignId(buttonMinus, R.id.minus_btn);
        assignId(buttonEqual, R.id.equal_btn);
        assignId(button0, R.id.zero_btn);
        assignId(button1, R.id.one_btn);
        assignId(button2, R.id.two_btn);
        assignId(button3, R.id.three_btn);
        assignId(button4, R.id.four_btn);
        assignId(button5, R.id.five_btn);
        assignId(button6, R.id.six_btn);
        assignId(button7, R.id.seven_btn);
        assignId(button8, R.id.eight_btn);
        assignId(button9, R.id.nine_btn);
        assignId(buttonAC, R.id.ac_btn);
        assignId(buttonDot, R.id.dot_btn);
    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();

        if (buttonText.equals("AC")) {
            solutionTv.setText("");
            resultTv.setText("0");
            return;
        }
        if (buttonText.equals("=")) {
            solutionTv.setText(resultTv.getText());
            return;
        }
        try {
            if (buttonText.equals("C")) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
            } else {
                dataToCalculate = dataToCalculate + buttonText;
            }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, "", Toast.LENGTH_SHORT).show();
        }
        solutionTv.setText(dataToCalculate);
        String finalResult = getResult(dataToCalculate);
        if (!finalResult.equals("Err")) {
            resultTv.setText(finalResult);

        }
    }
    String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        }
    }
}