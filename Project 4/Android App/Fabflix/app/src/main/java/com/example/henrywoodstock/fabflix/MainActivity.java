package com.example.henrywoodstock.fabflix;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import org.json.*;
import android.util.Log;
/*import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;*/


public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com..example.henrywoodstock.fabflix.MESSAGE";
    private String message = "Nothin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void sendMessage(View view) {

        // String url = "http://192.168.56.101:8080/fabflix/AndroidLogin";


        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText et = (EditText) findViewById(R.id.edit_message);
        final String message = et.getText().toString();


        try {


            URL url = new URL("http://fabflix.xyz:8080/fabflix2/AndroidLogin");
            new LoginRequest().execute(url);

        } catch (Exception e) {
            e.printStackTrace();
        }


        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }

    private class LoginRequest extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            String ret = "nothing";
            // params comes from the execute() call: params[0] is the url.
            try {
                HttpURLConnection urlConnection = (HttpURLConnection) urls[0].openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.connect();

                int status = urlConnection.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                        StringBuilder sb = new StringBuilder();
                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line+"\n");
                        }
                        br.close();
                        ret = sb.toString();
                }

            } catch (Exception e) {
                e.printStackTrace();

            }
            return ret;
            }


        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            message = result;
        }
    }
}
