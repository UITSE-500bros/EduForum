package com.example.eduforum.activity.model.community_manage;

import android.net.Uri;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Community {
    private String communityId;
    private String name;

    private List<String> adminList = new ArrayList<>();
    /**
     * profileImage is the uri of the image in client side used for uploading
     */
    private Uri profileImage;
    /**
     * profilePicture is the url of the image in server side used for downloading
     */
    private String profilePicture;
    private Timestamp timeCreated;
    private String description;
    private String department;
    private Integer totalPost;
    
    private Integer totalNewPost;
    private String inviteCode;
    private String visibility;
    private Boolean requestSent;


    private List<String> userList = new ArrayList<>();

    public Community() {
    }


    public Community(String communityId, String name, List<String> adminList, Uri profileImage, Timestamp timeCreated, String description, String department, List<String> userList) {
        this.communityId = communityId;
        this.name = name;
        this.adminList = adminList;
        this.profileImage = profileImage;
        this.timeCreated = timeCreated;
        this.description = description;
        this.department = department;
        this.userList = userList;
    }

    public Community(String communityId, String name, List<String> adminList, Uri profileImage, Timestamp timeCreated, String description, String department, Integer totalPost, Integer totalNewPost, List<String> userList) {
        this.communityId = communityId;
        this.name = name;
        this.adminList = adminList;
        this.profileImage = profileImage;
        this.timeCreated = timeCreated;
        this.description = description;
        this.department = department;
        this.totalPost = totalPost;
        this.totalNewPost = totalNewPost;
        this.userList = userList;
    }

    public String getVisibility() {
        return visibility;
    }

    public Boolean getRequestSent() {
        return requestSent;
    }

    public void setRequestSent(Boolean requestSent) {
        this.requestSent = requestSent;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public Integer getTotalPost() {
        return totalPost;
    }

    public void setTotalPost(Integer totalPost) {
        this.totalPost = totalPost;
    }

    public Integer getTotalNewPost() {
        return totalNewPost;
    }

    public void setTotalNewPost(Integer totalNewPost) {
        this.totalNewPost = totalNewPost;
    }

    public List<String> getAdminList() {
        return adminList;
    }

    public void setAdminList(List<String> adminList) {
        this.adminList = adminList;
    }


    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getCommunityId() {
        return communityId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Uri getProfileImage() {
        return profileImage;
    }

    /**
     * Convert firebase timestamp to string in (DD/MM/YYYY) format
     * @return timeCreated in (DD/MM/YYYY) format
     */
    public String getTimeCreated() {
        // Convert firebase timestamp to string in (DD/MM/YYYY) format
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        return sdf.format(timeCreated.toDate());
    }

    public String getDepartment() {
        return department;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProfileImage(Uri profileImage) {
        this.profileImage = profileImage;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public void setUserList(List<String> userList) {
        this.userList = userList;
    }

    public List<String> getUserList() {
        return userList;
    }
}
