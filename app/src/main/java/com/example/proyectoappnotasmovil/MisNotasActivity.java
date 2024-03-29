package com.example.proyectoappnotasmovil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import  androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
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
import android.widget.Button;
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

    boolean refresh=false;
    private AdaptadorNota AdaptadorNota;


    SwipeRefreshLayout SwipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_notas);

        ListaNotas= new ArrayList<>();
        rq= Volley.newRequestQueue(this);
        CargarNotas(refresh);

        Rv1=findViewById(R.id.Rv1);
        LinearLayoutManager Linearlayout =new LinearLayoutManager(this);
        Rv1.setLayoutManager(Linearlayout);

        AdaptadorNota=new AdaptadorNota();
        Rv1.setAdapter(AdaptadorNota);

        SwipeRefreshLayout= findViewById(R.id.swipeRefreshLayout);

        SwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh=true;
                CargarNotas(refresh);
                SwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void CargarNotas(boolean refresh) {
        if(refresh==true)
        {
            ListaNotas.clear();
            AdaptadorNota.notifyDataSetChanged();
        }

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
                        int Id=Integer.parseInt(Onotas.getString("Id"));
                        Nota nota= new Nota(Titulo, Contenido,Id);

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
        refresh=false;
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
                new AlertDialog.Builder(this)
                        .setTitle("¿Cerrrar Sesion?")
                        .setMessage("¿Esta seguro que desea cerrar sesion?")
                        .setNegativeButton("No", null)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences prefs = getSharedPreferences("shared_login_data",getApplicationContext().MODE_PRIVATE);
                                prefs.edit().clear().commit();
                                Intent CerrarSesion=new Intent(getApplicationContext(),LoginActivity.class);
                                BorrarHistorialActivitys(CerrarSesion);
                                startActivity(CerrarSesion);
                            }
                        }).create().show();
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
            Nota nota= ListaNotas.get(position);
            holder.imprimir(position);
            holder.nota=nota;
        }

        @Override
        public int getItemCount() {
            return ListaNotas.size();
        }

        class AdaptadorNotaHolder extends RecyclerView.ViewHolder
        {
            TextView tvTitulo, tvContenido;
            Nota nota;
            public AdaptadorNotaHolder(@NonNull View itemView) {
                super(itemView);
                tvTitulo=itemView.findViewById(R.id.tvTitulo);
                tvContenido=itemView.findViewById(R.id.tvContenido);
                    //OnClickListener de modificar y eliminar nota
                itemView.findViewById(R.id.CardView).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MisNotasActivity.this,EditarNotaActivity.class);
                        intent.putExtra("IdNota",nota.getId());
                        intent.putExtra("Titulo",nota.getTitulo());
                        intent.putExtra("Contenido",nota.getContenido());
                        startActivity(intent);
                    }
                });
            }
            public void imprimir(int position) {
              tvTitulo.setText("Titulo: "+ListaNotas.get(position).getTitulo());
              tvContenido.setText("Contenido: "+ListaNotas.get(position).getContenido());
            }
        }
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("¿Salir?")
                .setMessage("¿Estas seguro que deseas salir?")
                .setNegativeButton("No", null)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences prefs = getSharedPreferences("shared_login_data",getApplicationContext().MODE_PRIVATE);
                        prefs.edit().clear().commit();
                        finish(); //finaliza Activity.
                    }
                }).create().show();
    }
}
