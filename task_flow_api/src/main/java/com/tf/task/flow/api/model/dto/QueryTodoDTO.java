package com.tf.task.flow.api.model.dto;

import com.tf.task.flow.common.domain.PageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author ouweijian
 * @date 2025/3/12 10:54
 */
@Data
public class QueryTodoDTO {
    @Schema(description = "Page parameter")
    private PageQuery pageable;
    @Schema(description = "Due date", example = "2025-03-19T11:03:31.351Z")
    private LocalDateTime[] dueDate;
    @Schema(description = "Status", example = "0")
    private Integer status;
    @Schema(description = "tags", example = "[\"tag1\", \"tag2\"]")
    private Set<String> tags;
}
