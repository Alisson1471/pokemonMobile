package com.example.pokedex.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokedex.R;
import com.example.pokedex.model.PokemonResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.TypesAdapterViewHolder> {

    private List<String> types;

    public static final Map<String, String> TYPE_COLORS = new HashMap<String, String>() {{
        put("fire", "#FF6B6B");       // vermelho mais vibrante
        put("water", "#4D9EFF");      // azul vivo
        put("grass", "#4BD37B");      // verde mais intenso
        put("electric", "#FFD93D");   // amarelo vivo
        put("ice", "#6FE6F2");        // azul claro brilhante
        put("fighting", "#FF5C57");   // vermelho/laranja mais vibrante
        put("poison", "#C468FF");     // roxo mais saturado
        put("ground", "#EFC75E");     // bege mais vivo
        put("flying", "#A4A9FF");     // azul lilás
        put("psychic", "#FF6AD5");    // rosa vivo
        put("bug", "#BEE544");        // verde amarelado vivo
        put("rock", "#D2B45B");       // marrom dourado
        put("ghost", "#8A6FFF");      // roxo azulado
        put("dragon", "#7350FF");     // roxo vivo
        put("dark", "#6E5A4F");       // marrom escuro
        put("steel", "#C0C0D0");      // cinza claro com brilho
        put("fairy", "#FF92D0");      // rosa vibrante
        put("normal", "#CFC77B");     // bege mais claro
    }};


    public TypesAdapter(List<String> types) {
        this.types = types;
    }


    @NonNull
    @Override
    public TypesAdapter.TypesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_type, parent, false);
        TypesAdapter.TypesAdapterViewHolder holder = new TypesAdapter.TypesAdapterViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TypesAdapter.TypesAdapterViewHolder holder, int position) {
        String typeName = types.get(position);
        holder.type.setText(typeName);

        //lógica de cores
        String colorHex = TYPE_COLORS.get(typeName.toLowerCase());
        if (colorHex != null) {
            int color = Color.parseColor(colorHex);
            int lightColor = lightenColor(color, 0.6f); // deixa 60% mais claro
            holder.cardView.setCardBackgroundColor(lightColor);
        }
    }

    @Override
    public int getItemCount() {
        return types != null ? types.size() : 0;
    }

    public static int lightenColor(int color, float factor) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);

        r = (int) (r + (255 - r) * factor);
        g = (int) (g + (255 - g) * factor);
        b = (int) (b + (255 - b) * factor);

        return Color.rgb(r, g, b);
    }

    public static class TypesAdapterViewHolder extends RecyclerView.ViewHolder {

        TextView type;
        CardView cardView;

        public TypesAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.type = itemView.findViewById(R.id.txtType);
            this.cardView = itemView.findViewById(R.id.cardType);
        }
    }
}
