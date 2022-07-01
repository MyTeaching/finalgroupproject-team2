package com.example.team2finalprojectpokedex;

import android.annotation.SuppressLint;
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
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();

        assert bundle != null;
        poke = bundle.getParcelable("POKEMON");
        connectViews();
        setTextValues();
        setImages();
        Log.d("SecondFrag","Second frag bundle: " +poke.toString());


        fav.setOnClickListener(v -> setFav());
        binding.buttonSecond.setOnClickListener(view1 -> {
            Bundle bundle1 = new Bundle();
            bundle1.putParcelable("POKEMON" , poke);
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment, bundle1);

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void connectViews(){
        fav = requireView().findViewById(R.id.iv_fav2);
        pokeBackSprite = requireView().findViewById(R.id.iv_poke_sprite_back);
        type1 = requireView().findViewById(R.id.tv_type_1_b);
        type2 = requireView().findViewById(R.id.tv_type_2_b);
        weight = requireView().findViewById(R.id.tv_weight_b);
        height = requireView().findViewById(R.id.tv_height_b);
        name = requireView().findViewById(R.id.tv_poke_name_b);
        desc = requireView().findViewById(R.id.description);
        cardView = requireView().findViewById(R.id.view2_b);
        pokeNum = requireView().findViewById(R.id.tv_poke_num_b);
    }
    @SuppressLint("DefaultLocale")
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