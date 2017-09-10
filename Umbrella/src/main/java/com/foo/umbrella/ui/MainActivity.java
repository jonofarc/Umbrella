package com.foo.umbrella.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.foo.umbrella.R;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    final static private String TAG= MainActivity.class.getSimpleName()+"_TAG";



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



     }




    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_settings:
                Toast.makeText(this, "Going to settings", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, UmbrellaSettings.class);
                startActivity(intent);
                break;

            
        }
    }


}
