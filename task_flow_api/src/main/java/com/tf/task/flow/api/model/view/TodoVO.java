package com.tf.task.flow.api.model.view;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ouweijian
 * @date 2025/3/11 21:35
 */
@Data
public class TodoVO {

    @Schema(description = "primary key,generate by mongodb", example = "67d10bcfa3ea6a1d4aa6f034")
    private String id;
    @Schema(description = "ownerId", example = "67d123262087c927324663c8")
    private String ownerId;
    @Schema(description = "todo name", example = "todo title")
    private String todoTitle;
    @Schema(description = "todo description", example = "desc")
    private String description;
    @Schema(description = "due date", example = "2025-03-12T11:18:22.934Z")
    private LocalDateTime dueDate;
    @Schema(description = "status, 0-not started, 1-in progress, 2-completed", example = "0")
    private Integer status;
    @Schema(description = "task proiority, the higher the value, the higher the priority.", example = "88")
    private Integer priority;
    @Schema(description = "tags", example = "[\"tag1\", \"tag2\"]")
    private List<String> tags;
    @Schema(description = "createdAt", example = "2025-03-12T11:18:22.934Z")
    private LocalDateTime createdAt;
    @Schema(description = "updatedAt", example = "2025-03-12T11:18:22.934Z")
    private LocalDateTime updatedAt;
    @Schema(description = "data version, start from 0", example = "1")
    private Integer version;
}
