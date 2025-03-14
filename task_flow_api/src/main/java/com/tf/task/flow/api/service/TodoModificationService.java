package com.tf.task.flow.api.service;

import com.tf.task.flow.api.model.entity.Todo;
import com.tf.task.flow.api.model.view.TodoVO;
import com.tf.task.flow.common.domain.Result;

import java.util.concurrent.CompletableFuture;

/**
 * @author ouweijian
 * @date 2025/3/12 21:55
 */
public interface TodoModificationService {
    CompletableFuture<Result<TodoVO>> listenModification(String todoId);

    void handleTodoModified(Todo todo);
}
