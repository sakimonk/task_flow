package com.tf.task.flow.common.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author ouweijian
 * @date 2025/3/12 11:51
 */
@Data
public class PageQuery {

    @Schema(description = "Page number, start from 0", example = "0")
    private int pageNum;
    @Schema(description = "Page size", example = "5")
    private int pageSize;
    @Schema(description = "sortFiled, camel_case style", example = "due_date")
    private String sortFiled;
    @Schema(description = "sortDirection, ASC or DESC", example = "ASC")
    private String sortDirection;
}
