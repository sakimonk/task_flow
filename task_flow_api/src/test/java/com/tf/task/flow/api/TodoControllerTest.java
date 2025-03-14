package com.tf.task.flow.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tf.task.flow.api.controller.TodoController;
import com.tf.task.flow.api.model.converter.TodoMapper;
import com.tf.task.flow.api.model.dto.QueryTodoDTO;
import com.tf.task.flow.api.model.dto.SaveTodoDTO;
import com.tf.task.flow.api.model.entity.Todo;
import com.tf.task.flow.api.model.view.TodoVO;
import com.tf.task.flow.api.service.TodoService;
import com.tf.task.flow.common.domain.PageQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.nullValue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Todo todo;
    private TodoVO todoVO;
    private SaveTodoDTO saveTodoDTO;
    private QueryTodoDTO queryTodoDTO;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(todoController).build();

//        MockitoAnnotations.openMocks(this);

        todo = new Todo();
        todo.setId("1");
        todo.setTodoTitle("Test Todo");
        todo.setDescription("This is a test todo");

        todoVO = TodoMapper.INSTANCE.toView(todo);

        saveTodoDTO = new SaveTodoDTO();
        saveTodoDTO.setTodoTitle("Test Todo");
        saveTodoDTO.setDescription("This is a test todo");
        saveTodoDTO.setDueDate(LocalDateTime.now());
        saveTodoDTO.setStatus(0);
        saveTodoDTO.setPriority(1);
        saveTodoDTO.setTags(Arrays.asList("tag1", "tag2"));
        saveTodoDTO.setVersion(0);

        queryTodoDTO = new QueryTodoDTO();
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNum(0);
        pageQuery.setPageSize(2);
        queryTodoDTO.setPageable(pageQuery);
    }

    @Test
    public void testGetAllTodos() throws Exception {
        List<Todo> todoList = Arrays.asList(todo);
        when(todoService.getAllTodos()).thenReturn(todoList);

        mockMvc.perform(get("/todo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    public void testCreateTodo() throws Exception {
        when(todoService.saveTodo(null, saveTodoDTO)).thenReturn(todo);

        mockMvc.perform(post("/todo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveTodoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(todoVO.getId()))
                .andExpect(jsonPath("$.data.todoTitle").value(todoVO.getTodoTitle()))
                .andExpect(jsonPath("$.data.description").value(todoVO.getDescription()));
    }

    @Test
    public void testUpdateTodo() throws Exception {
        when(todoService.saveTodo("1", saveTodoDTO)).thenReturn(todo);

        mockMvc.perform(put("/todo/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveTodoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(todoVO.getId()))
                .andExpect(jsonPath("$.data.todoTitle").value(todoVO.getTodoTitle()))
                .andExpect(jsonPath("$.data.description").value(todoVO.getDescription()));
    }

    @Test
    public void testQueryTodos() throws Exception {
        List<Todo> todoList = Arrays.asList(todo);
        List<TodoVO> todoVOList = TodoMapper.INSTANCE.toViewList(todoList);
        Page<Todo> page = new PageImpl<>(todoList, PageRequest.of(0, 10), 1);

        when(todoService.queryTodos(queryTodoDTO)).thenReturn(page);

        mockMvc.perform(post("/todo/query")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(queryTodoDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content[0].id").value(todoVO.getId()))
                .andExpect(jsonPath("$.data.content[0].todoTitle").value(todoVO.getTodoTitle()))
                .andExpect(jsonPath("$.data.content[0].description").value(todoVO.getDescription()));
    }

    @Test
    public void testGetTodoById() throws Exception {
        when(todoService.getTodoById("1")).thenReturn(todo);

        mockMvc.perform(get("/todo/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(todoVO.getId()))
                .andExpect(jsonPath("$.data.todoTitle").value(todoVO.getTodoTitle()))
                .andExpect(jsonPath("$.data.description").value(todoVO.getDescription()));
    }

    @Test
    public void testDeleteTodo() throws Exception {
        doNothing().when(todoService).deleteTodo("1");

        mockMvc.perform(delete("/todo/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(nullValue()));
    }
}
