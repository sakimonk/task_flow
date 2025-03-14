package com.tf.task.flow.api.repository;

import com.tf.task.flow.api.model.entity.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author ouweijian
 * @date 2025/3/11 23:15
 */
public interface TodoRepository extends MongoRepository<Todo, String>, CustomTodoRepository {
}
