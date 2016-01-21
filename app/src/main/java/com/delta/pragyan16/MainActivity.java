package com.delta.pragyan16;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


public class MainActivity extends Activity {
    public static final String URL = "http://api.pragyan.org";

    public LinearLayout[] frac;
    public int offset=0;
    public int FRAC_COUNT=10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utilities.init_colors();
        frac=new LinearLayout[10];
        for(int i=1;i<=10;i++)
        frac[i-1]=(LinearLayout)findViewById(getResources().getIdentifier("frac" + i, "id", "com.delta.pragyan16"));


        Fractspin();

        frac[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // Log.e("touch","touched");
                offset++;
                //if(offset+FRAC_COUNT==Utilities.strcolors.length)off
                Fractspin();
            }
        });
    }


    void Fractspin()
    {

        for(int i=0;i<10;i++)

        frac[i].setBackgroundColor(Utilities.colors[(i+offset)%Utilities.strcolors.length]);
    }


}
