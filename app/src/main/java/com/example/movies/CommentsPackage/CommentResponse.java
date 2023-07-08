package com.example.movies.CommentsPackage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentResponse {

    @SerializedName("docs")
    private List<Comment> commentsList;

    public CommentResponse(List<Comment> commentsList) {
        this.commentsList = commentsList;
    }

    public List<Comment> getCommentsList() {
        return commentsList;
    }

    @Override
    public String toString() {
        return "CommentResponse{" +
                "commentsList=" + commentsList +
                '}';
    }
}
