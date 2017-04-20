package com.example.sakkar.busservice;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class BusService extends AppCompatActivity {

    Spinner start,end;
    Button submit;
    ProgressBar progress;
    ConstraintLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_service);

        start= (Spinner) findViewById(R.id.startSpinner);
        end= (Spinner) findViewById(R.id.destinationSpinner);

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.location_list));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        start.setAdapter(adapter);
        end.setAdapter(adapter);
        submit= (Button) findViewById(R.id.submit);
        progress= (ProgressBar) findViewById(R.id.progress);
        container= (ConstraintLayout) findViewById(R.id.container);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoOnBackGround doOnBack=new DoOnBackGround();
                String data=codedData("source",start.getSelectedItem().toString())+"&"
                        +codedData("destination",end.getSelectedItem().toString());
                doOnBack.execute(data);
                progress.setVisibility(View.VISIBLE);
                container.setVisibility(View.INVISIBLE);
            }
        });
    }

    public String codedData(String key,String value){
        try {
            return URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void successfull(String s) {
        progress.setVisibility(View.INVISIBLE);
        container.setVisibility(View.VISIBLE);
        startActivity(new Intent(this,BusList.class).putExtra("data",s));
    }

    public class DoOnBackGround extends AsyncTask<String,String,String>{
        String link;

        public DoOnBackGround() {
            link="https://mined-human.000webhostapp.com/busservice.php";
        }

        @Override
        protected String doInBackground(String... strings) {
            String data=strings[0];
            OutputStreamWriter wr;
            URLConnection conn;
            try {
                URL url = new URL(link);
                conn = url.openConnection();
                conn.setDoOutput(true);
                wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                wr.write( data );
                wr.flush();
                wr.close();
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));

                String line;

                // Read Server Response
                line = reader.readLine();
                Log.w("Sakkar",line);
                reader.close();
                return line;
            } catch (Exception e) {
                Log.w("Sakkar",e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            successfull(s);
        }
    }
}
