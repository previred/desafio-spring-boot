package cl.cediaz.desafiospringboot.controller;

import cl.cediaz.desafiospringboot.dto.TaskRequest;
import cl.cediaz.desafiospringboot.dto.TaskResponse;
import cl.cediaz.desafiospringboot.security.CustomUserDetailsService;
import cl.cediaz.desafiospringboot.service.TaskService;
import cl.cediaz.desafiospringboot.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testCreateTask() throws Exception {
        TaskResponse response = new TaskResponse(
                1L, "New Task", "New Description",
                null, null, null, List.of()
        );
        when(taskService.save(any(TaskRequest.class))).thenReturn(response);

        String requestBody = """
                {
                  "title": "New Task",
                  "description": "New Description",
                  "statusId": 1,
                  "userIds": [1],
                  "dueDate": "2026-03-15T10:00:00"
                }
                """;

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Task"))
                .andExpect(jsonPath("$.description").value("New Description"));
    }

    @Test
    void testGetTaskById() throws Exception {
        TaskResponse response = new TaskResponse(
                1L, "Test Task", "Description",
                null, null, null, List.of()
        );
        when(taskService.findById(anyLong())).thenReturn(response);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Task"))
                .andExpect(jsonPath("$.description").value("Description"));
    }

    @Test
    void testGetTasksByUser() throws Exception {
        TaskResponse response = new TaskResponse(
                1L, "User Task", "User Description",
                null, null, null, List.of()
        );
        when(taskService.findByUserId(anyLong())).thenReturn(List.of(response));

        mockMvc.perform(get("/api/tasks/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("User Task"));
    }

    @Test
    void testAssignTaskToUser() throws Exception {
        TaskResponse response = new TaskResponse(
                1L, "Assigned Task", "Assigned",
                null, null, null, List.of()
        );
        when(taskService.assignTaskToUser(anyLong(), anyLong())).thenReturn(response);

        mockMvc.perform(put("/api/tasks/1/assign/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Assigned Task"));
    }

    @Test
    void testUpdateTask() throws Exception {
        TaskResponse response = new TaskResponse(
                1L, "Updated Task", "Updated Description",
                null, null, null, List.of()
        );
        when(taskService.update(anyLong(), any(TaskRequest.class))).thenReturn(response);

        String requestBody = """
                {
                  "title": "Updated Task",
                  "description": "Updated Description",
                  "statusId": 2,
                  "userIds": [1],
                  "dueDate": "2026-03-15T10:00:00"
                }
                """;

        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.description").value("Updated Description"));
    }

    @Test
    void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllTasks() throws Exception {
        TaskResponse response = new TaskResponse(
                1L, "Task 1", "Desc 1",
                null, null, null, List.of()
        );
        when(taskService.findAll()).thenReturn(List.of(response));

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Task 1"));
    }
}
