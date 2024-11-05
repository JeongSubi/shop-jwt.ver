package com.subi.shop.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class CustomUser extends User {
    public Long id;
    public String displayName;

    public CustomUser(String username,
                      String password,
                      List<SimpleGrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}