package com.tf.task.flow.api.service.impl;

import com.tf.task.flow.api.constant.TaskFlowErrorCode;
import com.tf.task.flow.api.model.TodoModifiedEvent;
import com.tf.task.flow.api.model.dto.QueryTodoDTO;
import com.tf.task.flow.api.model.dto.SaveTodoDTO;
import com.tf.task.flow.api.model.entity.Todo;
import com.tf.task.flow.api.repository.TodoRepository;
import com.tf.task.flow.api.service.TodoService;
import com.tf.task.flow.api.utils.RequestContext;
import com.tf.task.flow.common.domain.PageQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author ouweijian
 * @date 2025/3/11 21:35
 */
@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * get all todos
     */
    @Override
    public Page<Todo> queryTodos(QueryTodoDTO queryTodoDTO) {
        PageQuery pageable = queryTodoDTO.getPageable();
        Sort sort = StringUtils.isEmpty(pageable.getSortFiled()) ? Sort.unsorted()
                : Sort.by(Sort.Direction.fromString(pageable.getSortDirection()), pageable.getSortFiled());
        PageRequest pageRequest = PageRequest.of(pageable.getPageNum(), pageable.getPageSize(), sort);
        return todoRepository.searchTodos(pageRequest, RequestContext.getCurrentUserId(), queryTodoDTO.getStatus(),
                queryTodoDTO.getDueDate(), queryTodoDTO.getTags());
    }

    /**
     * add or update todo
     */
    @Override
    public Todo saveTodo(String todoId, SaveTodoDTO request) {
        Todo toSave;
        if (todoId != null) {
            Optional<Todo> opt = todoRepository.findById(todoId);
            // validate ID
            toSave = opt.orElseThrow(TaskFlowErrorCode.TODO_NOT_EXIST::toException);
            // validate owner
            TaskFlowErrorCode.PERMISSION_DENY.assertTrue(RequestContext.getCurrentUserId().equals(toSave.getOwnerId()));
            // validate CONCURRENT_MODIFY
            TaskFlowErrorCode.CONCURRENT_MODIFY.assertTrue(Objects.equals(request.getVersion(), toSave.getVersion()));
        } else {
            toSave = new Todo();
            toSave.setOwnerId(RequestContext.getCurrentUserId());
            toSave.setVersion(0);
            toSave.setCreatedAt(LocalDateTime.now());
        }
        BeanUtils.copyProperties(request, toSave, "id", "createdAt", "version", "ownerId");
        toSave.setUpdatedAt(LocalDateTime.now());

        if (todoId == null) {
            return todoRepository.save(toSave);
        } else {
            Todo todo = todoRepository.updateTodo(toSave, request.getVersion());
            TaskFlowErrorCode.CONCURRENT_MODIFY.assertNonNull(todo);

            // publish event, real-time notify updates
            eventPublisher.publishEvent(new TodoModifiedEvent(this, todo));
            return todo;
        }
    }

    /**
     * Get todo by id
     */
    @Override
    public Todo getTodoById(String id) {
        Todo todo = todoRepository.findById(id).orElseThrow(TaskFlowErrorCode.TODO_NOT_EXIST::toException);
        TaskFlowErrorCode.PERMISSION_DENY.assertTrue(RequestContext.getCurrentUserId().equals(todo.getOwnerId()));
        return todo;
    }

    /**
     * delete todo
     * @param id
     */
    @Override
    public void deleteTodo(String id) {
        todoRepository.deleteById(id);
    }

    @Override
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

}
