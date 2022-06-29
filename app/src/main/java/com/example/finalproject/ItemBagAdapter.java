package com.example.finalproject;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemBagAdapter extends RecyclerView.Adapter<ItemBagAdapter.ViewHolder> {
    private final RecyclerViewInterface viewInterface;
    Context context;
    ArrayList<Pokemon> pokemonList;

    public ItemBagAdapter(Context context, ArrayList<Pokemon> pokemonList, RecyclerViewInterface viewInterface) {
        this.viewInterface = viewInterface;
        this.context = context;
        this.pokemonList = pokemonList;
    }

    @NonNull
    @Override
    public ItemBagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pokemon_view, parent, false);
        return new ViewHolder(view, viewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemBagAdapter.ViewHolder holder, int position) {
        ImageView imageView = holder.pokemonId;
        Glide.with(context).load(pokemonList.get(position).getSpriteURL()).into(imageView);
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pokemonId;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface viewInterface) {
            super(itemView);
            pokemonId = itemView.findViewById(R.id.pokemonId);
            itemView.setOnClickListener(v -> {
                if (viewInterface != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        viewInterface.onItemClick(position);
                    }
                }
            });
        }
    }
}
