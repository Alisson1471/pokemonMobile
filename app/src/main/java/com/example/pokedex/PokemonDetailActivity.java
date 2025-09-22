package com.example.pokedex;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.pokedex.adapter.TypesAdapter;
import com.example.pokedex.client.PokemonAPI;
import com.example.pokedex.model.PokemonResponse;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailActivity extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;

    private Retrofit retrofit;

    private RecyclerView typesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Configurar o ViewPager e o SmartTabLayout
        smartTabLayout = findViewById(R.id.smartTabLayout);
        viewPager = findViewById(R.id.viewpager);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                        .add("Base stats", BaseStatsFragment.class)
                        .add("Evolution", EvolutionFragment.class)
                        .add("Maps", MapsFragment.class)
                        .create()
        );

        viewPager.setAdapter( adapter );
        smartTabLayout.setViewPager( viewPager );

        //montar o RecycleView
        typesRV = findViewById(R.id.TypesRV2);
        typesRV.setLayoutManager(new GridLayoutManager(this, 2));

        // Receber os dados do Pokemon
        String pokemonName = getIntent().getStringExtra("pokemon_name");
        int pokemonId = getIntent().getIntExtra("pokemon_id", 0);
        int backgroundColor = getIntent().getIntExtra("pokemon_background_color", 0);

        ((TextView) findViewById(R.id.txtPokemonName)).setText(pokemonName);
        ((TextView) findViewById(R.id.txtPokemonId)).setText("#" + pokemonId);
        ((View) findViewById(R.id.viewBackground)).setBackgroundColor(backgroundColor);

        chamaAPI_buscaPokemon(pokemonName);
    }

    private void chamaAPI_buscaPokemon(String nome) {
        //Definir a URL
        String url = "https://pokemon-kwqr.onrender.com/";

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
        Call<PokemonResponse> call = pokemonAPI.buscaPokemon(nome);

        //executar a chamada do endpoint
        call.enqueue(new Callback<PokemonResponse>() {
            @Override
            public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonResponse pokemon = response.body();

                    // carregar dados do Pokémon
                    ImageView imgPokemon = findViewById(R.id.imgPokemon);
                    Glide.with(PokemonDetailActivity.this)
                            .load(pokemon.getImageUrl())
                            .centerCrop()
                            .into(imgPokemon);

                    if (pokemon.getTypes() != null) {
                        typesRV.setAdapter(new TypesAdapter(pokemon.getTypes()));
                    }
                } else {
                    Toast.makeText(PokemonDetailActivity.this,
                            "Erro ao buscar detalhes do Pokémon",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonResponse> call, Throwable throwable) {
                throwable.printStackTrace();
                Toast.makeText(PokemonDetailActivity.this, "Erro ao carregar dados do pokemon", Toast.LENGTH_SHORT).show();
            }
        });

    }
}