package com.tf.task.flow.api.model;

import com.tf.task.flow.api.model.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author ouweijian
 * @date 2025/3/12 17:55
 */
@Data
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String userId;
    private String username;
    private String password;
    List<GrantedAuthority> authorities;

    public CustomUserDetails(User user, List<GrantedAuthority> authorities) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
