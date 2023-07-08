package com.example.movies.CommentsPackage;

import com.google.gson.annotations.SerializedName;

public class Comment {

    @SerializedName("type")
    private String type;
    @SerializedName("author")
    private String author;
    @SerializedName("review")
    private String text;

    public Comment(String type, String author, String text) {
        this.type = type;
        this.author = author;
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public String getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }
}
