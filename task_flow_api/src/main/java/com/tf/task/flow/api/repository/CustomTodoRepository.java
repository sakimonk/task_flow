package com.tf.task.flow.api.repository;

import com.tf.task.flow.api.model.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author ouweijian
 * @date 2025/3/12 10:49
 */
public interface CustomTodoRepository {

    Page<Todo> searchTodos(Pageable pageable, String currentUserId, Integer status, LocalDateTime[] dueDate, Set<String> tags);

    Todo updateTodo(Todo todo, Integer expectVersion);
}
