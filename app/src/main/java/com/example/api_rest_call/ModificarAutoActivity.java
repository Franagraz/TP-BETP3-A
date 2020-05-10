package com.example.api_rest_call;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ModificarAutoActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_auto);
        setTitle("Vehículo");
        Bundle b = getIntent().getExtras();

        final String id = b.getString("id");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://us-central1-be-tp3-a.cloudfunctions.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        final AutoService autoService = retrofit.create(AutoService.class);

        Call<Auto> http_call = autoService.getAuto(id);

        http_call.enqueue(new Callback<Auto>() {
            @Override
            public void onResponse(Call<Auto> call, Response<Auto> response) {
                Auto auto = response.body();

                TextView idAuto = (TextView) findViewById(R.id.id_auto);
                idAuto.setText(auto.getId());
                TextView marca = (TextView) findViewById(R.id.marca_auto);
                marca.setText(auto.getMarca());
                TextView modelo = (TextView) findViewById(R.id.modelo_auto);
                modelo.setText(auto.getModelo());

                Toast.makeText(
                        ModificarAutoActivity.this,
                        auto.getMarca(),
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Auto> call, Throwable t) {
                Toast.makeText(ModificarAutoActivity.this, "Hubo un error con la llamada a la API", Toast.LENGTH_LONG);


            }
        });

        //Modificar auto

        final Button buttonEditar = findViewById(R.id.buttonEditar);
        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String marca = ((TextInputEditText) findViewById(R.id.marca_auto)).getText().toString();
                String modelo = ((TextInputEditText) findViewById(R.id.modelo_auto)).getText().toString();
                Call<Void> http_call = autoService.modificarAuto(
                        id,
                        marca,
                        modelo
                );
                Log.i("DATOS", id+"-"+marca+"-"+modelo);
                http_call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.i("Ok","Actualizado correctamente");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable e) {
                        Log.i("ERROR", e.getMessage());
                    }
                });
            }

        });

        //Eliminar auto

        final Button buttonEliminar = findViewById(R.id.buttonEliminar);
        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Call<Void> http_call = autoService.eliminarAuto(id);

                http_call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Log.i("Eliminar", "Registro eliminado");
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.i("RROR", t.getMessage());
                    }
                });
            }
        });


        final Button buttonVolver = findViewById(R.id.buttonVolver);
        buttonVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });




    }

}
