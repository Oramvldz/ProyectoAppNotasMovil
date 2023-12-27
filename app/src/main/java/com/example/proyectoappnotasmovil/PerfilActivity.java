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

public class PerfilActivity extends AppCompatActivity {
    EditText Edt_nombres, Edt_apellidos, Edt_correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Edt_nombres=findViewById(R.id.et_Nombres);
        Edt_apellidos=findViewById(R.id.et_Apellidos);
        Edt_correo=findViewById(R.id.et_Correo);

        //llamar al metodo perfil
        Perfil();

    }

    public void Perfil()
    {

        Toast.makeText(PerfilActivity.this,"entro",Toast.LENGTH_SHORT).show();

        SharedPreferences prefs = getSharedPreferences("shared_login_data",getApplicationContext().MODE_PRIVATE);
        int Id=prefs.getInt("Id",0);
        String url="https://proyectoappnotastallerfic.000webhostapp.com/ApiPerfil/"+Id;

        StringRequest getRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject Perfil= new JSONObject(response);
                    Edt_nombres.setText("Nombres: "+Perfil.getString("Nombres"));
                    Edt_apellidos.setText("Apellidos: "+Perfil.getString("Apellidos"));
                    Edt_correo.setText("Correo: "+Perfil.getString("Email"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(PerfilActivity.this,"Error",Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(this).add(getRequest);
    }

    public void ActualizarEmail(View view)
    {
        Intent intent =new Intent(this,ActualizarEmailActivity.class);
        startActivity(intent);
    }

    public void ActualizarPass(View view){
        Intent intent=new Intent(this,ActualizarPassActivity.class);
        startActivity(intent);
    }
}