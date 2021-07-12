package com.example.mrfoody;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class foodId {
    @Exclude
    public String foodId;

    public <T extends foodId> T withId(@NonNull final String id) {

        this.foodId = id;
        return (T) this;
    }
}
