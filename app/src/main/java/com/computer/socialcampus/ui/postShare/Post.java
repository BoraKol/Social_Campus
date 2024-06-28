package com.computer.socialcampus.ui.postShare;

import com.google.firebase.firestore.auth.User;

public class Post {
    private String postId;
    private String imageUrl;
    private String description;
    private String postContent;
    private User user;
    private long timestamp;

    public Post() {}

    public Post(String postId, String imageUrl, String description, String postContent, User user) {
        this.postId = postId;
        this.imageUrl = imageUrl;
        this.description = description;
        this.postContent = postContent;
        this.user = user;
        this.timestamp = System.currentTimeMillis();
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}