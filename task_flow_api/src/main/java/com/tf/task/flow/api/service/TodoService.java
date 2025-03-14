package com.tf.task.flow.api.service;

import com.tf.task.flow.api.model.dto.QueryTodoDTO;
import com.tf.task.flow.api.model.dto.SaveTodoDTO;
import com.tf.task.flow.api.model.entity.Todo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author ouweijian
 * @date 2025/3/11 23:27
 */
public interface TodoService {

    /**
     * query todos
     */
    Page<Todo> queryTodos(QueryTodoDTO queryTodoDTO);

    /**
     * add or update todo
     */
    Todo saveTodo(String todoId, SaveTodoDTO todo);

    /**
     * Get todo by id
     */
    Todo getTodoById(String id);

    /**
     * delete todo
     * @param id
     */
    void deleteTodo(String id);

    List<Todo> getAllTodos();
}
