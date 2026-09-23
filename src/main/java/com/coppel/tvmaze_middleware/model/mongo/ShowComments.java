package com.coppel.tvmaze_middleware.model.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "show_comments")
public class ShowComments {

    @Id
    private Integer showId;

    private List<Comment> comments = new ArrayList<>();

    public ShowComments() {
    }

    public ShowComments(Integer showId, List<Comment> comments) {
        this.showId = showId;
        this.comments = comments;
    }

    public Integer getShowId() {
        return showId;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}