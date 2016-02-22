package com.delta.pragyan16;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class MainActivity extends Activity {
    public static final String URL = "http://api.pragyan.org";

    public LinearLayout[] frac;
    public int offset=0;
    public int FRAC_COUNT=10;
    TextView txtschedule,txtevents,txtprofile;
    Typeface typeface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utilities.sp= getSharedPreferences("llep",0);
        offset=Utilities.sp.getInt("offset",0);
        Utilities.init_colors();
        frac=new LinearLayout[10];
        for(int i=1;i<=10;i++)
        frac[i-1]=(LinearLayout)findViewById(getResources().getIdentifier("frac" + i, "id", "com.delta.pragyan16"));


        Fractspin();
        View.OnClickListener fraconclick =new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fractspin();
            }
        };

        frac[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Log.e("touch","touched");

                //if(offset+FRAC_COUNT==Utilities.strcolors.length)off

                Fractspin();

                Intent i=new Intent(MainActivity.this,ClustersActivity.class);
                MainActivity.this.startActivity(i);
            }
        });
        frac[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Log.e("touch","touched");

                //if(offset+FRAC_COUNT==Utilities.strcolors.length)off

                Fractspin();

                Intent i = new Intent(MainActivity.this, UpcomingActivity.class);
                MainActivity.this.startActivity(i);
            }
        });
        frac[9].setOnClickListener(fraconclick);
        frac[8].setOnClickListener(fraconclick);
        frac[7].setOnClickListener(fraconclick);
        frac[6].setOnClickListener(fraconclick);
        frac[5].setOnClickListener(fraconclick);
        frac[4].setOnClickListener(fraconclick);
        frac[3].setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fractspin();

                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                MainActivity.this.startActivity(i);
            }
        });
        frac[1].setOnClickListener(fraconclick);

            txtevents=(TextView)findViewById(R.id.txtevents);
            txtschedule=(TextView)findViewById(R.id.txtschedule);
        typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto-Light.ttf");
        txtevents.setTypeface(typeface);
        txtschedule.setTypeface(typeface);

        frac[0].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                //Log.e("tt","tt");

                    Fractspin();

                return false;
            }
        });
        frac[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
view.setSelected(true);
                Log.e("tt","tt");
                    Fractspin();

                return false;
            }
        });
    }



    void Fractspin()
    {

        offset++;
        for(int i=0;i<10;i++)

        frac[i].setBackgroundColor(Utilities.colors[(i+offset)%Utilities.strcolors.length]);
    }



    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences.Editor editor = Utilities.sp.edit();

        editor.putInt("offset", offset);

        editor.apply();

    }

}
