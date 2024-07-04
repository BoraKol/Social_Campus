package com.computer.socialcampus.ui.postShare;

public class Comment {
    private String commentText;
    private String username;

    public Comment() {
        // Boş constructor gereklidir
    }

    public Comment(String commentText, String username) {
        this.commentText = commentText;
        this.username = username;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
