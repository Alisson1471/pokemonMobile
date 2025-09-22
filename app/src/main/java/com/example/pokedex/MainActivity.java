package com.example.pokedex;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.adapter.PokemonsAdapter;
import com.example.pokedex.adapter.TypesAdapter;
import com.example.pokedex.client.PokemonAPI;
import com.example.pokedex.model.PokemonsResponse;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;

    private RecyclerView pokemonsRV;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //montar o RecycleView
        pokemonsRV = findViewById(R.id.pokemonsRV);
        pokemonsRV.setLayoutManager(new GridLayoutManager(this, 2));

        // Receber os dados do Intent
        int offset = getIntent().getIntExtra("offset", 0);
        int limit = getIntent().getIntExtra("limit", 151);

       progressBar = findViewById(R.id.progressBar);


        //chamar a API
        chamaAPI_GetPokemons(offset, limit);

    }

    private void chamaAPI_GetPokemons(int offset, int limit) {
        progressBar.setVisibility(View.VISIBLE); // mostra o loading

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
        Call<List<PokemonsResponse>> call = pokemonAPI.listaPokemons(offset, limit);

        //executar a chamada do endpoint
        call.enqueue(new Callback<List<PokemonsResponse>>() {
            @Override
            public void onResponse(Call<List<PokemonsResponse>> call, Response<List<PokemonsResponse>> response) {
                progressBar.setVisibility(View.GONE); // esconde o loading
                List<PokemonsResponse> pokemons = response.body();

                //carregar o ADAPTER do RecycleView
                pokemonsRV.setAdapter(new PokemonsAdapter(pokemons, pokemon -> {
                    int lightenColor = TypesAdapter.lightenColor(Color.rgb(0, 255, 0), 0.2f);

                    String typeName = pokemon.getTypes().get(0);
                    String colorHex = TypesAdapter.TYPE_COLORS.get(typeName.toLowerCase());
                    if (colorHex != null) {
                        int color = Color.parseColor(colorHex);
                        lightenColor = TypesAdapter.lightenColor(color, 0.2f);
                    }

                    Intent intent = new Intent(MainActivity.this, PokemonDetailActivity.class);
                    intent.putExtra("pokemon_id", pokemon.getId());
                    intent.putExtra("pokemon_name", pokemon.getName());
                    intent.putExtra("pokemon_background_color", lightenColor);
                    startActivity(intent);
                }));
            }

            @Override
            public void onFailure(Call<List<PokemonsResponse>> call, Throwable throwable) {
                progressBar.setVisibility(View.GONE); // esconde o loading mesmo em erro
                throwable.printStackTrace();
                Toast.makeText(MainActivity.this, "Erro ao carregar pokemons", Toast.LENGTH_SHORT).show();
            }
        });

    }
}