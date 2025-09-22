package com.example.pokedex;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.adapter.GenerationAdapter;
import com.example.pokedex.adapter.PokemonsAdapter;
import com.example.pokedex.adapter.TypesAdapter;
import com.example.pokedex.client.PokemonAPI;
import com.example.pokedex.model.GenerationResponse;
import com.example.pokedex.model.PokemonsResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GenerationsActivity extends AppCompatActivity {

    private RecyclerView generationsRV;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_generations);

        //montar o RecycleView
        generationsRV = findViewById(R.id.generationRV);
        generationsRV.setLayoutManager(new GridLayoutManager(this, 2));

        //chamar a API
        chamaAPI_GetPokemons();

    }

    private void chamaAPI_GetPokemons() {
        //Definir a URL
        String url = "https://pokemon-kwqr.onrender.com/";
//        String url = "http://10.0.2.2:8080/";

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS) // tempo para conectar
                .readTimeout(30, TimeUnit.SECONDS)    // tempo para esperar resposta
                .writeTimeout(20, TimeUnit.SECONDS)   // tempo para enviar dados
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Criar a interface para a API
        PokemonAPI pokemonAPI = retrofit.create(PokemonAPI.class);
        Call<List<GenerationResponse>> call = pokemonAPI.listaGeracoes();

        //executar a chamada do endpoint
        call.enqueue(new Callback<List<GenerationResponse>>() {
            @Override
            public void onResponse(Call<List<GenerationResponse>> call, Response<List<GenerationResponse>> response) {
                Toast.makeText(GenerationsActivity.this, "Gerações carregadas com sucesso", Toast.LENGTH_SHORT).show();
                System.out.println(response.body());

                if (response.isSuccessful() && response.body() != null) {
                    List<GenerationResponse> generations = response.body();

                    //carregar o ADAPTER do RecycleView
                    generationsRV.setAdapter(new GenerationAdapter(generations, generation -> {
                        Intent intent = new Intent(GenerationsActivity.this, SplashInicialActivity.class);
                        intent.putExtra("geracao_nome", generation.getNome());
                        intent.putExtra("offset", generation.getOffset());
                        intent.putExtra("limit", generation.getLimit());
                        startActivity(intent);
                    }));
                } else {
                    Toast.makeText(GenerationsActivity.this, "Resposta inválida da API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GenerationResponse>> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(GenerationsActivity.this, "Erro ao carregar pokemons", Toast.LENGTH_SHORT).show();
            }
        });

    }
}