package com.tf.task.flow.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tf.task.flow.api.controller.UserController;
import com.tf.task.flow.api.model.converter.UserMapper;
import com.tf.task.flow.api.model.dto.UserRegisterDTO;
import com.tf.task.flow.api.model.entity.User;
import com.tf.task.flow.api.model.view.UserVO;
import com.tf.task.flow.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
//@WebMvcTest(TodoController.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserVO userVO;
    private UserRegisterDTO userRegisterDTO;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        user = new User();
        user.setId("67d2d283ce68fd68b0780ef3");
        user.setUsername("testUser");
        user.setPassword("password");
        user.setEmail("email@mail.com");
        user.setRoles(Arrays.asList("ROLE_USER"));
        userVO = UserMapper.INSTANCE.toView(user);

        userRegisterDTO = new UserRegisterDTO();
        userRegisterDTO.setUsername("testUser");
        userRegisterDTO.setPassword("password");
        userRegisterDTO.setEmail("email@mail.com");
        userRegisterDTO.setRoles(Arrays.asList("ROLE_USER"));
    }

    @Test
    public void testRegister() throws Exception {
        when(userService.register(userRegisterDTO)).thenReturn(user);

        mockMvc.perform(post("/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRegisterDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.username").value(userVO.getUsername()))
                .andExpect(jsonPath("$.data.email").value(userVO.getEmail()));

        verify(userService).register(any(UserRegisterDTO.class));
    }

    @Test
    public void testLogin() throws Exception {
        String mockToken = "mock-jwt-token";
        when(userService.login("testUser", "password")).thenReturn(mockToken);  // 🔥 这里 Mock Service

        mockMvc.perform(post("/user/login")
                .param("username", "testUser")
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data").value(mockToken));
    }
}
