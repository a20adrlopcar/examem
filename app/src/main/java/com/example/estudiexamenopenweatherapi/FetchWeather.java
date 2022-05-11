package com.example.estudiexamenopenweatherapi;

import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;


public class FetchWeather extends AsyncTask<String, Void, String> {

    String description, speed,visibility;
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();
    private WeakReference<TextView> tvDescription;
    private WeakReference<TextView> tvspeed;
    // private WeakReference<TextView> tVisibility;
    private WeakReference<EditText> etBuscador;

    public FetchWeather(TextView tvDescription, TextView tvspeed, TextView tvVisinility,EditText etBuscador) {
        this.tvDescription = new WeakReference<>(tvDescription);
        this.tvspeed = new WeakReference<>(tvspeed);
        //this.tVisibility = new WeakReference<>(tvVisinility);
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
            speed = fetchspeed(jsonObject);
            //visibility=Visinility(jsonObject);
            tvDescription.get().setText(description);
            tvspeed.get().setText(speed);
            //tVisibility.get().setText(visibility);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private String fetchDescription(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("weather");
        JSONObject description = jsonArray.getJSONObject(0);
        return description.getString("description");


    }

    private String fetchspeed(JSONObject jsonObject) throws JSONException {
        JSONObject main  = jsonObject.getJSONObject("wind");
        return main.getString("speed");


    }
    private String Visinility(JSONObject jsonObject) throws JSONException {
        JSONObject main  = new JSONObject();
        return main.getString("visibility");


    }




}
