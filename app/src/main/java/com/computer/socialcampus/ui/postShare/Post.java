package com.computer.socialcampus.ui.postShare;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.auth.User;

import java.util.Map;

public class Post implements Parcelable {

    private String username;
    private String content;
    private String mediaUrl; // URL for the image or video
    private String groupId; // ID of the group the post belongs to
    private String timestamp; // Timestamp of the post creation
    private String imageUri;
    private String videoUri;
    private int likes;
    private Map<String, Object> comments;

    public Post() {
    }

    public Post(String username, String content, String mediaUrl) {
        this.username = username;
        this.content = content;
        this.mediaUrl = mediaUrl;
    }

    protected Post(Parcel in) {
        username = in.readString();
        content = in.readString();
        mediaUrl = in.readString();
    }

    public Post(String username, String content, String mediaUrl, String groupId, String timestamp) {
        this.username = username;
        this.content = content;
        this.mediaUrl = mediaUrl;
        this.groupId = groupId;
        this.timestamp = timestamp;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    // Getters and setters for username, content, and mediaUrl
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(content);
        dest.writeString(mediaUrl);
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Map<String, Object> getComments() {
        return comments;
    }

    public void setComments(Map<String, Object> comments) {
        this.comments = comments;
    }
}