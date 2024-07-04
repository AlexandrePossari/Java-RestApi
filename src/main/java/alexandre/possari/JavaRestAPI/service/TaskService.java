package alexandre.possari.JavaRestAPI.service;

import alexandre.possari.JavaRestAPI.domain.Task;
import alexandre.possari.JavaRestAPI.repository.TaskRepository;
import alexandre.possari.JavaRestAPI.requests.TaskPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> listAll(){
        return taskRepository.findAll();
    }

    public Task save(TaskPostRequestBody taskPostRequestBody) {
        return taskRepository.save(Task.builder().title(taskPostRequestBody.getTitle())
                        .description(taskPostRequestBody.getDescription())
                        .dueDate(taskPostRequestBody.getDueDate())
                        .status(taskPostRequestBody.getStatus())
                .build());
    }
}
