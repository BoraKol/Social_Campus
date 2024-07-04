package com.computer.socialcampus.service;

public abstract class PostNotificationSetting extends NotificationSetting {

    private String postId;

    public PostNotificationSetting(String settingName, String postId) {
        super(settingName);
        this.postId = postId;
    }

    public String getPostId() {
        return postId;
    }

    public abstract void saveSetting(String userId);

    public abstract void loadSetting(String userId);
}
