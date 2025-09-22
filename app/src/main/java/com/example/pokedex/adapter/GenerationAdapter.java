package com.example.pokedex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.pokedex.R;
import com.example.pokedex.model.GenerationResponse;
import com.example.pokedex.model.PokemonsResponse;

import java.util.List;

public class GenerationAdapter extends RecyclerView.Adapter<GenerationAdapter.GenerationViewHolder>{

    private List<GenerationResponse> generations;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(GenerationResponse generationResponse);
    }

    public GenerationAdapter(List<GenerationResponse> generations) {
        this.generations = generations;
    }

    public GenerationAdapter(List<GenerationResponse> generations, OnItemClickListener listener) {
        this.generations = generations;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GenerationAdapter.GenerationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generation, parent, false);
        GenerationAdapter.GenerationViewHolder holder = new GenerationAdapter.GenerationViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GenerationAdapter.GenerationViewHolder holder, int position) {
        GenerationResponse generation = generations.get(position);

        holder.tvGenerationName.setText(generation.getNome());
        Glide.with(holder.imgGeneration.getContext())
                .load(generation.getImageUrl())
                .into(holder.imgGeneration);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(generation);
            }
        });

    }

    @Override
    public int getItemCount() {
        return generations != null ? generations.size() : 9;
    }

    public static class GenerationViewHolder extends RecyclerView.ViewHolder {

        ImageView imgGeneration;
        TextView tvGenerationName;

        public GenerationViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGeneration = itemView.findViewById(R.id.imgGeneration);
            tvGenerationName = itemView.findViewById(R.id.tvGenerationName);
        }
    }
}
