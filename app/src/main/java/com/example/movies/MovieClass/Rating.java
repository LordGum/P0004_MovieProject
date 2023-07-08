package com.example.movies.MovieClass;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    @SerializedName("kp")
    private String kp;

    public Rating(String kp) {
        this.kp = kp;
    }

    public String getKp() {
        float score = Float.parseFloat(kp);
        score *= 10;
        int num = Math.round(score);

        return String.valueOf((float) num/10);
        //return kp;
    }
}
