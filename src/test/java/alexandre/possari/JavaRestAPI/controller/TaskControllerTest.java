package alexandre.possari.JavaRestAPI.controller;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.requests.TaskPostRequestBody;
import alexandre.possari.JavaRestAPI.requests.TaskPutRequestBody;
import alexandre.possari.JavaRestAPI.service.TaskService;
import alexandre.possari.JavaRestAPI.util.TaskCreator;
import alexandre.possari.JavaRestAPI.util.TaskPostRequestBodyCreator;
import alexandre.possari.JavaRestAPI.util.TaskPutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class TaskControllerTest {
    @InjectMocks
    private TaskController taskController;
    @Mock
    private TaskService taskServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Task> taskPage = new PageImpl<>(List.of(TaskCreator.createValidTask()));
        
        BDDMockito.when(taskServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(taskPage);

        BDDMockito.when(taskServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(TaskCreator.createValidTask());

        BDDMockito.when(taskServiceMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(List.of(TaskCreator.createValidTask()));

        BDDMockito.when(taskServiceMock.save(ArgumentMatchers.any(TaskPostRequestBody.class)))
                .thenReturn(TaskCreator.createValidTask());

        BDDMockito.doNothing().when(taskServiceMock).replace(ArgumentMatchers.any(TaskPutRequestBody.class));

        BDDMockito.doNothing().when(taskServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("List returns list of task inside page object when successful")
    void list_ReturnsListOfTasksInsidePageObject_WhenSuccessful(){
        String expectedTitle = TaskCreator.createValidTask().getTitle();
        Page<Task> taskPage = taskController.list(null).getBody();

        Assertions.assertThat(taskPage).isNotNull();
        Assertions.assertThat(taskPage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(taskPage.toList().get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findById returns task when successful")
    void findById_ReturnsTask_WhenSuccessful(){
        Long expectedId = TaskCreator.createValidTask().getId();
        Task task = taskController.findById(1).getBody();

        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByTitle returns a list of task when successful")
    void findByTitle_ReturnsListOfTask_WhenSuccessful(){
        String expectedTitle = TaskCreator.createValidTask().getTitle();
        List<Task> tasks = taskController.findByTitle("task").getBody();

        Assertions.assertThat(tasks)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(tasks.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findByName returns an empty list of task when task is not found")
    void findByName_ReturnsEmptyListOfTask_WhenTaskIsNotFound(){
        BDDMockito.when(taskServiceMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());
        List<Task> tasks = taskController.findByTitle("task").getBody();

        Assertions.assertThat(tasks)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns task when successful")
    void save_ReturnsTask_WhenSuccessful(){
        Task task = taskController.save(TaskPostRequestBodyCreator.createTaskPostRequestBody()).getBody();

        Assertions.assertThat(task).isNotNull().isEqualTo(TaskCreator.createValidTask());
    }

    @Test
    @DisplayName("replace updates task when successful")
    void replace_UpdatesTask_WhenSuccessful(){
        Assertions.assertThatCode(() ->taskController.replace(TaskPutRequestBodyCreator.createTaskPutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = taskController.replace(TaskPutRequestBodyCreator.createTaskPutRequestBody());

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete removes task when successful")
    void delete_RemovesTask_WhenSuccessful(){
        Assertions.assertThatCode(() ->taskController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = taskController.delete(1);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}