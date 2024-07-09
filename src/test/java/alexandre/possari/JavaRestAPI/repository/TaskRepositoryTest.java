package alexandre.possari.JavaRestAPI.repository;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.util.TaskCreator;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Task Repository")
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;    

    @Test
    @DisplayName("Save creates task when Successful")
    void save_PersistTask_WhenSuccessful(){
        Task taskToBeSaved = TaskCreator.createTaskToBeSaved();
        Task taskSaved = this.taskRepository.save(taskToBeSaved);
        Assertions.assertThat(taskSaved).isNotNull();
        Assertions.assertThat(taskSaved.getId()).isNotNull();
        Assertions.assertThat(taskSaved.getTitle()).isEqualTo(taskToBeSaved.getTitle());

    }

    @Test
    @DisplayName("Save updates task when Successful")
    void save_UpdatesTask_WhenSuccessful(){
        Task taskToBeSaved = TaskCreator.createTaskToBeSaved();
        Task taskSaved = this.taskRepository.save(taskToBeSaved);
        taskSaved.setTitle("Updated title");
        Task taskUpdated = this.taskRepository.save(taskSaved);

        Assertions.assertThat(taskUpdated).isNotNull();
        Assertions.assertThat(taskUpdated.getId()).isNotNull();
        Assertions.assertThat(taskUpdated.getTitle()).isEqualTo(taskSaved.getTitle());
    }

    @Test
    @DisplayName("Delete removes task when Successful")
    void delete_RemovesTask_WhenSuccessful(){
        Task taskToBeSaved = TaskCreator.createTaskToBeSaved();
        Task taskSaved = this.taskRepository.save(taskToBeSaved);
        this.taskRepository.delete(taskSaved);
        Optional<Task> taskOptional = this.taskRepository.findById(taskSaved.getId());

        Assertions.assertThat(taskOptional).isEmpty();
    }

    @Test
    @DisplayName("Find By Title returns list of task when Successful")
    void findByTitle_ReturnsListOfTask_WhenSuccessful(){
        Task taskToBeSaved = TaskCreator.createTaskToBeSaved();
        Task taskSaved = this.taskRepository.save(taskToBeSaved);
        String title = taskSaved.getTitle();
        List<Task> tasks = this.taskRepository.findByTitle(title);

        Assertions.assertThat(tasks).isNotEmpty();
        Assertions.assertThat(tasks).contains(taskSaved);
    }

    @Test
    @DisplayName("Find By Title returns empty list when no task is found")
    void findByTitle_ReturnsEmptyList_WhenTaskIsNotFound(){
        List<Task> tasks = this.taskRepository.findByTitle("Does not exist");

        Assertions.assertThat(tasks).isEmpty();
    }

    @Test
    @DisplayName("Save throw ConstraintViolationException when title is empty")
    void save_ThrowsConstraintViolationException_WhenTitleIsEmpty(){
        Task task = new Task();

        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> this.taskRepository.save(task))
                .withMessageContaining("The task title cannot be empty");
    }

}