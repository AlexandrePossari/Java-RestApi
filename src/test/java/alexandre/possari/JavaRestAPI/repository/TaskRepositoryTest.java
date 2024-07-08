package alexandre.possari.JavaRestAPI.repository;

import alexandre.possari.JavaRestAPI.domain.Task;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Tests for Task Repository")
class TaskRepositoryTest {
    @Autowired
    private TaskRepository taskRepository;

    @Test
    @DisplayName("Save creates task when Successful")
    void save_PersistTask_WhenSuccessful(){
        Task taskToBeSaved = createTask();
        Task taskSaved = this.taskRepository.save(taskToBeSaved);
        Assertions.assertThat(taskSaved).isNotNull();
        Assertions.assertThat(taskSaved.getId()).isNotNull();
        Assertions.assertThat(taskSaved.getTitle()).isEqualTo(taskToBeSaved.getTitle());

    }

    private Task createTask(){
        LocalDate date = LocalDate.of(2024, 12, 21);;
        return Task.builder()
                .title("JPA Test")
                .description("JPA Test")
                .status("Done")
                .dueDate(date)
                .build();
    }
}