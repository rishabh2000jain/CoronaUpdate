package com.example.coronaupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, DataLoader.OnDownloadComplete {
    TextInputEditText editText;
    MaterialButton button, totalCasesButton;
    TextView textView;
    String URL = "", countryName = "";
    String URL_TOTAL_CASES = "https://srv1.worldometers.info/coronavirus/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.textInput);
        button = findViewById(R.id.buttonPanel);
        button.setOnClickListener(this);
        totalCasesButton = findViewById(R.id.overallCases);
        totalCasesButton.setOnClickListener(this);
        textView = findViewById(R.id.TEXT_STATUS_ID);
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    showDataOnTextBar();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.buttonPanel) {
            showDataOnTextBar();

        } else {
            removeKeyBoard(MainActivity.this);
            DataLoader dataLoader = new DataLoader(this);
            dataLoader.execute(URL_TOTAL_CASES);
        }

    }

    private void removeKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void setDataCompleteListener(String Data, String TotalData) {
        textView.setText(Data.substring(0, Data.length() - 13));
        Log.i("Html Hadders", TotalData);
    }

    void showDataOnTextBar() {
        if (!editText.getText().toString().equals("")) {
            countryName = editText.getText().toString();
            countryName = (countryName.substring(0, 1).toUpperCase()).concat(countryName.substring(1));
            URL = "https://www.worldometers.info/coronavirus/country/" + countryName + "/";
            removeKeyBoard(MainActivity.this);
            DataLoader dataLoader = new DataLoader(this);
            dataLoader.execute(URL);

        } else {
            Toast.makeText(MainActivity.this, "Country name can't be empty", Toast.LENGTH_SHORT).show();
        }
    }
}
