package com.example.movies.CommentsPackage;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.R;

import java.util.ArrayList;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {
    List<Comment> comments = new ArrayList<>();

    public void setComments(List<Comment> comments) {
        this.comments = comments;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.comment_item,
                parent,
                false
        );

        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentHolder holder, int position) {
        Comment comment = comments.get(position);

        holder.commentText.setText(comment.getText());
        holder.author.setText(comment.getAuthor());

        int colorId;
        if(comment.getType() == null) {
            colorId = android.R.color.holo_orange_light;
        } else {
            String type = comment.getType();

            if (type.equals("Позитивный")) {
                colorId = android.R.color.holo_green_dark;
            } else if (type.equals("Нейтральный")) {
                colorId = android.R.color.holo_orange_light;
            } else {
                colorId = android.R.color.holo_red_dark;
            }
        }
        Drawable back = ContextCompat.getDrawable(holder.itemView.getContext(), colorId);
        holder.constraintLayout.setBackground(back);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    static class CommentHolder extends RecyclerView.ViewHolder{
        private TextView commentText;
        private TextView author;
        private ConstraintLayout constraintLayout;

        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            commentText = itemView.findViewById(R.id.textViewComment);
            author = itemView.findViewById(R.id.CommentAuthor);
            constraintLayout = itemView.findViewById(R.id.constraint);
        }
    }
}
