package com.example.finalproject.ui.theme;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class theme extends colors {
    public Color primary;
    public Color background;
    public Color onBackground;
    public Color surface;
    public Color onSurface;

    public theme(String background) {
        if (background.equalsIgnoreCase("dark"))
            DarkPalette();
        else
            LightPalette();
    }

    public void DarkPalette() {
        primary = Color.valueOf(Color.YELLOW);
        background = Color.valueOf(0xFF101010);
        onBackground = Color.valueOf(Color.WHITE);
        surface = Color.valueOf(0xFF303030);
        onSurface = Color.valueOf(Color.WHITE);
    }

    public void LightPalette() {
        primary = Color.valueOf(Color.BLUE);
        background = LIGHT_BLUE;
        onBackground = Color.valueOf(Color.BLACK);
        surface = Color.valueOf(Color.WHITE);
        onSurface = Color.valueOf(Color.BLACK);
    }
};