package com.tf.task.flow.api.controller;

import com.tf.task.flow.api.model.converter.UserMapper;
import com.tf.task.flow.api.model.dto.UserRegisterDTO;
import com.tf.task.flow.api.model.entity.User;
import com.tf.task.flow.api.model.view.UserVO;
import com.tf.task.flow.api.service.UserService;
import com.tf.task.flow.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ouweijian
 * @date 2025/3/12 13:26
 */
@RestController
@RequestMapping("/user")
@Tag(name = "user Management", description = "Implement login and registration function")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "User register")
    public Result<UserVO> register(@RequestBody @Valid UserRegisterDTO user) {
        User registeredUser = userService.register(user);
        return Result.success(UserMapper.INSTANCE.toView(registeredUser));
    }

    @PostMapping("/login")
    @Operation(summary = "User login")
    public Result<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        String token  = userService.login(username, password);
        return Result.success(token);
    }
}

