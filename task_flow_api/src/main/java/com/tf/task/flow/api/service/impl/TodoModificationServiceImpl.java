package com.tf.task.flow.api.service.impl;

import com.tf.task.flow.api.constant.TaskFlowErrorCode;
import com.tf.task.flow.api.model.converter.TodoMapper;
import com.tf.task.flow.api.model.entity.Todo;
import com.tf.task.flow.api.model.view.TodoVO;
import com.tf.task.flow.api.repository.TodoRepository;
import com.tf.task.flow.api.service.TodoModificationService;
import com.tf.task.flow.common.domain.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author ouweijian
 * @date 2025/3/12 21:55
 */
@Slf4j
@Service
public class TodoModificationServiceImpl implements TodoModificationService {

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(16);

    private final Map<String, Queue<CompletableFuture<Result<TodoVO>>>> PENDING_MAP = new ConcurrentHashMap<>();

    @Autowired
    private TodoRepository todoRepository;

    @Override
    public CompletableFuture<Result<TodoVO>> listenModification(String todoId) {
        // whether todoId exist?
        todoRepository.findById(todoId).orElseThrow(TaskFlowErrorCode.TODO_NOT_EXIST::toException);

        CompletableFuture<Result<TodoVO>> future = new CompletableFuture<>();
        // Use queue to support multiple clients to listen for changes
        PENDING_MAP.compute(todoId, (key, pendingResponses) -> {
            if (pendingResponses == null) {
                pendingResponses = new ConcurrentLinkedQueue<>();
            }
            pendingResponses.add(future);
            return pendingResponses;
        });

        // 30s timeout
        scheduler.schedule(() -> {
            Queue<CompletableFuture<Result<TodoVO>>> pendingResponses = PENDING_MAP.get(todoId);
            if (pendingResponses.remove(future)) {
                future.complete(Result.success());
                // avoid memory leak
                if (pendingResponses.isEmpty()) {
                    PENDING_MAP.remove(todoId);
                }
            }

        }, 30, TimeUnit.SECONDS);
        return future;
    }

    @Override
    public void handleTodoModified(Todo todo) {
        try {
            String todoId = todo.getId();
            // avoid memory leak
            Queue<CompletableFuture<Result<TodoVO>>> pendingResponses = PENDING_MAP.remove(todoId);
            // notify all clients
            for (CompletableFuture<Result<TodoVO>> pendingResponse : pendingResponses) {
                pendingResponse.complete(Result.success(TodoMapper.INSTANCE.toView(todo)));
            }
        } catch (Exception e) {
            log.error("handleTodoModified error", e);
        }

    }


}
