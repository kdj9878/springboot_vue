package com.xxx.boot.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CostomUserDetail implements UserDetails {
    private int userId;
    private String username;
    private String password;
    private int activated;

    private String authority;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(authority));
        return authList;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return activated == 1 ? true:false;
    }

    public int getMember_id() {
        return userId;
    }

    public void setMember_id(int userId) {
        this.userId = userId;
    }



}
