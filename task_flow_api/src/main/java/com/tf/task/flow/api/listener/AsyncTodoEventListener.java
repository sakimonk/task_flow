package com.tf.task.flow.api.listener;

import com.tf.task.flow.api.model.TodoModifiedEvent;
import com.tf.task.flow.api.service.TodoModificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author ouweijian
 * @date 2025/3/12 22:17
 */
@Slf4j
@Component
public class AsyncTodoEventListener {

    @Autowired
    private TodoModificationService todoModificationService;

    @Async
    @EventListener
    public void handleTodoModified(TodoModifiedEvent event) {
        log.info("handle TodoModifiedEvent: " + event);
        todoModificationService.handleTodoModified(event.getTodo());
    }
}
