package com.tf.task.flow.api.model.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author ouweijian
 * @date 2025/3/12 13:21
 */
@Data
public class UserVO {
    private String id;
    @Schema(description = "username", example = "test")
    private String username;
    @Schema(description = "email", example = "fox@gmail.com")
    private String email;
    @Schema(description = "roles", example = "[\"ROLE_ADMIN\", \"ROLE_USER\"]")
    private List<String> roles;
}
