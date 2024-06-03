package com.thx.xuitest;

import androidx.annotation.Nullable;

public class Flavors {
    private String size;
    private String sugar;
    private String temperature;
    Flavors(String size, String sugar, String temperature)
    {
        this.size = size;
        this.sugar = sugar;
        this.temperature = temperature;
    }
    @Override
    public String toString()
    {
        String s = size+"，"+sugar+"，"+temperature;
        return s;
    }
    @Override
    public boolean equals(@Nullable Object obj) {
        return this.toString().equals(((Flavors)obj).toString());
    }
    public String getSize() {
        return size;
    }
}

