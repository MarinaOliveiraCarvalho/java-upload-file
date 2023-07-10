package com.core.service;

import com.core.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class TaskResourceTest {

    @Autowired
    private MockMvc mockmvc;

    @MockBean
    private OauthService oauthService;

    @MockBean
    private TodoService todoService;

    @MockBean
    private TaskService taskService;

    @MockBean
    TaskRepository taskRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getJourneyTest() throws Exception {

        User userTest = new User("2c44cfdd-2020-4753-a162-2e5cfd018a2a", "test", "test@test.com" );
        when(oauthService.getUserByToken(any())).thenReturn(userTest);

        Todo todoTest = new Todo(UUID.fromString("59ae3935-1272-48f2-a0e1-51dcabc81d81"),
                userTest, "Todo_test", null, LocalDateTime.now(), LocalDateTime.now());

        Task taskTest =  new Task(UUID.fromString("d6d14ff5-4a77-4e88-adf2-ec8e31a5c6c2"), false,
                "task_test", null, todoTest, LocalDateTime.now(), LocalDateTime.now() );
        List taskList = new ArrayList<Task>();
        taskList.add(taskTest);

        todoTest.setTask(taskList);

        TaskConclusionDto taskConclusionDto = TaskConclusionDto.builder()
                .id("d6d14ff5-4a77-4e88-adf2-ec8e31a5c6c2")
                .name("task_test")
                .todoId("59ae3935-1272-48f2-a0e1-51dcabc81d81")
                .build();

        when(oauthService.getUserByToken(any())).thenReturn(userTest);

        when(todoService.findOneTodoOfUser(any(), any())).thenReturn(todoTest);

        when(taskService.findOneTaskOfTodo(any(), any())).thenReturn(taskTest);

        when(taskRepository.save(any())).thenReturn(taskTest);

        String URL = "/task/conclusion";
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpZEhvc3BpdGFsIjoxLCJwYXRpZW50TmFtZSI6Ikpob25ueSBMYXdyZW5jZSIsInRpY2tldCI6IlhQVE8iLCJwYXRpZW50UGhvbmUiOiIrNTUxMTk4Nzg3NjU2NSIsImNvZFRyaWFnZSI6MjgwMDY0OCwiY29kU2VydmljZSI6NDg5MjI0MH0.n4Woj-cAo9Js0-tvp1fOXrxfZ_oD_QWJX33ZBvO9-Ls";

        mockmvc.perform(post(URL)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsBytes(taskConclusionDto))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


}
