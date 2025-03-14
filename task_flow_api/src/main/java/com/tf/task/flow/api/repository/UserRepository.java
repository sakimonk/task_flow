package com.tf.task.flow.api.repository;

import com.tf.task.flow.api.model.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ouweijian
 * @date 2025/3/12 13:39
 */
public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository{

    User findByUsername(String username);
}
