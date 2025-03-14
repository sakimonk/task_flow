package com.tf.task.flow.api.service;

import com.tf.task.flow.api.model.dto.UserRegisterDTO;
import com.tf.task.flow.api.model.entity.User;

/**
 * @author ouweijian
 * @date 2025/3/12 13:29
 */
public interface UserService {
    User register(UserRegisterDTO user);

    String login(String username, String password);
}
