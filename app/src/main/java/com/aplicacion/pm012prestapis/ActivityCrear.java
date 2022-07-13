package com.aplicacion.pm012prestapis;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ActivityCrear extends AppCompatActivity {

    Button btncrear;
    List<Message> MessageList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        MessageList = new ArrayList<>();

        btncrear = (Button) findViewById(R.id.btncrear);

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearEmple();
            }
        });
    }

    private void CrearEmple()
    {
        String PostUrl = Configuracion.EndpointCreateEmple;

        HashMap<String, String> parametros = new HashMap<>();
        parametros.put(getResources().getString(R.string.nombres),"Jose Miguel");
        parametros.put(getResources().getString(R.string.apellidos),"Perez");
        parametros.put(Configuracion.FieldEdad,"24");
        parametros.put("telefono","99001134");
        parametros.put("cedula","0501199904325");
        parametros.put("direccion","COLONIA EL ALTIPLANO");

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, PostUrl, new JSONObject(parametros), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response)
            {
                try
                {
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i<= jsonArray.length(); i++)
                    {
                        JSONObject msg = jsonArray.getJSONObject(i);

                        if(msg.getString(getResources().getString(R.string.codigo)).equals(getResources().getString(R.string.StatusSuccess)))
                        {
                            Message mensaje = new Message(
                                       msg.getString("CODIGO"),
                                       msg.getString("MESSAGE"));

                            MessageList.add(mensaje);
                        }
                        else
                        {
                            Message mensaje = new Message(
                                    msg.getString("CODIGO"),
                                    msg.getString("MESSAGE"));

                            MessageList.add(mensaje);
                        }
                    }

                    if(MessageList.size() > 0)
                    {
                        final AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());

                        alerta.setTitle(MessageList.get(0).message);
                        alerta.setIcon(R.mipmap.ic_launcher);
                        alerta.setPositiveButton(R.string.StatusSuccessText, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        alerta.show();
                    }
                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);

    }
}