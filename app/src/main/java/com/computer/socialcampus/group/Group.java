package com.computer.socialcampus.group;

import com.computer.socialcampus.ui.postShare.Post;

import java.util.List;

public class Group {
    private String groupId;
    private String groupName;
    private String groupDescription;
    private String groupImage; // URL for the group's profile image
    private List<Post> posts; // List of posts for the group
    private List<String> groupMembers;

    public Group() {
    }

    public Group(String groupId, String groupName, String groupDescription, String groupImage, List<Post> posts) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupImage = groupImage;
        this.posts = posts;
    }
    public Group(String groupId, String groupName, String groupDescription, List<String> groupMembers) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupMembers = groupMembers;
    }

    public Group(String groupId, String groupName, String groupDescription, String groupImage, List<Post> posts, List<String> groupMembers) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupDescription = groupDescription;
        this.groupImage = groupImage;
        this.posts = posts;
        this.groupMembers = groupMembers;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = groupImage;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<String> groupMembers) {
        this.groupMembers = groupMembers;
    }
}
