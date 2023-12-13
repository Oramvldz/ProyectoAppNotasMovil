package com.example.proyectoappnotasmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MisNotasActivity extends AppCompatActivity {
    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_notas);
        /*textView=findViewById(R.id.textView3);
        SharedPreferences prefs = getSharedPreferences("shared_login_data",Context.MODE_PRIVATE);
        int Idint=prefs.getInt("Id",0);
        String Id=String.valueOf(Idint);
        textView.setText(Id);
         */
    }

    public void CrearNota(View view)
    {
        Intent intent= new Intent(this,CrearNotaActivity.class);
        startActivity(intent);
    }
}