package com.example.proyectoappnotasmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PerfilActivity extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        editText=findViewById(R.id.editTextText);
        editText.setText("HOLA");
    }
/*
    public void ActualizarEmail(View view)
    {
        Intent intent =new Intent(this,ActualizarEmailActivity.class);
        startActivity(intent);
    }

    public void ActualizarPass(View view){
        Intent intent=new Intent(this,ActualizarPassActivity.class);
        startActivity(intent);
    }*/
}