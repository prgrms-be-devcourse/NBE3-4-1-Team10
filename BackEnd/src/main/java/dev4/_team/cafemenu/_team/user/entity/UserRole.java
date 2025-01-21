package dev4._team.cafemenu._team.user.entity;

import lombok.Getter;

@Getter
public enum UserRole {
    ROLE_USER,
    ROLE_ADMIN;

    public String getAuthority() {
        return name();
    }
}