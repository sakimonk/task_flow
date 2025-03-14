package com.tf.task.flow.api.controller;

import com.tf.task.flow.api.model.view.TodoVO;
import com.tf.task.flow.api.service.TodoModificationService;
import com.tf.task.flow.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author ouweijian
 * @date 2025/3/12 21:49
 */
@RestController
@RequestMapping("/todoModification")
@Tag(name = "TODO Real-Time Update", description = "Monitor todo changes to achieve real-time update function")
public class TodoModificationController {

    @Autowired
    private TodoModificationService todoModificationService;

    @GetMapping("/{id}")
    @Operation(summary = "Use long polling to listen for todo changes")
    public CompletableFuture<Result<TodoVO>> listenModification(@PathVariable("id") String todoId) {
        return todoModificationService.listenModification(todoId);
    }


}
