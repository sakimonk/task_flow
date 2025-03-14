package com.tf.task.flow.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author ouweijian
 * @date 2025/3/12 13:21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class User {
    /**
     * user id
     */
    private String id;
    /**
     * user name
     */
    private String username;
    /**
     * password
     */
    private String password;
    /**
     * email
     */
    private String email;
    /**
     * userRoles
     */
    private List<String> roles;
}
