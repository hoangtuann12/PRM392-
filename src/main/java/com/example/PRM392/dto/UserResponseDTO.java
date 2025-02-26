package com.example.PRM392.dto;

public class UserResponseDTO {
    private String fullname;
    private String username;
    private String phone;
    private String avatar;

    // Constructors
    public UserResponseDTO(String fullname, String username, String phone, String avatar) {
        this.fullname = fullname;
        this.username = username;
        this.phone = phone;
        this.avatar = avatar;
    }

    // Getters and setters
    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
