package alexandre.possari.JavaRestAPI.service;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.exception.BadRequestException;
import alexandre.possari.JavaRestAPI.repository.TaskRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class TaskServiceTest {
    @InjectMocks
    private TaskService taskService;
    @Mock
    private TaskRepository taskRepositoryMock;

    @BeforeEach
    void setUp(){
        PageImpl<Task> taskPage = new PageImpl<>(List.of(TaskCreator.createValidTask()));

        BDDMockito.when(taskRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(taskPage);

        BDDMockito.when(taskRepositoryMock.findAll())
                .thenReturn(List.of(TaskCreator.createValidTask()));

        BDDMockito.when(taskRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(TaskCreator.createValidTask()));

        BDDMockito.when(taskRepositoryMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(List.of(TaskCreator.createValidTask()));

        BDDMockito.when(taskRepositoryMock.save(ArgumentMatchers.any(Task.class)))
                .thenReturn(TaskCreator.createValidTask());

        BDDMockito.doNothing().when(taskRepositoryMock).delete(ArgumentMatchers.any(Task.class));
    }
    @Test
    @DisplayName("listAll returns list of task inside page object when successful")
    void listAll_ReturnsListOfTasksInsidePageObject_WhenSuccessful(){
        String expectedName = TaskCreator.createValidTask().getTitle();
        Page<Task> taskPage = taskService.listAll(PageRequest.of(1,1));

        Assertions.assertThat(taskPage).isNotNull();
        Assertions.assertThat(taskPage.toList())
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(taskPage.toList().get(0).getTitle()).isEqualTo(expectedName);
    }

    

    @Test
    @DisplayName("findByIdOrThrowBadRequestException returns task when successful")
    void findByIdOrThrowBadRequestException_ReturnsTask_WhenSuccessful(){
        Long expectedId = TaskCreator.createValidTask().getId();
        Task task = taskService.findByIdOrThrowBadRequestException(1);

        Assertions.assertThat(task).isNotNull();
        Assertions.assertThat(task.getId()).isNotNull().isEqualTo(expectedId);
    }

    @Test
    @DisplayName("findByIdOrThrowBadRequestException throws BadRequestException when task is not found")
    void findByIdOrThrowBadRequestException_ThrowsBadRequestException_WhenTaskIsNotFound(){
        BDDMockito.when(taskRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> taskService.findByIdOrThrowBadRequestException(1));
    }

    @Test
    @DisplayName("findByTitle returns a list of task when successful")
    void findByTitle_ReturnsListOfTask_WhenSuccessful(){
        String expectedTitle = TaskCreator.createValidTask().getTitle();
        List<Task> tasks = taskService.findByTitle("task");

        Assertions.assertThat(tasks)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);
        Assertions.assertThat(tasks.get(0).getTitle()).isEqualTo(expectedTitle);
    }

    @Test
    @DisplayName("findByTitle returns an empty list of task when task is not found")
    void findByTitle_ReturnsEmptyListOfTask_WhenTaskIsNotFound(){
        BDDMockito.when(taskRepositoryMock.findByTitle(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());
        List<Task> tasks = taskService.findByTitle("task");

        Assertions.assertThat(tasks)
                .isNotNull()
                .isEmpty();
    }

    @Test
    @DisplayName("save returns task when successful")
    void save_ReturnsTask_WhenSuccessful(){
        Task task = taskService.save(TaskPostRequestBodyCreator.createTaskPostRequestBody());

        Assertions.assertThat(task).isNotNull().isEqualTo(TaskCreator.createValidTask());
    }

    @Test
    @DisplayName("replace updates task when successful")
    void replace_UpdatesTask_WhenSuccessful(){
        Assertions.assertThatCode(() ->taskService.replace(TaskPutRequestBodyCreator.createTaskPutRequestBody()))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("delete removes task when successful")
    void delete_RemovesTask_WhenSuccessful(){
        Assertions.assertThatCode(() ->taskService.delete(1))
                .doesNotThrowAnyException();
    }
}
