package com.tf.task.flow.api.service.impl;

import com.tf.task.flow.api.constant.UserErrorCode;
import com.tf.task.flow.api.model.CustomUserDetails;
import com.tf.task.flow.api.model.dto.UserRegisterDTO;
import com.tf.task.flow.api.model.entity.User;
import com.tf.task.flow.api.repository.UserRepository;
import com.tf.task.flow.api.service.UserService;
import com.tf.task.flow.common.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author ouweijian
 * @date 2025/3/12 13:35
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public User register(UserRegisterDTO registerDTO) {
        User userByName = userRepository.findByUsername(registerDTO.getUsername());
        // validate duplicate name
        UserErrorCode.USERNAME_EXIST.assertTrue(userByName == null);

        User userToSave = new User();
        userToSave.setUsername(registerDTO.getUsername());
        userToSave.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userToSave.setEmail(registerDTO.getEmail());
        userToSave.setRoles(registerDTO.getRoles());
        return userRepository.save(userToSave);
    }

    @Override
    public String login(String username, String password) {
        // validate
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // Generate JWT Token
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return JwtUtil.generateToken(principal.getUserId());
    }
}
