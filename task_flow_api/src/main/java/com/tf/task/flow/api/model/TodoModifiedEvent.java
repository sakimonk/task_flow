package com.tf.task.flow.api.model;

import com.tf.task.flow.api.model.entity.Todo;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author ouweijian
 * @date 2025/3/12 22:13
 */
@Getter
public class TodoModifiedEvent extends ApplicationEvent {
    private final Todo todo;

    public TodoModifiedEvent(Object source, Todo todo) {
        super(source);
        this.todo = todo;
    }
}
