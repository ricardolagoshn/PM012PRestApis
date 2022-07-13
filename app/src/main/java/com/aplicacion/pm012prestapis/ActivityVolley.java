package com.aplicacion.pm012prestapis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ActivityVolley extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley);

        obtenerBooks();
    }

    private void obtenerBooks()
    {
        RequestQueue cola = Volley.newRequestQueue(this);
        String endpointurl = "https://jsonplaceholder.typicode.com/posts";

        StringRequest consulta = new StringRequest(Request.Method.GET, endpointurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                Log.i("Mensaje de Respuesta ", response.substring(1,1000));
;
            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        });


    }
}