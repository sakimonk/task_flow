package com.tf.task.flow.api.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Task Entity
 * @author ouweijian
 * @date 2025/3/11 23:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "todo")
public class Todo {

    /**
     * primary key,generate by mongodb
     */
    @Id
    private String id;
    /**
     * owner user id
     */
    @Field("owner_id")
    private String ownerId;
    /**
     * task name
     */
    @Field("todo_title")
    private String todoTitle;
    /**
     * description
     */
    private String description;
    /**
     * due date
     */
    @Field("due_date")
    private LocalDateTime dueDate;
    /**
     * status, 0-not started, 1-in progress, 2-completed
     */
    private Integer status;
    /**
     * task proiority, the higher the value, the higher the priority.
     */
    private Integer priority;
    /**
     * task proiority, the higher the value, the higher the priority.
     */
    private List<String> tags;
    /**
     * create time
     */
    @Field("created_at")
    private LocalDateTime createdAt;
    /**
     * update time
     */
    @Field("updated_at")
    private LocalDateTime updatedAt;
    /**
     * version, for concurrency modify control
     */
    private Integer version;
}
