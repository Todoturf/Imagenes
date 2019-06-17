package com.example.imagenes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static com.example.imagenes.Datos.listaImagenes;

public class MostrarImagenActivity extends AppCompatActivity {

    Button btnMostrar;
    ImageView imgMostrar;

    int idImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_imagen);

        btnMostrar = findViewById(R.id.btnMostrar);
        imgMostrar = findViewById(R.id.imgMostrar);

        idImage = 0;

        btnMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idImage == 0){
                    imgMostrar.setImageBitmap(listaImagenes.get(0));
                    idImage = 1;
                }else{
                    imgMostrar.setImageBitmap(listaImagenes.get(1));
                    idImage = 0;
                }
            }
        });

    }
}
