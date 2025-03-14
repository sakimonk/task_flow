package com.tf.task.flow.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

/**
 * @author ouweijian
 * @date 2025/3/12 13:21
 */
@Data
public class UserRegisterDTO {
    @NotBlank
    @Schema(description = "username", example = "test")
    private String username;
    @NotBlank
    @Schema(description = "password", example = "pwd")
    private String password;
    @Schema(description = "email", example = "fox@gmail.com")
    private String email;
    @Schema(description = "roles", example = "[\"ROLE_ADMIN\", \"ROLE_USER\"]")
    private List<String> roles;
}
