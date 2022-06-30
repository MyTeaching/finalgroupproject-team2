package com.example.team2finalprojectpokedex;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.team2finalprojectpokedex.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    Pokemon poke;
    ImageView fav, pokeBackSprite;
    TextView weight, height, name, desc, type1,type2, pokeNum;
    CardView cardView;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        poke = bundle.getParcelable("POKEMON");
        connectViews();
        setTextValues();
        setImages();
        Log.d("SecondFrag","Second frag bundle: " +poke.toString());


        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFav();
            }
        });
        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("POKEMON" , poke);
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment,bundle);

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void connectViews(){
        fav = getView().findViewById(R.id.iv_fav2);
        pokeBackSprite = getView().findViewById(R.id.iv_poke_sprite_back);
        type1 = getView().findViewById(R.id.tv_type_1_b);
        type2 = getView().findViewById(R.id.tv_type_2_b);
        weight = getView().findViewById(R.id.tv_weight_b);
        height = getView().findViewById(R.id.tv_height_b);
        name = getView().findViewById(R.id.tv_poke_name_b);
        desc = getView().findViewById(R.id.description);
        cardView = getView().findViewById(R.id.view2_b);
        pokeNum = getView().findViewById(R.id.tv_poke_num_b);
    }
    public void setTextValues(){
//        connectViews();
        weight.setText(String.format("%.2f kg",poke.getWeight() * .1));
//        Log.d(TAG, "Value of Weight: " + poke.getWeight());
        height.setText(String.format("%.2f  m",poke.getHeight() * .1));
//        Log.d(TAG, "Value of Height: " + poke.getHeight());
        name.setText(String.valueOf(poke.getName()));
//        Log.d(TAG, "Value of name: " + poke.getName());
        desc.setText(poke.getDescription());
        type1.setText(poke.getType1().toUpperCase());
        type2.setText(poke.getType2().toUpperCase());
        pokeNum.setText(String.format("No. %d",poke.getId()));
    }
    public void setImages(){
        Glide.with(this)
                .load((poke.isFavorite() ? R.drawable.ic_baseline_favorite_24:R.drawable.ic_baseline_favorite_border_24))
                .override(48,48)
                .into(fav);
        Glide.with(this)
                .load(poke.getSpriteURLBack())
                .into(pokeBackSprite);
    }

    public void setFav(){
        poke.setFavorite(!poke.isFavorite());
        Glide.with(this)
                .load((poke.isFavorite() ? R.drawable.ic_baseline_favorite_24:R.drawable.ic_baseline_favorite_border_24))
                .override(48,48)
                .into(fav);
    }

}