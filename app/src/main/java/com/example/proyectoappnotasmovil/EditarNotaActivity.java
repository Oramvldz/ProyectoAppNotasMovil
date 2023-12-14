package com.example.proyectoappnotasmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class EditarNotaActivity extends AppCompatActivity {

  EditText titulo, contenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nota);

        titulo = findViewById(R.id.edt_titulo2);
        contenido = findViewById(R.id.edt_contenido2);
    }

    public boolean validar (){
        boolean retorno=true;
        String campo1,campo2;
        campo1=titulo.getText().toString();
        campo2=contenido.getText().toString();

        if(campo1.isEmpty())
        {
            titulo.setError("Este campo no puede estar vacio");
            retorno=false;
        }
        if(campo2.isEmpty())
        {
            contenido.setError("Este campo no puede estar vacio");
            retorno=false;
        }
        return retorno;

    }
}

