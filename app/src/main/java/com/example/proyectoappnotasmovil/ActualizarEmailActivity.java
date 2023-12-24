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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ActualizarEmailActivity extends AppCompatActivity {
    EditText Edt_ActualizarEmail,Edt_EmailAntiguo,Edt_ContraseñaAntigua;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_email);
        Edt_ActualizarEmail=findViewById(R.id.edt_CambiarEmail);
        Edt_EmailAntiguo=findViewById(R.id.edt_EmailCredenciales);
        Edt_ContraseñaAntigua=findViewById(R.id.edt_PassCredenciales);
    }

    public boolean validar()
    {
        String c1=Edt_ActualizarEmail.getText().toString();
        String c2=Edt_EmailAntiguo.getText().toString();
        String c3=Edt_ContraseñaAntigua.getText().toString();
        boolean retorno=true;

        if(c1.isEmpty())
        {
            Edt_ActualizarEmail.setError("Este campo no puede estar vacio");
            retorno=false;
        }
        if(c2.isEmpty())
        {
            Edt_EmailAntiguo.setError("Este campo no puede estar vacio");
            retorno=false;
        }
        if(c3.isEmpty())
        {
            Edt_ContraseñaAntigua.setError("Este campo no puede estar vacio");
            retorno=false;
        }
        return retorno;
    }
    public void Actualizar(View view)
    {
        if(validar())
        {
            //Obteniendo la variable y pasandola a string por que asi lo pide la api
            SharedPreferences prefs = getSharedPreferences("shared_login_data",getApplicationContext().MODE_PRIVATE);
            int Id=prefs.getInt("Id",0);

            String url="https://proyectoappnotastallerfic.000webhostapp.com/ApiActualizarEmail/"+Id;

            StringRequest postrequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Intent intent =new Intent(getApplicationContext(),MisNotasActivity.class);
                    startActivity(intent);
                    Toast.makeText(ActualizarEmailActivity.this,"Recurso Actualizado",Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        String responseBody=new String(error.networkResponse.data,"utf-8");
                        JSONObject jsonObject = new JSONObject( responseBody );
                        if(jsonObject.getInt("status")==404)
                        {
                            Toast.makeText(ActualizarEmailActivity.this,"Error, credenciales no validas",Toast.LENGTH_SHORT).show();
                        }else if(jsonObject.getInt("status")==409)
                        {
                            Toast.makeText(ActualizarEmailActivity.this,"El email ya existe, intente con otro",Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(ActualizarEmailActivity.this,"El servidor no esta disponible en este momento",Toast.LENGTH_SHORT).show();
                        }

                    } catch (UnsupportedEncodingException e) {

                    } catch (JSONException e) {

                    }
                }
            })
            {
                protected Map<String, String> getParams(){
                    Map<String,String>params=new HashMap<>();
                    params.put("EmailAntiguo",Edt_EmailAntiguo.getText().toString());
                    params.put("PassAntigua",Edt_ContraseñaAntigua.getText().toString());
                    params.put("Email",Edt_ActualizarEmail.getText().toString());
                    return params;
                }
            };
            Volley.newRequestQueue(this).add(postrequest);
        }
    }
}