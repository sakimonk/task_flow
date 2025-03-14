package com.tf.task.flow.api.repository.impl;

import com.mongodb.client.result.UpdateResult;
import com.tf.task.flow.api.model.entity.Todo;
import com.tf.task.flow.api.repository.CustomTodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author ouweijian
 * @date 2025/3/12 10:52
 */
public class CustomTodoRepositoryImpl implements CustomTodoRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Page<Todo> searchTodos(Pageable pageParameter, String currentUserId, Integer status, LocalDateTime[] dueDate, Set<String> tags) {
        Query query = new Query();

        query.addCriteria(Criteria.where("ownerId").is(currentUserId));
        if (status != null) {
            query.addCriteria(Criteria.where("status").is(status));
        }
        // due date range query
        if (dueDate != null && dueDate.length > 0) {
            Criteria criteria = Criteria.where("dueDate");
            if (dueDate[0] != null) {
                criteria.gte(dueDate[0]);
            }
            if (dueDate.length > 1 && dueDate[1] != null) {
                criteria.lte(dueDate[1]);
            }
            query.addCriteria(criteria);
        }

        // Queries whether an array field contains the specified label
        if (tags != null && !tags.isEmpty()) {
            query.addCriteria(Criteria.where("tags").in(tags));
        }

        // count total
        long total = mongoTemplate.count(query, Todo.class);

        // handle paging and sorting
        PageRequest pageRequest = PageRequest.of(pageParameter.getPageNumber(), pageParameter.getPageSize());
        query.with(pageRequest);
        query.with(pageParameter.getSort());

        List<Todo> todos = mongoTemplate.find(query, Todo.class);
        return new PageImpl<>(todos, pageRequest, total);
    }

    @Override
    public Todo updateTodo(Todo todo, Integer expectVersion) {
        UpdateResult updateResult = mongoTemplate.updateFirst(
                Query.query(Criteria.where("id").is(todo.getId()).and("version").is(expectVersion)),
                new Update()
                        .set("todoTitle", todo.getTodoTitle())
                        .set("description", todo.getDescription())
                        .set("dueDate", todo.getDueDate())
                        .set("status", todo.getStatus())
                        .set("tags", todo.getTags())
                        .set("priority", todo.getPriority())
                        .set("updatedAt", todo.getUpdatedAt())
                        .inc("version", 1),
                Todo.class
        );
        boolean success = updateResult.getModifiedCount() == 1;
        if (!success) {
            // concurrent modify
            return null;
        }
        todo.setVersion(todo.getVersion() + 1);
        return todo;
    }

}
