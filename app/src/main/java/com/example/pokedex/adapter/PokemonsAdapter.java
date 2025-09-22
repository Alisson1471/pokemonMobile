package com.example.pokedex.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex.R;
import com.example.pokedex.model.PokemonsResponse;

import java.util.List;

public class PokemonsAdapter extends RecyclerView.Adapter<PokemonsAdapter.PokemonsViewHolder> {

    private List<PokemonsResponse> pokemons;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PokemonsResponse pokemon);
    }

    public PokemonsAdapter(List<PokemonsResponse> pokemons) {
        this.pokemons = pokemons;
    }

    public PokemonsAdapter(List<PokemonsResponse> pokemons, OnItemClickListener listener) {
        this.pokemons = pokemons;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PokemonsAdapter.PokemonsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_basico, parent, false);
        PokemonsViewHolder holder = new PokemonsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonsAdapter.PokemonsViewHolder holder, int position) {
        PokemonsResponse pokemon = pokemons.get(position);

        holder.txtNome.setText(pokemon.getName());
        Glide.with(holder.imgPokemon.getContext())
                .load(pokemon.getImageUrl())
                .centerCrop()
                .into(holder.imgPokemon);

        String typeName = pokemon.getTypes().get(0);
        String colorHex = TypesAdapter.TYPE_COLORS.get(typeName.toLowerCase());
        if (colorHex != null) {
            int color = Color.parseColor(colorHex);
            int lightenColor = TypesAdapter.lightenColor(color, 0.2f);
            ((CardView) holder.itemView).setCardBackgroundColor(lightenColor);
        }

        holder.typesRV.setLayoutManager(
                new LinearLayoutManager(holder.itemView.getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        holder.typesRV.setAdapter(new TypesAdapter(pokemon.getTypes()));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(pokemon);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pokemons != null ? pokemons.size() : 50;
    }

    public class PokemonsViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPokemon;
        TextView txtNome;
        RecyclerView typesRV;

        public PokemonsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imgPokemon = itemView.findViewById(R.id.imgPokemon);
            this.txtNome = itemView.findViewById(R.id.txtPokemonName);
            this.typesRV = itemView.findViewById(R.id.TypesRV);
        }
    }
}
