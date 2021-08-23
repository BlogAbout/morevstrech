package ru.morevstrech.service.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_USER,
    ROLE_COMPANY,
    ROLE_MANAGER,
    ROLE_MODERATOR,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}