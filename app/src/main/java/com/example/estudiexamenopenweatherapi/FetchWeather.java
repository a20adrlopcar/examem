package com.example.estudiexamenopenweatherapi;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


public class FetchWeather extends AsyncTask<String, Void, String> {

    String description, humitat;
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private WeakReference<TextView> tvDescription;
    private WeakReference<TextView> tvHumedad;
    private WeakReference<EditText> etBuscador;

    public FetchWeather(TextView tvDescription, TextView tvHumedad, EditText etBuscador) {
        this.tvDescription = new WeakReference<>(tvDescription);
        this.tvHumedad = new WeakReference<>(tvHumedad);
        this.etBuscador = new WeakReference<>(etBuscador);




    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getWeatherInfo(etBuscador.get().getText().toString());
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            description = fetchDescription(jsonObject);
            humitat = fetchHumitat(jsonObject);

            tvDescription.get().setText(description);
            tvHumedad.get().setText(humitat);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String fetchDescription(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        JSONObject description = jsonArray.getJSONObject(0);
        return description.getString("description");


    }

    private String fetchHumitat(JSONObject jsonObject) throws JSONException {
        JSONObject main  = jsonObject.getJSONObject("main");
        return main.getString("humidity");


    }




}
