package com.example.sakkar.busservice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BusList extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);
        String s=getIntent().getStringExtra("data");
        textView= (TextView) findViewById(R.id.textList);
        try {
            JSONObject jsonObject=new JSONObject(s);
            JSONArray jsonArray=jsonObject.getJSONArray("bus list");
            String info="There are "+jsonArray.length()+" Bus Available\n";
            for (int i=0;i<jsonArray.length();i++){
                JSONObject object= (JSONObject) jsonArray.get(i);
                info+="\nBus Name :"+object.getString("bus_name")+"\n"+"Ticket Price :"+object.getDouble("ticket")+
                    "\nRoute : "+object.getString("route")+"\n";
            }
            textView.setText(info);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
