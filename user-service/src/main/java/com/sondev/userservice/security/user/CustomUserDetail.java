package com.sondev.userservice.security.user;

import com.sondev.userservice.entity.Role;
import com.sondev.userservice.entity.Status;
import com.sondev.userservice.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomUserDetail implements UserDetails {

    private String userName;
    private String password;
    private String email;
    private SimpleGrantedAuthority role;

    public CustomUserDetail (User user) {
        this.userName = user.getUserName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.role = new SimpleGrantedAuthority(user.getRole().name());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.role);
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.userName;
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
        return true;
    }

}
