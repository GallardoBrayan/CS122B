package com.example.henrywoodstock.fabflix;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

public class MovieSearch extends AppCompatActivity {

    private SearchMovie mAuthTask = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_search);
    }


    public void searchMovies(View view)
    {
        EditText et = (EditText) findViewById(R.id.movie_title);
        String title = et.getText().toString();
        mAuthTask = new SearchMovie(title);
        mAuthTask.execute((Void) null);

    }

    public class SearchMovie extends AsyncTask<Void, Void, ArrayList<String>> {

        private final String mTitle;

        SearchMovie(String title) {
            mTitle = title;
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            StringBuilder sb = new StringBuilder();
            JSONObject json = null;
            try {
                String query = URLEncoder.encode(mTitle, "utf-8");
                URL url = new URL("http://fabflix.xyz:8080/fabflix2//AndroidMovieSearch?title="+query);
                // Simulate network access.
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty("Accept", "application/json");
                urlConnection.connect();

                int status = urlConnection.getResponseCode();

                switch (status) {
                    case 200:
                    case 201:
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                        String line;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        json = new JSONObject(sb.toString());

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            ArrayList<String> result = new ArrayList<String>();
            buildList(json, result);
            return result;
        }

        private void buildList(JSONObject json, ArrayList<String> res)
        {
            Iterator<String> iter = json.keys();
            while(iter.hasNext())
            {
                try {
                    res.add((String)json.get(iter.next()));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        @Override
        protected void onPostExecute(ArrayList<String> movies) {
            mAuthTask = null;
            TextView banner = (TextView)findViewById(R.id.Movie_banner);
            banner.setText("Search Results for Title: " + mTitle);
            if(banner.getVisibility() == View.INVISIBLE)
                banner.setVisibility(View.VISIBLE);

            LinearLayout ll = (LinearLayout) findViewById(R.id.movie_list);

            if(ll.getChildCount() > 0)
                ll.removeAllViews();

            for(String title: movies)
            {
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView tv = new TextView(getApplicationContext());
                tv.setLayoutParams(lp);
                tv.setText(title);
                tv.setTextColor(Color.BLACK);
                ll.addView(tv);

            }
        }

    }



}
