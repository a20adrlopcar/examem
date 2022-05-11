package com.example.estudiexamenopenweatherapi;

import android.net.Uri;
import android.util.Log;
import android.util.Printer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {



    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    // Base URL for Books API.
    private static final String BASE_WEATHER_URL =  "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String API_KEY_NUMBER = "87a3e0bb394ac9fcf668aa28c1fedc7b";
    private static String API_KEY_CONFIG=  "&appid=" + API_KEY_NUMBER ;



    static String getWeatherInfo(String queryString){

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;
        try {
            //https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}
            Uri builtURI = Uri.parse(BASE_WEATHER_URL +   queryString + "&lang=ca" +API_KEY_CONFIG).buildUpon()
                    .build();
            System.out.println("----------LINK---------: " + builtURI);
            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpsURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            Log.d(LOG_TAG, "URL: " + builtURI.toString());
            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();

            // Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Use a StringBuilder to hold the incoming response.
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // Since it's JSON, adding a newline isn't necessary (it won't
                // affect parsing) but it does make debugging a *lot* easier
                // if you print out the completed buffer for debugging.
                builder.append("\n");
            }
            if (builder.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            bookJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }
}
