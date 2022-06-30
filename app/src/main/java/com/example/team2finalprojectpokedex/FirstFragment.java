package com.example.team2finalprojectpokedex;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.team2finalprojectpokedex.databinding.FragmentFirstBinding;

import java.util.Objects;

public class FirstFragment extends Fragment {
    static  final String TAG = "FirstFrag";
    private FragmentFirstBinding binding;
    Pokemon poke;
    ImageView fav, pokeSprite;
    TextView type1, type2, weight, height, name, hp, atk, def, spAtk, spDef, speed, pokeNum;
    ProgressBar barHp, barAtk, barDef, barSpAtk, barSpDef, barSpeed;
    CardView cardView;
    static final int MAX = 255;
//    public Context context;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);

        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        poke = bundle.getParcelable("POKEMON");
        Log.d("FIRSTFRAGMENT", poke.toString());
        connectViews();
        setTextValues();
        setImages();
//        determineMaxStat();
        determineProgress();
        fav.setOnClickListener(v -> setFav());
        binding.buttonFirst.setOnClickListener(view1 -> {
            Bundle bundle1 = new Bundle();
            bundle1.putParcelable("POKEMON", poke);
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle1);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void connectViews(){
        fav = requireView().findViewById(R.id.iv_fav);
        pokeSprite = requireView().findViewById(R.id.iv_poke_sprite);
        type1 = requireView().findViewById(R.id.tv_type_1);
        type2 = requireView().findViewById(R.id.tv_type_2);
        weight = requireView().findViewById(R.id.tv_weight);
        height = requireView().findViewById(R.id.tv_height);
        name = requireView().findViewById(R.id.tv_poke_name);
        hp = requireView().findViewById(R.id.tv_hp_amount);
        atk = requireView().findViewById(R.id.tv_atk_amount);
        def = requireView().findViewById(R.id.tv_def_amount);
        spAtk = requireView().findViewById(R.id.tv_spatk_amount);
        spDef = requireView().findViewById(R.id.tv_spdef_amount);
        speed = requireView().findViewById(R.id.tv_spd_amount);
        barHp = requireView().findViewById(R.id.bar_hp);
        barAtk = requireView().findViewById(R.id.bar_atk);
        barDef = requireView().findViewById(R.id.bar_def);
        barSpAtk = requireView().findViewById(R.id.bar_sp_atk);
        barSpDef = requireView().findViewById(R.id.bar_sp_def);
        barSpeed = requireView().findViewById(R.id.bar_speed);
        cardView = requireView().findViewById(R.id.view2);
        pokeNum = requireView().findViewById(R.id.tv_poke_num);
    }

    @SuppressLint("DefaultLocale")
    public void setTextValues(){
        hp.setText(String.valueOf(poke.getHp()));
        Log.d(TAG, "Value of Hp: " + poke.getHp());
        atk.setText(String.valueOf(poke.getAtk()));
        Log.d(TAG, "Value of Atk: " + poke.getAtk());
        def.setText(String.valueOf(poke.getDef()));
        Log.d(TAG, "Value of Def: "  + poke.getDef());
        spAtk.setText(String.valueOf(poke.getSpAtk()));
        Log.d(TAG, "Value of SpAtk: " + poke.getSpAtk());
        spDef.setText(String.valueOf(poke.getSpDef()));
        Log.d(TAG, "Value of SpDef: " + poke.getSpDef());
        speed.setText(String.valueOf(poke.getSpeed()));
        Log.d(TAG, "Value of Speed: " + poke.getSpeed());
        weight.setText(String.format("%.2f kg",poke.getWeight() * .1));
        Log.d(TAG, "Value of Weight: " + poke.getWeight());
        height.setText(String.format("%.2f  m",poke.getHeight() * .1));
        Log.d(TAG, "Value of Height: " + poke.getHeight());
        name.setText(String.valueOf(poke.getName()));
        Log.d(TAG, "Value of name: " + poke.getName());
        type1.setText(poke.getType1().toUpperCase());
        type2.setText(poke.getType2().toUpperCase());
        pokeNum.setText(String.format("No. %d",poke.getId()));
    }
    public void setImages(){
        Glide.with(this)
                .load((poke.favorite ? R.drawable.ic_baseline_favorite_24:R.drawable.ic_baseline_favorite_border_24))
                .override(48,48)
                .into(fav);
        Glide.with(this)
                .load(poke.spriteURL)
                .into(pokeSprite);
    }


    private void determineProgress(){

        double hpProg, atkProg, defProg, spAtkProg, spDefProg, spdProg;
        hpProg = ((double)poke.getHp()/MAX) * 100;
        Log.d(TAG, "Value of hpProg: " + hpProg);
        atkProg = ((double)poke.getAtk()/MAX) * 100 ;
        Log.d(TAG, "Value of atkProg: " + atkProg);
        defProg = ((double)poke.getDef()/MAX) * 100;
        Log.d(TAG, "Value of defProg: " + defProg);
        spAtkProg = ((double)poke.getAtk()/MAX) * 100;
        Log.d(TAG, "Value of spAtkProg: " + spAtkProg);
        spDefProg = ((double)poke.getSpDef()/MAX) * 100;
        Log.d(TAG, "Value of spDefProg: " + spDefProg);
        spdProg = ((double)poke.getSpeed()/MAX) * 100;
        Log.d(TAG, "Value of spdProg: " + spdProg);
        barHp.setProgress((int)hpProg);
        barAtk.setProgress((int) atkProg);
        barDef.setProgress((int)defProg);
        barSpAtk.setProgress((int)spAtkProg);
        barSpDef.setProgress((int)spDefProg);
        barSpeed.setProgress((int)spdProg);

        attachToProgress(barHp,hp,(int) hpProg);
        attachToProgress(barAtk,atk, (int)atkProg);
        attachToProgress(barDef,def, (int)defProg);
        attachToProgress(barSpAtk,spAtk, (int)spAtkProg);
        attachToProgress(barSpDef,spDef,(int) spDefProg);
        attachToProgress(barSpeed,speed, (int) spdProg);
    }
    public void attachToProgress(ProgressBar progressBar, TextView textView, int margin) {
        String TG = "AtP";

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        float scale = requireContext().getResources().getDisplayMetrics().density;
        progressBar.post(() -> {
            int width = progressBar.getWidth(); //height is ready
            int tvWidth = textView.getWidth();
            Log.d(TG, "marginStart for   = "+layoutParams.getMarginStart() + " width for bar = " + width);
            layoutParams.setMarginStart(  (int)((margin/100.0)*(int)Math.ceil((width)) - tvWidth));
            Log.d(TG, "LayoutParam MArgin after: " + layoutParams.getMarginStart());
            if(layoutParams.getMarginStart() > width*.9){
                layoutParams.setMarginStart(width - (int)(width*.2));
//                } else if(layoutParams.getMarginStart() < width *.2){
//                    layoutParams.setMarginStart(width- (int)(width*.8));
            }else {
                textView.setLayoutParams(layoutParams);
            }
        });


    }
    public void setFav(){
        poke.setFavorite(!poke.isFavorite());
        Glide.with(this)
                .load((poke.isFavorite() ? R.drawable.ic_baseline_favorite_24:R.drawable.ic_baseline_favorite_border_24))
                .override(48,48)
                .into(fav);
    }
}