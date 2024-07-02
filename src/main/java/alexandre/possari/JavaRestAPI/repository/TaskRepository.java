package alexandre.possari.JavaRestAPI.repository;

import alexandre.possari.JavaRestAPI.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
