package com.example.proyectoappnotasmovil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditarNotaActivity extends AppCompatActivity {

  EditText titulo, contenido;
    int Id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_nota);

        titulo = findViewById(R.id.edt_titulo);
        contenido = findViewById(R.id.edt_contenido);
        titulo.setText(getIntent().getStringExtra("Titulo"));
        contenido.setText(getIntent().getStringExtra("Contenido"));
        Id=getIntent().getIntExtra("IdNota",0);
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

    public void Accion_Modificar(View view)
    {
        if(validar())
        {
            Modificar_Nota(Id);
        }
    }

    public void Accion_Eliminar(View view)
    {
            EliminarNota(Id);
    }

    public void Modificar_Nota(int Id)
    {
        String url="https://proyectoappnotastallerfic.000webhostapp.com/MisNotas/ModificarNota/"+Id;
        StringRequest PostRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditarNotaActivity.this, "Recurso actualizado con exito", Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(EditarNotaActivity.this,MisNotasActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditarNotaActivity.this,"El servidor no esta disponible en este momento",Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams(){
                Map<String,String>params =new HashMap<>();
                params.put("Titulo",titulo.getText().toString());
                params.put("Contenido",contenido.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(PostRequest);
    }

    public void EliminarNota(int Id)
    {
        String url="https://proyectoappnotastallerfic.000webhostapp.com/MisNotas/EliminarNota/"+Id;

        StringRequest getRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(EditarNotaActivity.this,"Recurso eliminado correctamente",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(EditarNotaActivity.this,MisNotasActivity.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditarNotaActivity.this,"El servidor no esta disponible por el momento",Toast.LENGTH_SHORT).show();
            }
        });
        Volley.newRequestQueue(EditarNotaActivity.this).add(getRequest);
    }
}

