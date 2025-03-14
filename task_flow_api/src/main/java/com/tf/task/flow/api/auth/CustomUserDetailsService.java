package com.tf.task.flow.api.auth;

import com.tf.task.flow.api.constant.UserErrorCode;
import com.tf.task.flow.api.model.CustomUserDetails;
import com.tf.task.flow.api.model.entity.User;
import com.tf.task.flow.api.repository.UserRepository;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ouweijian
 * @date 2025/3/12 17:50
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库查询用户
        User user = userRepository.findByUsername(username);

        UserErrorCode.USER_NOT_FOUND.assertNonNull(user);

        List<GrantedAuthority> authorities = ObjectUtils.defaultIfNull(user.getRoles(), Collections.<String>emptyList())
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        return new CustomUserDetails(user, authorities);
    }
}
