package com.example.pokemonultimate.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokemonultimate.HomePageActivity;
import com.example.pokemonultimate.Model.Pokemon;
import com.example.pokemonultimate.R;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.MyViewHolder>{

    Context mContext;
    List<Pokemon> pokemonList;


    @SuppressLint("NotifyDataSetChanged")
    public PokemonListAdapter(Context mContext, List<Pokemon> pokemonList){
        this.mContext   = mContext;
        this.pokemonList      = pokemonList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.pokemon_content, parent, false );
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Pokemon pokemonDetails = pokemonList.get(position);
        holder.name.setText(pokemonDetails.getName());
        Drawable drawable = ContextCompat.getDrawable(mContext, pokemonDetails.getImage());
        holder.imageThumbnail.setImageDrawable(drawable);

        if (pokemonDetails.getType().equals("Grass")){
            holder.linearLayout.setBackgroundColor(Color.GREEN);
        }else if (pokemonDetails.getType().equals("Electric")){
            holder.linearLayout.setBackgroundColor(Color.YELLOW);
        }else{
            holder.linearLayout.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent detailsPage = new Intent(mContext, HomePageActivity.class);
                detailsPage.putExtra("id", pokemonDetails.getId());
                mContext.startActivity(detailsPage);

            }
        });
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView imageThumbnail;
        LinearLayout linearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            imageThumbnail = itemView.findViewById(R.id.imageThumbnail);
            linearLayout = itemView.findViewById(R.id.linearLayout);



        }
    }
}
