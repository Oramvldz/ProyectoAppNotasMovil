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

import java.util.HashMap;
import java.util.Map;

public class ActualizarPassActivity extends AppCompatActivity {
    EditText ActualizarPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_pass);
        ActualizarPass=findViewById(R.id.edt_CambiarPass);
    }

    public void siguiente(View view)
    {
        //Obteniendo la variable y pasandola a string por que asi lo pide la api
        SharedPreferences prefs = getSharedPreferences("shared_login_data",getApplicationContext().MODE_PRIVATE);
        int Id=prefs.getInt("Id",0);

        String url="https://proyectoappnotastallerfic.000webhostapp.com/ApiActualizarPass/"+Id;

        StringRequest postrequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent intent =new Intent(getApplicationContext(),MisNotasActivity.class);
                startActivity(intent);
                Toast.makeText(ActualizarPassActivity.this,"Recurso Actualizado",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActualizarPassActivity.this,"Error",Toast.LENGTH_SHORT).show();
            }
        })
        {
            protected Map<String, String> getParams(){
                Map<String,String>params=new HashMap<>();
                params.put("Pass",ActualizarPass.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postrequest);
    }
}
