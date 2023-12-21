package com.example.proyectoappnotasmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MisNotasActivity extends AppCompatActivity {

    private ArrayList<Nota> ListaNotas;
    private RequestQueue rq;
    private RecyclerView Rv1;

    private AdaptadorNota AdaptadorNota;

    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_notas);
        /*
        textView=findViewById(R.id.textView3);
        SharedPreferences prefs = getSharedPreferences("shared_login_data",Context.MODE_PRIVATE);
        int Idint=prefs.getInt("Id",0);
        String Id=String.valueOf(Idint);
        textView.setText(Id);
         */
        ListaNotas= new ArrayList<>();
        rq= Volley.newRequestQueue(this);
        CargarNota();

        Rv1=findViewById(R.id.Rv1);
        LinearLayoutManager Linearlayout =new LinearLayoutManager(this);
        Rv1.setLayoutManager(Linearlayout);

        AdaptadorNota=new AdaptadorNota();
        Rv1.setAdapter(AdaptadorNota);
    }

    private void CargarNota() {
        SharedPreferences prefs = getSharedPreferences("shared_login_data",getApplicationContext().MODE_PRIVATE);
        int Id=prefs.getInt("Id",0);
        String url="https://proyectoappnotastallerfic.000webhostapp.com/MisNotas/SeleccionarNotas/"+Id;

        StringRequest getRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray Notas= new JSONArray(response);
                    JSONObject Onotas= new JSONObject();
                    for (int i=0; i<Notas.length(); i++){
                        Onotas=Notas.getJSONObject(i);
                        String Titulo=Onotas.getString("Titulo");
                        String Contenido=Onotas.getString("Contenido");

                        Nota nota= new Nota(Titulo, Contenido);
                        ListaNotas.add(nota);

                        AdaptadorNota.notifyItemRangeInserted(ListaNotas.size(), 1);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        rq.add(getRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.Perfil:
                Intent Perfil=new Intent(this,PerfilActivity.class);
                startActivity(Perfil);
                break;
            case R.id.CerrarSesion:
                SharedPreferences prefs = getSharedPreferences("shared_login_data",getApplicationContext().MODE_PRIVATE);
                prefs.edit().clear().commit();
                Intent CerrarSesion=new Intent(this,LoginActivity.class);
                BorrarHistorialActivitys(CerrarSesion);
                startActivity(CerrarSesion);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void BorrarHistorialActivitys(Intent intent){
        //Con esto se borra el historial de activity osea no me dejara ir para atras una vez cierre sesion
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    }

    public void CrearNota(View view)
    {
        Intent intent= new Intent(this,CrearNotaActivity.class);
        startActivity(intent);
    }

    private class AdaptadorNota extends RecyclerView.Adapter<AdaptadorNota.AdaptadorNotaHolder>{

        @NonNull
        @Override
        public AdaptadorNotaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new AdaptadorNotaHolder(getLayoutInflater().inflate(R.layout.layout_nota, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull AdaptadorNotaHolder holder, int position) {
            holder.imprimir(position);
        }

        @Override
        public int getItemCount() {
            return ListaNotas.size();
        }

        class AdaptadorNotaHolder extends RecyclerView.ViewHolder{
            TextView tvTitulo, tvContenido;
            public AdaptadorNotaHolder(@NonNull View itemView) {
                super(itemView);
                tvTitulo=itemView.findViewById(R.id.tvTitulo);
                tvContenido=itemView.findViewById(R.id.tvContenido);
            }

            public void imprimir(int position) {
              tvTitulo.setText("Titulo: "+ListaNotas.get(position).getTitulo());
              tvContenido.setText("Contenido: "+ListaNotas.get(position).getContenido());
            }
        }
    }

    public void Refrescar(View view)
    {
        Intent intent= new Intent(this, MisNotasActivity.class);
        startActivity(intent);
    }

}