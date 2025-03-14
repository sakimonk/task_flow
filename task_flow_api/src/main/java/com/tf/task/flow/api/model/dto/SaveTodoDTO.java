package com.tf.task.flow.api.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ouweijian
 * @date 2025/3/11 23:50
 */
@Data
public class SaveTodoDTO {
    /**
     * task name
     */
    @NotEmpty
    @Schema(description = "title", example = "todo title")
    private String todoTitle;
    /**
     * description
     */
    @Schema(description = "description", example = "todo description")
    private String description;
    /**
     * due date
     */
    @NotNull
    @Schema(description = "dueDate", example = "2025-03-18T14:30:00")
    private LocalDateTime dueDate;
    /**
     * status, 0-not started, 1-in progress, 2-completed
     */
    @NotNull
    @Schema(description = "status, 0-not started, 1-in progress, 2-completed", example = "0")
    private Integer status;
    /**
     * task priority, the higher the value, the higher the priority.
     */
    @NotNull
    @Schema(description = "priority, range from 0-999", example = "99")
    private Integer priority;
    /**
     * tags
     */
    @Schema(description = "tags", example = "[\"tag1\", \"tag2\"]")
    private List<String> tags;
    /**
     * version, for concurrency modify control
     */
    @Schema(description = "tags", example = "0")
    private Integer version;
}
