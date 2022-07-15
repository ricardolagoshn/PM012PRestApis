package com.aplicacion.pm012prestapis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ActivityCrear extends AppCompatActivity {

    static final int REQUESTCAMERA = 100;
    static final int TAKEPHOTO = 101;

    ImageView ObjectImage;
    String currentPhotoPath;

    Button btncrear, btnfoto;
    List<Message> MessageList;
    EditText nombres, apellidos, edad , telefono, direccion, cedula;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear);

        MessageList = new ArrayList<>();

        btnfoto = (Button) findViewById(R.id.bntfoto);
        btncrear = (Button) findViewById(R.id.btncrear);
        nombres = (EditText) findViewById(R.id.txtnombres);
        apellidos = (EditText) findViewById(R.id.apellidos);
        edad = (EditText) findViewById(R.id.edad);
        telefono = (EditText) findViewById(R.id.telefono);
        direccion = (EditText) findViewById(R.id.direccion);
        cedula = (EditText) findViewById(R.id.cedula);
        ObjectImage = (ImageView) findViewById(R.id.imageView) ;

        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CrearEmple();
            }
        });

        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OtorgarPermisos();
            }
        });
    }

    private void OtorgarPermisos()
    {
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.CAMERA }, REQUESTCAMERA);
        }
        else
        {
            dispatchTakePictureIntent();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.aplicacion.pm012prestapis.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, TAKEPHOTO);
            }
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == TAKEPHOTO && resultCode == RESULT_OK)
        {
            /*
            Bundle extraerfoto = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extraerfoto.get("data");
            ObjectImage.setImageBitmap(imageBitmap);
             */

            File foto = new File(currentPhotoPath);
            ObjectImage.setImageURI(Uri.fromFile(foto));

        }
    }


    public static String ImageToBase64(String filePath){
        Bitmap bmp = null;
        ByteArrayOutputStream bos = null;
        byte[] bt = null;
        String encodeString = null;
        try
        {
            bmp = BitmapFactory.decodeFile(filePath);
            bos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            bt = bos.toByteArray();
            encodeString = Base64.encodeToString(bt, Base64.DEFAULT);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return encodeString;
    }

    private void CrearEmple()
    {
        JSONObject JSONEmple;
        String PostUrl = Configuracion.EndpointCreateEmple;

        HashMap<String, String> parametros = new HashMap<>();
        parametros.put(getResources().getString(R.string.nombres), nombres.getText().toString());
        parametros.put(getResources().getString(R.string.apellidos),apellidos.getText().toString());
        parametros.put(Configuracion.FieldEdad,edad.getText().toString());
        parametros.put("telefono", telefono.getText().toString());
        parametros.put("cedula", cedula.getText().toString());
        parametros.put("direccion",direccion.getText().toString());
        parametros.put("foto",ImageToBase64(currentPhotoPath));

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        JSONEmple = new JSONObject(parametros);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, PostUrl, JSONEmple, new Response.Listener<JSONObject>() {
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