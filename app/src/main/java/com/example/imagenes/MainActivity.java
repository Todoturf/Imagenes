package com.example.imagenes;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.imagenes.Datos.listaImagenes;

public class MainActivity extends AppCompatActivity
{
    Button btnCamara;
    Button btnSiguiente;
    Button btnGaleria;
    ImageView imgResultado;

    final static int RESULTADO_GALERIA = 2;
    final static int RESULTADO_CAMARA = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCamara = findViewById(R.id.btnCamara);
        btnSiguiente = findViewById(R.id.btnsiguiente);
        btnGaleria = findViewById(R.id.btnGaleria);
        imgResultado = findViewById(R.id.imgResultado);

        btnGaleria.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RESULTADO_GALERIA);
            }
        });

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(intent, RESULTADO_CAMARA);
                }
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MostrarImagenActivity.class);
                startActivity(intent);
            }
        });
        {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RESULTADO_GALERIA)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                ponerFoto(data.getDataString());
            }
            else
            {
                Toast.makeText(MainActivity.this, "La foto no se ha cargado", Toast.LENGTH_SHORT).show();
            }
        }
        else
        if (requestCode == RESULTADO_CAMARA)
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgResultado.setImageBitmap(imageBitmap);
            saveImage(imageBitmap);

            if (resultCode == Activity.RESULT_OK)
            {
                ponerFoto(data.getDataString());
            }
            else
            {
                Toast.makeText(MainActivity.this, "La foto no se ha cargado", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ponerFoto(String uri){
        if(uri != null && !uri.isEmpty() && !uri.equals("null")){
            Uri imageUri = Uri.parse(uri);
            imgResultado.setImageURI(imageUri);
            try {
                saveImage(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveImage(Bitmap bitmap){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "Image_" + timeStamp + "jpg";
        File file = new File(directory, fileName);
        if(!file.exists()){
            Log.d("pathImage", file.toString());
            FileOutputStream fos = null;
            try{
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
                listaImagenes.add(bitmap);
                Log.d("pathImage", file.toString());
            }catch (java.io.IOException e){
                e.printStackTrace();
            }
        }
    }
}
