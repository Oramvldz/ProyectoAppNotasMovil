package com.example.proyectoappnotasmovil;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {
    EditText edt_email,edt_pass,edt_nombres,edt_apellidos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        edt_email=findViewById(R.id.edt_email);
        edt_pass=findViewById(R.id.edt_pass);
        edt_nombres=findViewById(R.id.edt_nombres);
        edt_apellidos=findViewById(R.id.edt_apellidos);
    }
    public boolean validar()
    {
        boolean retorno =true;

        String c1,c2,c3,c4;
        c1=edt_email.getText().toString();
        c2=edt_pass.getText().toString();
        c3=edt_nombres.getText().toString();
        c4=edt_apellidos.getText().toString();

        if(c1.isEmpty())
        {
            edt_email.setError("Este campo no puede estar vacio");
            retorno=false;
        }
        if(c2.isEmpty())
        {
            edt_pass.setError("Este campo no puede estar vacio");
            retorno=false;

        }
        if(c3.isEmpty())
        {
            edt_nombres.setError("Este campo no puede estar vacio");
            retorno=false;

        }
        if(c4.isEmpty())
        {
            edt_apellidos.setError("Este campo no puede estar vacio");
            retorno=false;
        }
        return retorno;
    }

    public void siguiente (View view)
    {
        if(validar())
        {
            String url="https://proyectoappnotastallerfic.000webhostapp.com/ApiRegistro";
            StringRequest PostRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        String status= jsonObject.getString("status");
                        if(jsonObject.getString("status").equals("201"))
                        {
                            Toast.makeText(RegistroActivity.this,"Recurso Añadido con exito",Toast.LENGTH_LONG).show();
                            Intent intent =new Intent(getApplicationContext(),LoginActivity.class);
                            BorrarHistorialActivitys(intent);
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        Log.e("Error",e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        String responseBody=new String(error.networkResponse.data,"utf-8");
                        JSONObject jsonObject=new JSONObject(responseBody);
                        if(jsonObject.getInt("status")==409)
                        {
                            Toast.makeText(RegistroActivity.this,"El email que trataste de añadir esta duplicado",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(RegistroActivity.this, "El servidor no esta disponible en este momento", Toast.LENGTH_SHORT).show();
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new RuntimeException(e);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            })
            {
                @Nullable
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String>params =new HashMap<>();
                    params.put("Email",edt_email.getText().toString());
                    params.put("Pass",edt_pass.getText().toString());
                    params.put("Nombres",edt_nombres.getText().toString());
                    params.put("Apellidos",edt_apellidos.getText().toString());
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(PostRequest);
        }
        }
    public void BorrarHistorialActivitys(Intent intent){
        //Con esto se borra el historial de activity osea no me dejara ir para atras una vez cierre sesion
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }
    }