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

import java.io.UnsupportedEncodingException;
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

    public boolean validar()
    {
        boolean retorno=true;
        String c1,c2;
        c1=edt_email1.getText().toString();
        c2=edt_pass1.getText().toString();

        if(c1.isEmpty())
        {
            edt_email1.setError("Este campo no puede estar vacio");
            retorno=false;
        }
        if(c2.isEmpty())
        {
            edt_pass1.setError("Este campo no puede estar vacio");
            retorno=false;
        }
        return retorno;
    }
    public void Registro(View view)
    {
    Intent siguiente=new Intent(this,RegistroActivity.class);
    startActivity(siguiente);
    }

    public void Login(View view)
    {
        if(validar())
        {
            String url="https://proyectoappnotastallerfic.000webhostapp.com/ApiLogin";
            StringRequest PostRequest= new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        //VARIABLES DE LOGIN
                        SharedPreferences prefs = getSharedPreferences("shared_login_data",getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        JSONObject jsonObject=new JSONObject(jsonArray.get(0).toString());
                        //EDITANDO PARA METER DATOS
                        String IdString=jsonObject.getString("Id");
                        int Id=Integer.parseInt(IdString);
                        editor.putInt("Id",Id);
                        //editor.putString("Nombres",jsonObject.getString("Nombres"));
                        //editor.putString("Email",jsonObject.getString("Email"));
                        editor.commit();
                        //INTENCION HACIA LA SIGUIENTE PANTALLA
                        Toast.makeText(LoginActivity.this,"Iniciando sesion",Toast.LENGTH_SHORT).show();
                        Intent siguiente =new Intent (getApplicationContext(),MisNotasActivity.class);
                        BorrarHistorialActivitys(siguiente);
                        startActivity(siguiente);
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
                        if(jsonObject.getInt("status")==404)
                        {
                            Toast.makeText(LoginActivity.this, "Credenciales invalidas", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(LoginActivity.this, "El servidor no esta disponible en este momento", Toast.LENGTH_SHORT).show();
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
                    params.put("Email",edt_email1.getText().toString());
                    params.put("Pass",edt_pass1.getText().toString());
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