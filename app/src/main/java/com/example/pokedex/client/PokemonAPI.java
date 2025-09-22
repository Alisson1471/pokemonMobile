package com.example.pokedex.client;

import com.example.pokedex.model.GenerationResponse;
import com.example.pokedex.model.PokemonResponse;
import com.example.pokedex.model.PokemonsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonAPI {

    @GET("test-pokemon/busca-pokemons")
    Call<List<PokemonsResponse>> listaPokemons(@Query("offset") int offset, @Query("limit") int limit);

    @GET("test-pokemon/busca-pokemon-mobile/{name}")
    Call<PokemonResponse> buscaPokemon(@Path("name") String name);

    @GET("test-pokemon/generation/listAll")
    Call<List<GenerationResponse>> listaGeracoes();

}
