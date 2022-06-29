package com.example.finalproject.ui.theme;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class colors {
    // App Background Colors
    final Color LIGHT_BLUE = Color.valueOf(0xFFBAC7FF);
    final Color LIGHT_GREY = Color.valueOf(0xFFAAAAAA);

    // Stat Colors
    final Color HP_COLOR = Color.valueOf(0xFFF5FF00);
    final Color ATK_COLOR = Color.valueOf(1f, 0f, 0f, 0.66f);
    final Color DEF_COLOR = Color.valueOf(0f, 0f, 1f, 0.44f);
    final Color SP_ATK_COLOR = Color.valueOf(0.671f, 0f, 1f, 0.57f);
    final Color SP_DEF_COLOR = Color.valueOf(1f, 0f, 0.8f, 0.7f);
    final Color SPD_COLOR = Color.valueOf(0f, 1f, 0.063f, 0.55f);

    final Color TYPE_NORMAL = Color.valueOf(0xFFA8A77A);
    final Color TYPE_FIRE = Color.valueOf(0xFFEE8130);
    final Color TYPE_WATER = Color.valueOf(0xFF6390F0);
    final Color TYPE_ELECTRIC = Color.valueOf(0xFFF7D02C);
    final Color TYPE_GRASS = Color.valueOf(0xFF7AC74C);
    final Color TYPE_ICE = Color.valueOf(0xFF96D9D6);
    final Color TYPE_FIGHTING = Color.valueOf(0xFFC22E28);
    final Color TYPE_POISON = Color.valueOf(0xFFA33EA1);
    final Color TYPE_GROUND = Color.valueOf(0xFFE2BF65);
    final Color TYPE_FLYING = Color.valueOf(0xFFA98FF3);
    final Color TYPE_PSYCHIC = Color.valueOf(0xFFF95587);
    final Color TYPE_BUG = Color.valueOf(0xFFA6B91A);
    final Color TYPE_ROCK = Color.valueOf(0xFFB6A136);
    final Color TYPE_GHOST = Color.valueOf(0xFF735797);
    final Color TYPE_DRAGON = Color.valueOf(0xFF6F35FC);
    final Color TYPE_DARK = Color.valueOf(0xFF705746);
    final Color TYPE_STEEL = Color.valueOf(0xFFB7B7CE);
    final Color TYPE_FAIRY = Color.valueOf(0xFFD685AD);
}
