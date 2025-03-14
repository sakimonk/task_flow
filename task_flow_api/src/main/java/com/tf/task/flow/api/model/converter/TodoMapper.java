package com.tf.task.flow.api.model.converter;

import com.tf.task.flow.api.model.entity.Todo;
import com.tf.task.flow.api.model.view.TodoVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author ouweijian
 * @date 2025/3/11 23:23
 */
@Mapper
public interface TodoMapper {

    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    List<TodoVO> toViewList(List<Todo> todoList);
    TodoVO toView(Todo todo);

}
