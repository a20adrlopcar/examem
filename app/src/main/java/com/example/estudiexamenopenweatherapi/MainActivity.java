package com.example.estudiexamenopenweatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView tvDescription, tvHumedad;
    EditText etBuscador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NOSEQUELLAMARLO();

    }

    private void NOSEQUELLAMARLO() {
        tvDescription = findViewById(R.id.tvDescription);
        tvHumedad = findViewById(R.id.tvHumedad);
        etBuscador= findViewById(R.id.etBuscador);
    }


    public void searchWeather(View view) {
        // Get the search string from the input field.
        String queryString = etBuscador.getText().toString();
        new FetchWeather(tvHumedad, tvDescription, etBuscador).execute(queryString);
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;


        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            new FetchWeather(tvHumedad, tvDescription, etBuscador).execute(queryString);
        }else {
            if (queryString.length() == 0) {
                tvDescription.setText("Error");

            } else {
                tvDescription.setText("");
            }
        }

    }
}