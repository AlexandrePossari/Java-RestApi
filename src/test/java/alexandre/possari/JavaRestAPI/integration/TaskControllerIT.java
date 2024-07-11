package alexandre.possari.JavaRestAPI.integration;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.repository.TaskRepository;
import alexandre.possari.JavaRestAPI.requests.TaskPostRequestBody;
import alexandre.possari.JavaRestAPI.util.TaskCreator;
import alexandre.possari.JavaRestAPI.util.TaskPostRequestBodyCreator;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
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

    @Test
    @DisplayName("findById returns task when successful")
    void findById_ReturnsTask_WhenSuccessful() {
        Task savedTask = taskRepository.save(TaskCreator.createTaskToBeSaved());
        Long expectedId = savedTask.getId();
        Task task = testRestTemplate.getForObject("/tasks/{id}", Task.class, expectedId);

        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByTitle returns a list of task when successful")
    void findByTitle_ReturnsListOfTask_WhenSuccessful(){
        Task savedTask = taskRepository.save(TaskCreator.createTaskToBeSaved());
        String expectedTitle = savedTask.getTitle();
        String url = String.format("/tasks/find?title=%s", expectedTitle);
        List<Task> tasks = testRestTemplate.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Task>>() {
                }).getBody();

        Assertions.assertThat(tasks)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(tasks.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findByTitle returns an empty list of task when task is not found")
    void findByTitle_ReturnsEmptyListOfTask_WhenTaskIsNotFound(){
        List<Task> tasks = testRestTemplate.exchange("/tasks/find?title=notexistent", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Task>>() {
                }).getBody();

        Assertions.assertThat(tasks)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns task when successful")
    void save_ReturnsTask_WhenSuccessful(){
        TaskPostRequestBody taskPostRequestBody = TaskPostRequestBodyCreator.createTaskPostRequestBody();
        ResponseEntity<Task> taskResponseEntity = testRestTemplate.postForEntity("/tasks", taskPostRequestBody, Task.class);

        Assertions.assertThat(taskResponseEntity).isNotNull();
        Assertions.assertThat(taskResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(taskResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(taskResponseEntity.getBody().getId()).isNotNull();

    }

    @Test
    @DisplayName("replace updates task when successful")
    void replace_UpdatesTask_WhenSuccessful(){
        Task savedTask = taskRepository.save(TaskCreator.createTaskToBeSaved());
        savedTask.setTitle("new title");
        ResponseEntity<Void> taskResponseEntity = testRestTemplate.exchange("/tasks",
                HttpMethod.PUT,new HttpEntity<>(savedTask), Void.class);

        Assertions.assertThat(taskResponseEntity).isNotNull();
        Assertions.assertThat(taskResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes task when successful")
    void delete_RemovesTask_WhenSuccessful(){
        Task savedTask = taskRepository.save(TaskCreator.createTaskToBeSaved());
        ResponseEntity<Void> taskResponseEntity = testRestTemplate.exchange("/tasks/{id}",
                HttpMethod.DELETE,null, Void.class, savedTask.getId());

        Assertions.assertThat(taskResponseEntity).isNotNull();
        Assertions.assertThat(taskResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
