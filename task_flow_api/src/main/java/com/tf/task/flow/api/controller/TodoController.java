package com.tf.task.flow.api.controller;

import com.tf.task.flow.api.model.converter.TodoMapper;
import com.tf.task.flow.api.model.dto.QueryTodoDTO;
import com.tf.task.flow.api.model.dto.SaveTodoDTO;
import com.tf.task.flow.api.model.entity.Todo;
import com.tf.task.flow.api.model.view.TodoVO;
import com.tf.task.flow.api.service.TodoService;
import com.tf.task.flow.common.domain.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * @author ouweijian
 * @date 2025/3/11 21:16
 */
@RestController
@RequestMapping("/todo")
@Tag(name = "TODO Management", description = "Provide CRUD APIs for to-do items.")
public class TodoController {

    @Autowired
    private TodoService todoService;

//    @Autowired
//    public TodoController(TodoServiceImpl todoService) {
//        this.todoService = todoService;
//    }

    @PreAuthorize("hasAnyRole('ADMIN')")  // Only ADMIN can access
    @GetMapping
    @Operation(summary = "get all todo list", description = "Return all todo list")
    public Result<List<TodoVO>> getAllTodos() {
        List<Todo> list = todoService.getAllTodos();
        List<TodoVO> viewList = TodoMapper.INSTANCE.toViewList(list);
        return Result.success(viewList);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")  // USER and ADMIN can access
    @Operation(summary = "create a new todo", description = "create a new todo")
    @PostMapping
    public Result<TodoVO> createTodo(@RequestBody @Valid SaveTodoDTO todo) {
        Todo saveTodo = todoService.saveTodo(null, todo);
        return Result.success(TodoMapper.INSTANCE.toView(saveTodo));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")  // USER and ADMIN can access
    @Operation(summary = "update a todo", description = "update a todo")
    @PutMapping("/{id}")
    public Result<TodoVO> updateTodo(@PathVariable("id") String id, @RequestBody @Valid SaveTodoDTO todo) {
        Todo saveTodo = todoService.saveTodo(id, todo);
        return Result.success(TodoMapper.INSTANCE.toView(saveTodo));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")  // USER and ADMIN can access
    @PostMapping("query")
    @Operation(summary = "Search todo list", description = "Return todo list")
    public Result<Page<TodoVO>> queryTodos(@RequestBody QueryTodoDTO queryTodoDTO) {
        Page<Todo> page = todoService.queryTodos(queryTodoDTO);
        List<TodoVO> viewList = TodoMapper.INSTANCE.toViewList(page.getContent());
        Page<TodoVO> pageImpl = new PageImpl<>(viewList, page.getPageable(), page.getTotalElements());
        return Result.success(pageImpl);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")  // USER and ADMIN can access
    @Operation(summary = "Get todo by id", description = "Return a single todo info")
    @GetMapping("/{id}")
    public Result<TodoVO> getTodoById(@PathVariable("id") String id) {
        Todo todo = todoService.getTodoById(id);
        return Result.success(TodoMapper.INSTANCE.toView(todo));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")  // USER and ADMIN can access
    @Operation(summary = "delete a todo")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteTodo(@PathVariable("id") String id) {
        todoService.deleteTodo(id);
        return Result.success();
    }
}
