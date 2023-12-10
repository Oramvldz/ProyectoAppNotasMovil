package com.example.proyectoappnotasmovil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {
    EditText edt_email1,edt_pass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edt_email1=findViewById(R.id.edt_email1);
        edt_pass1=findViewById(R.id.edt_pass1);
    }
    public void Registro(View view)
    {
    Intent siguiente=new Intent(this,RegistroActivity.class);
    startActivity(siguiente);
    }

    public void Login(View view)
    {
        /*
        Intent siguiente =new Intent (this, LoginActivity.class);
        startActivity(siguiente);
         */
        String url="https://proyectoappnotastallerfic.000webhostapp.com/ApiLogin";
        StringRequest PostRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    Toast.makeText(LoginActivity.this,"Iniciando sesion",Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Log.e("Error",e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams(){
                Map<String,String>params =new HashMap<>();
                params.put("Email",edt_email1.getText().toString());
                params.put("Pass",edt_pass1.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(PostRequest);
    }
}