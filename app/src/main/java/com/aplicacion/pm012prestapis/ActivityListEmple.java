package com.aplicacion.pm012prestapis;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.List;

public class ActivityListEmple extends AppCompatActivity {
    
    ListView ListEmple;
    List<Empleado> empleadoList;
    ArrayList<String> arrayEmple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_emple);

        ListEmple = (ListView) findViewById(R.id.listemple);
        empleadoList = new ArrayList<>();
        arrayEmple = new ArrayList<String>();

        ConsumirApi();
        
    }

    private void ConsumirApi()
    {
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Configuracion.EndpointListEmple,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray EmpleArray = jsonObject.getJSONArray("empleado");

                            for(int i=0; i < EmpleArray.length(); i++)
                            {
                                JSONObject RowEmple = EmpleArray.getJSONObject(i);
                                Empleado emple = new Empleado(RowEmple.getInt("id"),
                                        RowEmple.getString("nombres"),
                                        RowEmple.getString("apellidos"),
                                        RowEmple.getInt("edad"),
                                        RowEmple.getString("telefono"),
                                        RowEmple.getString("direccion"),
                                        RowEmple.getString("cedula")
                                        );

                                empleadoList.add(emple);

                                arrayEmple.add(emple.getNombres() + " "+ emple.getApellidos());
                            }

                            ArrayAdapter adp = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,arrayEmple);
                            ListEmple.setAdapter(adp);

                        }
                        catch (JSONException ex)
                        {
                            ex.toString();
                        }

                    }
                }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {


            }
        });

        queue.add(stringRequest);
    }
}