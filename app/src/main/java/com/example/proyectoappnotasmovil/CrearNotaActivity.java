package com.example.proyectoappnotasmovil;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class CrearNotaActivity extends AppCompatActivity {

    EditText Titulo,Contenido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);
        Titulo=findViewById(R.id.edt_titulo);
        Contenido=findViewById(R.id.edt_contenido);
    }
    public boolean validar()
    {
        String c1,c2;
        boolean retorno=true;
        c1=Titulo.getText().toString();
        c2=Contenido.getText().toString();

        if(c1.isEmpty())
        {
            retorno=false;
            Titulo.setError("Este campo no puede estar vacio");
        }

        if(c2.isEmpty())
        {
            retorno=false;
            Contenido.setError("Este campo no puede estar vacio");
        }
        return retorno;
    }


    public void CrearNota(View view)
    {
        if(validar())
        {
            //Obteniendo la variable y pasandola a string por que asi lo pide la api
            SharedPreferences prefs = getSharedPreferences("shared_login_data",getApplicationContext().MODE_PRIVATE);
            int Idint=prefs.getInt("Id",0);
            String Id=String.valueOf(Idint);

            String url="https://proyectoappnotastallerfic.000webhostapp.com/MisNotas/CrearNota";

            StringRequest postrequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Intent intent =new Intent(getApplicationContext(),MisNotasActivity.class);
                    BorrarHistorialActivitys(intent);
                    startActivity(intent);
                    Toast.makeText(CrearNotaActivity.this,"Recurso creado correctamente",Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(CrearNotaActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }
            })
            {
                protected Map<String, String> getParams(){
                    Map<String,String>params=new HashMap<>();
                    params.put("Id_usuario",Id);
                    params.put("Titulo",Titulo.getText().toString());
                    params.put("Contenido",Contenido.getText().toString());
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(postrequest);
        }
        }
        public void BorrarHistorialActivitys(Intent intent){
            //Con esto se borra el historial de activity osea no me dejara ir para atras una vez cierre sesion
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        }
    }