package alexandre.possari.JavaRestAPI.integration;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.repository.TaskRepository;
import alexandre.possari.JavaRestAPI.util.TaskCreator;
import alexandre.possari.JavaRestAPI.wrapper.PageableResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class TaskControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @LocalServerPort
    private int port;
    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("list returns list of task inside page object when successful")
    void list_ReturnsListOfTasksInsidePageObject_WhenSuccessful(){
        Task savedTask = taskRepository.save(TaskCreator.createTaskToBeSaved());
        String expectedTitle = savedTask.getTitle();
        PageableResponse<Task> taskPage = testRestTemplate.exchange("/tasks", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Task>>() {
                }).getBody();

        Assertions.assertThat(taskPage).isNotNull();
        Assertions.assertThat(taskPage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(taskPage.toList().get(0).getTitle()).isEqualTo(expectedTitle);
    }
}
