package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.calculator.R;

import org.mariuszgromada.math.mxparser.*;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    private String textBox = "";
    private int textFlag = 0;
    private int maxSize = 11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extractInfo = getIntent().getExtras();
        if (extractInfo != null) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getIntent().removeExtra("isAdv");
        }

        setContentView(R.layout.activity_main);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            maxSize = 11;
        }
        else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            maxSize = 40;
        }

        TextView calculatorDisplay = findViewById(R.id.calculatorDisplay);
        if (savedInstanceState != null){
            if (maxSize == 11) {
                String str = savedInstanceState.getString("Key");
                if (str.length() > 11) {
                    calculatorDisplay.setText("");
                }
                else {
                    calculatorDisplay.setText(savedInstanceState.getString("Key"));
                }
            }
            else {
                calculatorDisplay.setText(savedInstanceState.getString("Key"));
            }
        }


    }

    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        TextView calculatorDisplay = findViewById(R.id.calculatorDisplay);
        String savedText = calculatorDisplay.getText().toString();
        savedInstanceState.putString("Key", savedText);
    }

    public String getResult(BigDecimal num) {
        if (num.toString().length() > 2) {
            if (num.toString().charAt(num.toString().length()-2) == '.') {
                if (num.toString().charAt(num.toString().length()-1) == '0') {
                    num = new BigDecimal(num.toString().substring(0, num.toString().length()-2));
                }
            }
        }

        if (num.precision() <= maxSize) return num.toString();
        if (num.precision() - num.scale() > maxSize) {
            textFlag = 1;
            return "Too big.";
        }
        return num.toString();
    }

    public boolean checkIfSign(String calcText) {
        if (calcText.length() == 0) return false;

        char ch = calcText.charAt(calcText.length()-1);

        switch (ch) {
            case '+':
            case '%':
            case '.':
            case '/':
            case '-':
            case '*':
                return true;

            default:
                return false;
        }
    }

    public boolean checkState(String calcText) {
        if (calcText.length() == 0) return true;
        if (calcText.length() == 1 && calcText.charAt(0) == '0') return false;
        if (calcText.charAt(calcText.length() - 1) == ')') return false;
        if (calcText.charAt(calcText.length() - 1) == '0') {
            if (checkIfSign(calcText.substring(0, calcText.length() - 1))) {
                if (calcText.charAt(calcText.length() - 2) == '.') return true;
                return false;
            }
            return true;
        }
        return true;
    }

    public boolean checkComma(String calcText) {
        int commas_found = 0;
        int len = calcText.length();
        if (len == 0) return false;
        int i = len - 1;
        while (i != 0) {
            char c = calcText.charAt(i);
            switch(c) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    i--;
                    continue;
                case '.':
                    commas_found += 1;
                    i--;
                    break;
                case '+':
                case '%':
                case '/':
                case '-':
                case '*':
                    i = 0;
                    break;

            }
        }
        return commas_found <= 0;
    }

    public void onClick(View v) {
        TextView calculatorDisplay = findViewById(R.id.calculatorDisplay);
        if (textFlag == 1) {
            calculatorDisplay.setText("");
            textFlag = 0;
        }
        String currentText = (String) calculatorDisplay.getText();
        switch (v.getId())
        {

            case R.id.numButton0:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "0");
                break;
            case R.id.numButton1:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "1");
                break;
            case R.id.numButton2:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "2");
                break;
            case R.id.numButton3:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "3");
                break;
            case R.id.numButton4:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "4");
                break;
            case R.id.numButton5:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "5");
                break;
            case R.id.numButton6:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "6");
                break;
            case R.id.numButton7:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "7");
                break;
            case R.id.numButton8:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "8");
                break;
            case R.id.numButton9:
                if (currentText.length() < maxSize && checkState(currentText)) calculatorDisplay.setText(currentText + "9");
                break;
            case R.id.backspaceButton:
                if (currentText.length() != 0) calculatorDisplay.setText(currentText.substring(0, currentText.length() - 1));
                break;
            case R.id.cButton:
                calculatorDisplay.setText("");
                break;
            case R.id.divButton:
                if (!checkIfSign(currentText) && currentText.length() < maxSize && currentText.length() != 0) {
                    calculatorDisplay.setText(currentText + "/");
                }
                break;
            case R.id.multiplicationButton:
                if (!checkIfSign(currentText) && currentText.length() < maxSize  && currentText.length() != 0) {
                    calculatorDisplay.setText(currentText + "*");
                }
                break;
            case R.id.addButton:
                if (!checkIfSign(currentText) && currentText.length() < maxSize && currentText.length() != 0) {
                    calculatorDisplay.setText(currentText + "+");
                }
                break;
            case R.id.subtractButton:
                if (!checkIfSign(currentText) && currentText.length() < maxSize  && currentText.length() != 0) {
                    calculatorDisplay.setText(currentText + "-");
                }
                break;
            case R.id.equalsButton:
                if (currentText.length() == 0) break;
                if (checkIfSign(currentText)) currentText = currentText.substring(0, currentText.length() - 1);

                Expression ex = new Expression(currentText);
                try {
                    double res = ex.calculate();
                    BigDecimal bd_res = BigDecimal.valueOf(res);
                    //if (bd_res.scale() >= 1 && res % 10 != 0) bd_res = bd_res.stripTrailingZeros();
                    calculatorDisplay.setText(getResult(bd_res));
                }
                catch (Exception e) {
                    calculatorDisplay.setText("Błąd."); // In case of error, clears display
                    textFlag = 1;
                }
                break;
            case R.id.commaButton:
                if (!checkIfSign(currentText) && currentText.length() < maxSize && currentText.length() != 0) {
                    if (checkComma(currentText)) calculatorDisplay.setText(currentText + ".");
                }
                break;
            case R.id.plusMinusButton:
                try {
                    double x = Double.parseDouble(currentText);
                    x *= -1;
                    BigDecimal bd_res = BigDecimal.valueOf(x);
                    calculatorDisplay.setText(getResult(bd_res));
                }
                catch (Exception e) {
                    calculatorDisplay.setText(currentText);
                }
                break;
            case R.id.modButton:
                if (!checkIfSign(currentText) && currentText.length() < maxSize && currentText.length() != 0) calculatorDisplay.setText(currentText + "#");
                break;
            case R.id.sinButton:
                if (currentText.length() < maxSize - 5 && currentText.length() != 0) {
                    calculatorDisplay.setText("sin(" + currentText + ")");
                }
                break;
            case R.id.cosButton:
                if (currentText.length() < maxSize - 5 && currentText.length() != 0) {
                    calculatorDisplay.setText("cos(" + currentText + ")");
                }
                break;
            case R.id.tanButton:
                if (currentText.length() < maxSize - 5 && currentText.length() != 0) {
                    calculatorDisplay.setText("tan(" + currentText + ")");
                }
                break;
            case R.id.sqrtButton:
                if (currentText.length() < maxSize - 6 && currentText.length() != 0) {
                    calculatorDisplay.setText("sqrt(" + currentText + ")");
                }
                break;
            case R.id.lnButton:
                if (currentText.length() < maxSize - 4  && currentText.length() != 0) {
                    calculatorDisplay.setText("ln(" + currentText + ")");
                }
                break;
            case R.id.logButton:
                if (currentText.length() < maxSize - 5  && currentText.length() != 0) {
                    calculatorDisplay.setText("lg(" + currentText + ")");
                }
                break;
            case R.id.pow_x2Button:
                if (currentText.length() < maxSize - 4  && currentText.length() != 0) {
                    calculatorDisplay.setText("(" + currentText + "^2)");
                }
                break;
            case R.id.pow_xyButton:
                if (currentText.length() < maxSize - 4  && currentText.length() != 0) {
                    calculatorDisplay.setText("(" + currentText + ")^");
                }
                break;
            default:
                calculatorDisplay.setText("NaN");
                textFlag = 1;
                break;

        }
        textBox = (String) calculatorDisplay.getText();
    }
}