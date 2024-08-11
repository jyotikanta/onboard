package com.arcticbear.onboard.dto;

import java.util.Set;

public class UserDTO {
    private String id;
    private String email;
    private String mobile;
    private Set<String> roles;

    public UserDTO(String id, String email, String mobile, Set<String> roles) {
        this.id = id;
        this.email = email;
        this.mobile = mobile;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }
}
