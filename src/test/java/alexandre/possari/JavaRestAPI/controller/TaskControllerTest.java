package alexandre.possari.JavaRestAPI.controller;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.service.TaskService;
import alexandre.possari.JavaRestAPI.util.TaskCreator;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
}